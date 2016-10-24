package by.danceform.app.web.rest;

import by.danceform.app.DanceFormApp;

import by.danceform.app.domain.document.UploadedImage;
import by.danceform.app.repository.document.UploadedImageRepository;
import by.danceform.app.service.document.UploadedImageService;
import by.danceform.app.dto.document.UploadedImageDTO;

import by.danceform.app.web.rest.document.UploadedImageResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UploadedImageResource REST controller.
 *
 * @see UploadedImageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DanceFormApp.class)
public class UploadedImageResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_FULL_NAME = "A";
    private static final String UPDATED_FULL_NAME = "B";
    private static final String DEFAULT_PATH = "AAAAA";
    private static final String UPDATED_PATH = "BBBBB";
    private static final String DEFAULT_EXTERNAL_PATH = "AAAAA";
    private static final String UPDATED_EXTERNAL_PATH = "BBBBB";
    private static final String DEFAULT_UPLOADED_BY = "AAAAA";
    private static final String UPDATED_UPLOADED_BY = "BBBBB";

    private static final ZonedDateTime DEFAULT_UPLOADED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_UPLOADED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_UPLOADED_DATE_STR = dateTimeFormatter.format(DEFAULT_UPLOADED_DATE);
    private static final String DEFAULT_CONTENT_TYPE = "AAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBB";

    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    @Inject
    private UploadedImageRepository uploadedImageRepository;

   /* @Inject
    private UploadedImageMapper uploadedImageMapper;*/

    @Inject
    private UploadedImageService uploadedImageService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUploadedImageMockMvc;

    private UploadedImage uploadedImage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UploadedImageResource uploadedImageResource = new UploadedImageResource();
        ReflectionTestUtils.setField(uploadedImageResource, "uploadedImageService", uploadedImageService);
        this.restUploadedImageMockMvc = MockMvcBuilders.standaloneSetup(uploadedImageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
    public static UploadedImage createEntity(EntityManager em) {
        UploadedImage uploadedImage = new UploadedImage()
                .fullName(DEFAULT_FULL_NAME)
                .path(DEFAULT_PATH)
                .externalPath(DEFAULT_EXTERNAL_PATH)
                .uploadedBy(DEFAULT_UPLOADED_BY)
                .uploadedDate(DEFAULT_UPLOADED_DATE)
                .contentType(DEFAULT_CONTENT_TYPE)
                .content(DEFAULT_CONTENT)
                .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE);
        return uploadedImage;
    }

    @Before
    public void initTest() {
        uploadedImage = createEntity(em);
    }

    @Test
    @Transactional
    public void createUploadedImage() throws Exception {
        int databaseSizeBeforeCreate = uploadedImageRepository.findAll().size();

        // Create the UploadedImage
        UploadedImageDTO uploadedImageDTO = uploadedImageMapper.uploadedImageToUploadedImageDTO(uploadedImage);

        restUploadedImageMockMvc.perform(post("/api/uploaded-images")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(uploadedImageDTO)))
                .andExpect(status().isCreated());

        // Validate the UploadedImage in the database
        List<UploadedImage> uploadedImages = uploadedImageRepository.findAll();
        assertThat(uploadedImages).hasSize(databaseSizeBeforeCreate + 1);
        UploadedImage testUploadedImage = uploadedImages.get(uploadedImages.size() - 1);
        assertThat(testUploadedImage.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testUploadedImage.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testUploadedImage.getExternalPath()).isEqualTo(DEFAULT_EXTERNAL_PATH);
        assertThat(testUploadedImage.getUploadedBy()).isEqualTo(DEFAULT_UPLOADED_BY);
        assertThat(testUploadedImage.getUploadedDate()).isEqualTo(DEFAULT_UPLOADED_DATE);
        assertThat(testUploadedImage.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testUploadedImage.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testUploadedImage.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkFullNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = uploadedImageRepository.findAll().size();
        // set the field null
        uploadedImage.setFullName(null);

        // Create the UploadedImage, which fails.
        UploadedImageDTO uploadedImageDTO = uploadedImageMapper.uploadedImageToUploadedImageDTO(uploadedImage);

        restUploadedImageMockMvc.perform(post("/api/uploaded-images")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(uploadedImageDTO)))
                .andExpect(status().isBadRequest());

        List<UploadedImage> uploadedImages = uploadedImageRepository.findAll();
        assertThat(uploadedImages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUploadedImages() throws Exception {
        // Initialize the database
        uploadedImageRepository.saveAndFlush(uploadedImage);

        // Get all the uploadedImages
        restUploadedImageMockMvc.perform(get("/api/uploaded-images?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(uploadedImage.getId().intValue())))
                .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
                .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
                .andExpect(jsonPath("$.[*].externalPath").value(hasItem(DEFAULT_EXTERNAL_PATH.toString())))
                .andExpect(jsonPath("$.[*].uploadedBy").value(hasItem(DEFAULT_UPLOADED_BY.toString())))
                .andExpect(jsonPath("$.[*].uploadedDate").value(hasItem(DEFAULT_UPLOADED_DATE_STR)))
                .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))));
    }

    @Test
    @Transactional
    public void getUploadedImage() throws Exception {
        // Initialize the database
        uploadedImageRepository.saveAndFlush(uploadedImage);

        // Get the uploadedImage
        restUploadedImageMockMvc.perform(get("/api/uploaded-images/{id}", uploadedImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uploadedImage.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.externalPath").value(DEFAULT_EXTERNAL_PATH.toString()))
            .andExpect(jsonPath("$.uploadedBy").value(DEFAULT_UPLOADED_BY.toString()))
            .andExpect(jsonPath("$.uploadedDate").value(DEFAULT_UPLOADED_DATE_STR))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)));
    }

    @Test
    @Transactional
    public void getNonExistingUploadedImage() throws Exception {
        // Get the uploadedImage
        restUploadedImageMockMvc.perform(get("/api/uploaded-images/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUploadedImage() throws Exception {
        // Initialize the database
        uploadedImageRepository.saveAndFlush(uploadedImage);
        int databaseSizeBeforeUpdate = uploadedImageRepository.findAll().size();

        // Update the uploadedImage
        UploadedImage updatedUploadedImage = uploadedImageRepository.findOne(uploadedImage.getId());
        updatedUploadedImage
                .fullName(UPDATED_FULL_NAME)
                .path(UPDATED_PATH)
                .externalPath(UPDATED_EXTERNAL_PATH)
                .uploadedBy(UPDATED_UPLOADED_BY)
                .uploadedDate(UPDATED_UPLOADED_DATE)
                .contentType(UPDATED_CONTENT_TYPE)
                .content(UPDATED_CONTENT)
                .contentContentType(UPDATED_CONTENT_CONTENT_TYPE);
        UploadedImageDTO uploadedImageDTO = uploadedImageMapper.uploadedImageToUploadedImageDTO(updatedUploadedImage);

        restUploadedImageMockMvc.perform(put("/api/uploaded-images")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(uploadedImageDTO)))
                .andExpect(status().isOk());

        // Validate the UploadedImage in the database
        List<UploadedImage> uploadedImages = uploadedImageRepository.findAll();
        assertThat(uploadedImages).hasSize(databaseSizeBeforeUpdate);
        UploadedImage testUploadedImage = uploadedImages.get(uploadedImages.size() - 1);
        assertThat(testUploadedImage.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testUploadedImage.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testUploadedImage.getExternalPath()).isEqualTo(UPDATED_EXTERNAL_PATH);
        assertThat(testUploadedImage.getUploadedBy()).isEqualTo(UPDATED_UPLOADED_BY);
        assertThat(testUploadedImage.getUploadedDate()).isEqualTo(UPDATED_UPLOADED_DATE);
        assertThat(testUploadedImage.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testUploadedImage.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testUploadedImage.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
    }*/

    @Test
    @Transactional
    public void deleteUploadedImage() throws Exception {
        // Initialize the database
        uploadedImageRepository.saveAndFlush(uploadedImage);
        int databaseSizeBeforeDelete = uploadedImageRepository.findAll().size();

        // Get the uploadedImage
        restUploadedImageMockMvc.perform(delete("/api/uploaded-images/{id}", uploadedImage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UploadedImage> uploadedImages = uploadedImageRepository.findAll();
        assertThat(uploadedImages).hasSize(databaseSizeBeforeDelete - 1);
    }
}
