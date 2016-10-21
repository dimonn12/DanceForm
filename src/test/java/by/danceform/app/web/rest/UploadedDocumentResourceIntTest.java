package by.danceform.app.web.rest;

import by.danceform.app.DanceFormApp;
import by.danceform.app.converter.document.UploadedDocumentConverter;
import by.danceform.app.domain.document.UploadedDocument;
import by.danceform.app.repository.document.UploadedDocumentRepository;
import by.danceform.app.service.document.UploadedDocumentService;
import by.danceform.app.web.rest.document.UploadedDocumentResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the UploadedDocumentResource REST controller.
 *
 * @see UploadedDocumentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DanceFormApp.class)
public class UploadedDocumentResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "A";
    private static final String UPDATED_NAME = "B";
    private static final String DEFAULT_EXTENSION = "A";
    private static final String UPDATED_EXTENSION = "B";
    private static final String DEFAULT_PATH = "A";
    private static final String UPDATED_PATH = "B";
    private static final String DEFAULT_EXTERNAL_PATH = "AAAAA";
    private static final String UPDATED_EXTERNAL_PATH = "BBBBB";

    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_UPLOADED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_UPLOADED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_UPLOADED_DATE_STR = dateTimeFormatter.format(DEFAULT_UPLOADED_DATE);

    private static final Integer DEFAULT_UPLOADED_BY = 1;
    private static final Integer UPDATED_UPLOADED_BY = 2;

    @Inject
    private UploadedDocumentRepository uploadedDocumentRepository;

    @Inject
    private UploadedDocumentConverter uploadedDocumentConverter;

    @Inject
    private UploadedDocumentService uploadedDocumentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUploadedDocumentMockMvc;

    private UploadedDocument uploadedDocument;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UploadedDocumentResource uploadedDocumentResource = new UploadedDocumentResource();
        ReflectionTestUtils.setField(uploadedDocumentResource, "uploadedDocumentService", uploadedDocumentService);
        this.restUploadedDocumentMockMvc = MockMvcBuilders.standaloneSetup(uploadedDocumentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
    public static UploadedDocument createEntity(EntityManager em) {
        UploadedDocument uploadedDocument = new UploadedDocument()
                .name(DEFAULT_NAME)
                .extension(DEFAULT_EXTENSION)
                .path(DEFAULT_PATH)
                .externalPath(DEFAULT_EXTERNAL_PATH)
                .content(DEFAULT_CONTENT)
                .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE)
                .uploadedDate(DEFAULT_UPLOADED_DATE)
                .uploadedBy(DEFAULT_UPLOADED_BY);
        return uploadedDocument;
    }

    @Before
    public void initTest() {
        uploadedDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createUploadedDocument() throws Exception {
        int databaseSizeBeforeCreate = uploadedDocumentRepository.findAll().size();

        // Create the UploadedDocument
        UploadedDocumentDTO uploadedDocumentDTO = uploadedDocumentMapper.uploadedDocumentToUploadedDocumentDTO(uploadedDocument);

        restUploadedDocumentMockMvc.perform(post("/api/uploaded-documents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(uploadedDocumentDTO)))
                .andExpect(status().isCreated());

        // Validate the UploadedDocument in the database
        List<UploadedDocument> uploadedDocuments = uploadedDocumentRepository.findAll();
        assertThat(uploadedDocuments).hasSize(databaseSizeBeforeCreate + 1);
        UploadedDocument testUploadedDocument = uploadedDocuments.get(uploadedDocuments.size() - 1);
        assertThat(testUploadedDocument.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUploadedDocument.getExtension()).isEqualTo(DEFAULT_EXTENSION);
        assertThat(testUploadedDocument.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testUploadedDocument.getExternalPath()).isEqualTo(DEFAULT_EXTERNAL_PATH);
        assertThat(testUploadedDocument.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testUploadedDocument.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testUploadedDocument.getUploadedDate()).isEqualTo(DEFAULT_UPLOADED_DATE);
        assertThat(testUploadedDocument.getUploadedBy()).isEqualTo(DEFAULT_UPLOADED_BY);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = uploadedDocumentRepository.findAll().size();
        // set the field null
        uploadedDocument.setName(null);

        // Create the UploadedDocument, which fails.
        UploadedDocumentDTO uploadedDocumentDTO = uploadedDocumentMapper.uploadedDocumentToUploadedDocumentDTO(uploadedDocument);

        restUploadedDocumentMockMvc.perform(post("/api/uploaded-documents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(uploadedDocumentDTO)))
                .andExpect(status().isBadRequest());

        List<UploadedDocument> uploadedDocuments = uploadedDocumentRepository.findAll();
        assertThat(uploadedDocuments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExtensionIsRequired() throws Exception {
        int databaseSizeBeforeTest = uploadedDocumentRepository.findAll().size();
        // set the field null
        uploadedDocument.setExtension(null);

        // Create the UploadedDocument, which fails.
        UploadedDocumentDTO uploadedDocumentDTO = uploadedDocumentMapper.uploadedDocumentToUploadedDocumentDTO(uploadedDocument);

        restUploadedDocumentMockMvc.perform(post("/api/uploaded-documents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(uploadedDocumentDTO)))
                .andExpect(status().isBadRequest());

        List<UploadedDocument> uploadedDocuments = uploadedDocumentRepository.findAll();
        assertThat(uploadedDocuments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = uploadedDocumentRepository.findAll().size();
        // set the field null
        uploadedDocument.setPath(null);

        // Create the UploadedDocument, which fails.
        UploadedDocumentDTO uploadedDocumentDTO = uploadedDocumentMapper.uploadedDocumentToUploadedDocumentDTO(uploadedDocument);

        restUploadedDocumentMockMvc.perform(post("/api/uploaded-documents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(uploadedDocumentDTO)))
                .andExpect(status().isBadRequest());

        List<UploadedDocument> uploadedDocuments = uploadedDocumentRepository.findAll();
        assertThat(uploadedDocuments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUploadedDocuments() throws Exception {
        // Initialize the database
        uploadedDocumentRepository.saveAndFlush(uploadedDocument);

        // Get all the uploadedDocuments
        restUploadedDocumentMockMvc.perform(get("/api/uploaded-documents?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(uploadedDocument.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION.toString())))
                .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
                .andExpect(jsonPath("$.[*].externalPath").value(hasItem(DEFAULT_EXTERNAL_PATH.toString())))
                .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
                .andExpect(jsonPath("$.[*].uploadedDate").value(hasItem(DEFAULT_UPLOADED_DATE_STR)))
                .andExpect(jsonPath("$.[*].uploadedBy").value(hasItem(DEFAULT_UPLOADED_BY)));
    }

    @Test
    @Transactional
    public void getUploadedDocument() throws Exception {
        // Initialize the database
        uploadedDocumentRepository.saveAndFlush(uploadedDocument);

        // Get the uploadedDocument
        restUploadedDocumentMockMvc.perform(get("/api/uploaded-documents/{id}", uploadedDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uploadedDocument.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.extension").value(DEFAULT_EXTENSION.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.externalPath").value(DEFAULT_EXTERNAL_PATH.toString()))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.uploadedDate").value(DEFAULT_UPLOADED_DATE_STR))
            .andExpect(jsonPath("$.uploadedBy").value(DEFAULT_UPLOADED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingUploadedDocument() throws Exception {
        // Get the uploadedDocument
        restUploadedDocumentMockMvc.perform(get("/api/uploaded-documents/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUploadedDocument() throws Exception {
        // Initialize the database
        uploadedDocumentRepository.saveAndFlush(uploadedDocument);
        int databaseSizeBeforeUpdate = uploadedDocumentRepository.findAll().size();

        // Update the uploadedDocument
        UploadedDocument updatedUploadedDocument = uploadedDocumentRepository.findOne(uploadedDocument.getId());
        updatedUploadedDocument
                .name(UPDATED_NAME)
                .extension(UPDATED_EXTENSION)
                .path(UPDATED_PATH)
                .externalPath(UPDATED_EXTERNAL_PATH)
                .content(UPDATED_CONTENT)
                .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
                .uploadedDate(UPDATED_UPLOADED_DATE)
                .uploadedBy(UPDATED_UPLOADED_BY);
        UploadedDocumentDTO uploadedDocumentDTO = uploadedDocumentMapper.uploadedDocumentToUploadedDocumentDTO(updatedUploadedDocument);

        restUploadedDocumentMockMvc.perform(put("/api/uploaded-documents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(uploadedDocumentDTO)))
                .andExpect(status().isOk());

        // Validate the UploadedDocument in the database
        List<UploadedDocument> uploadedDocuments = uploadedDocumentRepository.findAll();
        assertThat(uploadedDocuments).hasSize(databaseSizeBeforeUpdate);
        UploadedDocument testUploadedDocument = uploadedDocuments.get(uploadedDocuments.size() - 1);
        assertThat(testUploadedDocument.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUploadedDocument.getExtension()).isEqualTo(UPDATED_EXTENSION);
        assertThat(testUploadedDocument.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testUploadedDocument.getExternalPath()).isEqualTo(UPDATED_EXTERNAL_PATH);
        assertThat(testUploadedDocument.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testUploadedDocument.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testUploadedDocument.getUploadedDate()).isEqualTo(UPDATED_UPLOADED_DATE);
        assertThat(testUploadedDocument.getUploadedBy()).isEqualTo(UPDATED_UPLOADED_BY);
    }*/

    @Test
    @Transactional
    public void deleteUploadedDocument() throws Exception {
        // Initialize the database
        uploadedDocumentRepository.saveAndFlush(uploadedDocument);
        int databaseSizeBeforeDelete = uploadedDocumentRepository.findAll().size();

        // Get the uploadedDocument
        restUploadedDocumentMockMvc.perform(delete("/api/uploaded-documents/{id}", uploadedDocument.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UploadedDocument> uploadedDocuments = uploadedDocumentRepository.findAll();
        assertThat(uploadedDocuments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
