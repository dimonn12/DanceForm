package by.danceform.app.web.rest;

import by.danceform.app.DanceFormApp;
import by.danceform.app.domain.DanceClass;
import by.danceform.app.repository.DanceClassRepository;
import by.danceform.app.service.DanceClassService;
import by.danceform.app.service.dto.DanceClassDTO;
import by.danceform.app.service.mapper.DanceClassMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the DanceClassResource REST controller.
 *
 * @see DanceClassResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DanceFormApp.class)
public class DanceClassResourceIntTest {

    private static final String DEFAULT_NAME = "A";
    private static final String UPDATED_NAME = "B";
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_SYMBOL = "A";
    private static final String UPDATED_SYMBOL = "B";

    private static final Integer DEFAULT_WEIGHT = 1;
    private static final Integer UPDATED_WEIGHT = 2;

    private static final Integer DEFAULT_TRANSFER_SCORE = 1;
    private static final Integer UPDATED_TRANSFER_SCORE = 2;

    @Inject
    private DanceClassRepository danceClassRepository;

    @Inject
    private DanceClassMapper danceClassMapper;

    @Inject
    private DanceClassService danceClassService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDanceClassMockMvc;

    private DanceClass danceClass;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DanceClassResource danceClassResource = new DanceClassResource();
        ReflectionTestUtils.setField(danceClassResource, "danceClassService", danceClassService);
        this.restDanceClassMockMvc = MockMvcBuilders.standaloneSetup(danceClassResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    /**
     * Create an entity for this test.
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DanceClass createEntity(EntityManager em) {
        DanceClass danceClass = new DanceClass().name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .symbol(DEFAULT_SYMBOL)
            .weight(DEFAULT_WEIGHT)
            .transferScore(DEFAULT_TRANSFER_SCORE);
        return danceClass;
    }

    @Before
    public void initTest() {
        danceClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createDanceClass() throws Exception {
        int databaseSizeBeforeCreate = danceClassRepository.findAll().size();

        // Create the DanceClass
        DanceClassDTO danceClassDTO = danceClassMapper.danceClassToDanceClassDTO(danceClass);

        restDanceClassMockMvc.perform(post("/api/dance-classes").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danceClassDTO))).andExpect(status().isCreated());

        // Validate the DanceClass in the database
        List<DanceClass> danceClasses = danceClassRepository.findAll();
        assertThat(danceClasses).hasSize(databaseSizeBeforeCreate + 1);
        DanceClass testDanceClass = danceClasses.get(danceClasses.size() - 1);
        assertThat(testDanceClass.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDanceClass.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDanceClass.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testDanceClass.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testDanceClass.getTransferScore()).isEqualTo(DEFAULT_TRANSFER_SCORE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = danceClassRepository.findAll().size();
        // set the field null
        danceClass.setName(null);

        // Create the DanceClass, which fails.
        DanceClassDTO danceClassDTO = danceClassMapper.danceClassToDanceClassDTO(danceClass);

        restDanceClassMockMvc.perform(post("/api/dance-classes").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danceClassDTO))).andExpect(status().isBadRequest());

        List<DanceClass> danceClasses = danceClassRepository.findAll();
        assertThat(danceClasses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSymbolIsRequired() throws Exception {
        int databaseSizeBeforeTest = danceClassRepository.findAll().size();
        // set the field null
        danceClass.setSymbol(null);

        // Create the DanceClass, which fails.
        DanceClassDTO danceClassDTO = danceClassMapper.danceClassToDanceClassDTO(danceClass);

        restDanceClassMockMvc.perform(post("/api/dance-classes").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danceClassDTO))).andExpect(status().isBadRequest());

        List<DanceClass> danceClasses = danceClassRepository.findAll();
        assertThat(danceClasses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = danceClassRepository.findAll().size();
        // set the field null
        danceClass.setWeight(null);

        // Create the DanceClass, which fails.
        DanceClassDTO danceClassDTO = danceClassMapper.danceClassToDanceClassDTO(danceClass);

        restDanceClassMockMvc.perform(post("/api/dance-classes").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danceClassDTO))).andExpect(status().isBadRequest());

        List<DanceClass> danceClasses = danceClassRepository.findAll();
        assertThat(danceClasses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDanceClasses() throws Exception {
        // Initialize the database
        danceClassRepository.saveAndFlush(danceClass);

        // Get all the danceClasses
        restDanceClassMockMvc.perform(get("/api/dance-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(danceClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL.toString())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].transferScore").value(hasItem(DEFAULT_TRANSFER_SCORE)));
    }

    @Test
    @Transactional
    public void getDanceClass() throws Exception {
        // Initialize the database
        danceClassRepository.saveAndFlush(danceClass);

        // Get the danceClass
        restDanceClassMockMvc.perform(get("/api/dance-classes/{id}", danceClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(danceClass.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL.toString()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.transferScore").value(DEFAULT_TRANSFER_SCORE));
    }

    @Test
    @Transactional
    public void getNonExistingDanceClass() throws Exception {
        // Get the danceClass
        restDanceClassMockMvc.perform(get("/api/dance-classes/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDanceClass() throws Exception {
        // Initialize the database
        danceClassRepository.saveAndFlush(danceClass);
        int databaseSizeBeforeUpdate = danceClassRepository.findAll().size();

        // Update the danceClass
        DanceClass updatedDanceClass = danceClassRepository.findOne(danceClass.getId());
        updatedDanceClass.name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .symbol(UPDATED_SYMBOL)
            .weight(UPDATED_WEIGHT)
            .transferScore(UPDATED_TRANSFER_SCORE);
        DanceClassDTO danceClassDTO = danceClassMapper.danceClassToDanceClassDTO(updatedDanceClass);

        restDanceClassMockMvc.perform(put("/api/dance-classes").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(danceClassDTO))).andExpect(status().isOk());

        // Validate the DanceClass in the database
        List<DanceClass> danceClasses = danceClassRepository.findAll();
        assertThat(danceClasses).hasSize(databaseSizeBeforeUpdate);
        DanceClass testDanceClass = danceClasses.get(danceClasses.size() - 1);
        assertThat(testDanceClass.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDanceClass.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDanceClass.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testDanceClass.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testDanceClass.getTransferScore()).isEqualTo(UPDATED_TRANSFER_SCORE);
    }

    @Test
    @Transactional
    public void deleteDanceClass() throws Exception {
        // Initialize the database
        danceClassRepository.saveAndFlush(danceClass);
        int databaseSizeBeforeDelete = danceClassRepository.findAll().size();

        // Get the danceClass
        restDanceClassMockMvc.perform(delete("/api/dance-classes/{id}",
            danceClass.getId()).accept(TestUtil.APPLICATION_JSON_UTF8)).andExpect(status().isOk());

        // Validate the database is empty
        List<DanceClass> danceClasses = danceClassRepository.findAll();
        assertThat(danceClasses).hasSize(databaseSizeBeforeDelete - 1);
    }
}
