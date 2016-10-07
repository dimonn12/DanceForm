package by.danceform.app.web.rest;

import by.danceform.app.DanceFormApp;
import by.danceform.app.domain.AgeCategory;
import by.danceform.app.repository.AgeCategoryRepository;
import by.danceform.app.service.AgeCategoryService;
import by.danceform.app.service.dto.AgeCategoryDTO;
import by.danceform.app.service.mapper.AgeCategoryMapper;
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
 * Test class for the AgeCategoryResource REST controller.
 *
 * @see AgeCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DanceFormApp.class)
public class AgeCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "A";
    private static final String UPDATED_NAME = "B";

    private static final Integer DEFAULT_MIN_AGE = 1;
    private static final Integer UPDATED_MIN_AGE = 2;

    private static final Integer DEFAULT_MAX_AGE = 1;
    private static final Integer UPDATED_MAX_AGE = 2;

    @Inject
    private AgeCategoryRepository ageCategoryRepository;

    @Inject
    private AgeCategoryMapper ageCategoryMapper;

    @Inject
    private AgeCategoryService ageCategoryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAgeCategoryMockMvc;

    private AgeCategory ageCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AgeCategoryResource ageCategoryResource = new AgeCategoryResource();
        ReflectionTestUtils.setField(ageCategoryResource, "ageCategoryService", ageCategoryService);
        this.restAgeCategoryMockMvc = MockMvcBuilders.standaloneSetup(ageCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    /**
     * Create an entity for this test.
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgeCategory createEntity(EntityManager em) {
        AgeCategory ageCategory = new AgeCategory().name(DEFAULT_NAME).minAge(DEFAULT_MIN_AGE).maxAge(DEFAULT_MAX_AGE);
        return ageCategory;
    }

    @Before
    public void initTest() {
        ageCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgeCategory() throws Exception {
        int databaseSizeBeforeCreate = ageCategoryRepository.findAll().size();

        // Create the AgeCategory
        AgeCategoryDTO ageCategoryDTO = ageCategoryMapper.ageCategoryToAgeCategoryDTO(ageCategory);

        restAgeCategoryMockMvc.perform(post("/api/age-categories").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ageCategoryDTO))).andExpect(status().isCreated());

        // Validate the AgeCategory in the database
        List<AgeCategory> ageCategories = ageCategoryRepository.findAll();
        assertThat(ageCategories).hasSize(databaseSizeBeforeCreate + 1);
        AgeCategory testAgeCategory = ageCategories.get(ageCategories.size() - 1);
        assertThat(testAgeCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAgeCategory.getMinAge()).isEqualTo(DEFAULT_MIN_AGE);
        assertThat(testAgeCategory.getMaxAge()).isEqualTo(DEFAULT_MAX_AGE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ageCategoryRepository.findAll().size();
        // set the field null
        ageCategory.setName(null);

        // Create the AgeCategory, which fails.
        AgeCategoryDTO ageCategoryDTO = ageCategoryMapper.ageCategoryToAgeCategoryDTO(ageCategory);

        restAgeCategoryMockMvc.perform(post("/api/age-categories").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ageCategoryDTO))).andExpect(status().isBadRequest());

        List<AgeCategory> ageCategories = ageCategoryRepository.findAll();
        assertThat(ageCategories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ageCategoryRepository.findAll().size();
        // set the field null
        ageCategory.setMinAge(null);

        // Create the AgeCategory, which fails.
        AgeCategoryDTO ageCategoryDTO = ageCategoryMapper.ageCategoryToAgeCategoryDTO(ageCategory);

        restAgeCategoryMockMvc.perform(post("/api/age-categories").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ageCategoryDTO))).andExpect(status().isBadRequest());

        List<AgeCategory> ageCategories = ageCategoryRepository.findAll();
        assertThat(ageCategories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ageCategoryRepository.findAll().size();
        // set the field null
        ageCategory.setMaxAge(null);

        // Create the AgeCategory, which fails.
        AgeCategoryDTO ageCategoryDTO = ageCategoryMapper.ageCategoryToAgeCategoryDTO(ageCategory);

        restAgeCategoryMockMvc.perform(post("/api/age-categories").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ageCategoryDTO))).andExpect(status().isBadRequest());

        List<AgeCategory> ageCategories = ageCategoryRepository.findAll();
        assertThat(ageCategories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgeCategories() throws Exception {
        // Initialize the database
        ageCategoryRepository.saveAndFlush(ageCategory);

        // Get all the ageCategories
        restAgeCategoryMockMvc.perform(get("/api/age-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ageCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].minAge").value(hasItem(DEFAULT_MIN_AGE)))
            .andExpect(jsonPath("$.[*].maxAge").value(hasItem(DEFAULT_MAX_AGE)));
    }

    @Test
    @Transactional
    public void getAgeCategory() throws Exception {
        // Initialize the database
        ageCategoryRepository.saveAndFlush(ageCategory);

        // Get the ageCategory
        restAgeCategoryMockMvc.perform(get("/api/age-categories/{id}", ageCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ageCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.minAge").value(DEFAULT_MIN_AGE))
            .andExpect(jsonPath("$.maxAge").value(DEFAULT_MAX_AGE));
    }

    @Test
    @Transactional
    public void getNonExistingAgeCategory() throws Exception {
        // Get the ageCategory
        restAgeCategoryMockMvc.perform(get("/api/age-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgeCategory() throws Exception {
        // Initialize the database
        ageCategoryRepository.saveAndFlush(ageCategory);
        int databaseSizeBeforeUpdate = ageCategoryRepository.findAll().size();

        // Update the ageCategory
        AgeCategory updatedAgeCategory = ageCategoryRepository.findOne(ageCategory.getId());
        updatedAgeCategory.name(UPDATED_NAME).minAge(UPDATED_MIN_AGE).maxAge(UPDATED_MAX_AGE);
        AgeCategoryDTO ageCategoryDTO = ageCategoryMapper.ageCategoryToAgeCategoryDTO(updatedAgeCategory);

        restAgeCategoryMockMvc.perform(put("/api/age-categories").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ageCategoryDTO))).andExpect(status().isOk());

        // Validate the AgeCategory in the database
        List<AgeCategory> ageCategories = ageCategoryRepository.findAll();
        assertThat(ageCategories).hasSize(databaseSizeBeforeUpdate);
        AgeCategory testAgeCategory = ageCategories.get(ageCategories.size() - 1);
        assertThat(testAgeCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAgeCategory.getMinAge()).isEqualTo(UPDATED_MIN_AGE);
        assertThat(testAgeCategory.getMaxAge()).isEqualTo(UPDATED_MAX_AGE);
    }

    @Test
    @Transactional
    public void deleteAgeCategory() throws Exception {
        // Initialize the database
        ageCategoryRepository.saveAndFlush(ageCategory);
        int databaseSizeBeforeDelete = ageCategoryRepository.findAll().size();

        // Get the ageCategory
        restAgeCategoryMockMvc.perform(delete("/api/age-categories/{id}",
            ageCategory.getId()).accept(TestUtil.APPLICATION_JSON_UTF8)).andExpect(status().isOk());

        // Validate the database is empty
        List<AgeCategory> ageCategories = ageCategoryRepository.findAll();
        assertThat(ageCategories).hasSize(databaseSizeBeforeDelete - 1);
    }
}
