package by.danceform.app.web.rest;

import by.danceform.app.DanceFormApp;
import by.danceform.app.converter.competition.CompetitionCategoryConverter;
import by.danceform.app.domain.competition.CompetitionCategory;
import by.danceform.app.dto.competition.CompetitionCategoryDTO;
import by.danceform.app.repository.competition.CompetitionCategoryRepository;
import by.danceform.app.service.competition.CompetitionCategoryService;
import by.danceform.app.web.rest.competition.CompetitionCategoryResource;
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
 * Test class for the CompetitionCategoryResource REST controller.
 *
 * @see CompetitionCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DanceFormApp.class)
public class CompetitionCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "A";
    private static final String UPDATED_NAME = "B";
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Boolean DEFAULT_CHECK_MIN_AGE = false;
    private static final Boolean UPDATED_CHECK_MIN_AGE = true;

    private static final Boolean DEFAULT_CHECK_MAX_AGE = false;
    private static final Boolean UPDATED_CHECK_MAX_AGE = true;

    private static final Long DEFAULT_COMPETITION_ID = 1L;
    private static final Long UPDATED_COMPETITION_ID = 2L;

    @Inject
    private CompetitionCategoryRepository competitionCategoryRepository;

    @Inject
    private CompetitionCategoryConverter competitionCategoryConverter;

    @Inject
    private CompetitionCategoryService competitionCategoryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCompetitionCategoryMockMvc;

    private CompetitionCategory competitionCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompetitionCategoryResource competitionCategoryResource = new CompetitionCategoryResource();
        ReflectionTestUtils.setField(competitionCategoryResource,
            "competitionCategoryService",
            competitionCategoryService);
        this.restCompetitionCategoryMockMvc = MockMvcBuilders.standaloneSetup(competitionCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    /**
     * Create an entity for this test.
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitionCategory createEntity(EntityManager em) {
        CompetitionCategory competitionCategory = new CompetitionCategory();
        competitionCategory.setName(DEFAULT_NAME);
        competitionCategory.setDescription(DEFAULT_DESCRIPTION);
        competitionCategory.setActive(DEFAULT_ACTIVE);
        competitionCategory.setCheckMinAge(DEFAULT_CHECK_MIN_AGE);
        competitionCategory.setCheckMaxAge(DEFAULT_CHECK_MAX_AGE);
        competitionCategory.setCompetitionId(DEFAULT_COMPETITION_ID);
        return competitionCategory;
    }

    @Before
    public void initTest() {
        competitionCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitionCategory() throws Exception {
        int databaseSizeBeforeCreate = competitionCategoryRepository.findAll().size();

        // Create the CompetitionCategory
        CompetitionCategoryDTO competitionCategoryDTO = competitionCategoryConverter.convertToDto(competitionCategory);

        restCompetitionCategoryMockMvc.perform(post("/api/competition-categories").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionCategoryDTO))).andExpect(status().isCreated());

        // Validate the CompetitionCategory in the database
        List<CompetitionCategory> competitionCategories = competitionCategoryRepository.findAll();
        assertThat(competitionCategories).hasSize(databaseSizeBeforeCreate + 1);
        CompetitionCategory testCompetitionCategory = competitionCategories.get(competitionCategories.size() - 1);
        assertThat(testCompetitionCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompetitionCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompetitionCategory.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testCompetitionCategory.isCheckMinAge()).isEqualTo(DEFAULT_CHECK_MIN_AGE);
        assertThat(testCompetitionCategory.isCheckMaxAge()).isEqualTo(DEFAULT_CHECK_MAX_AGE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitionCategoryRepository.findAll().size();
        // set the field null
        competitionCategory.setName(null);

        // Create the CompetitionCategory, which fails.
        CompetitionCategoryDTO competitionCategoryDTO = competitionCategoryConverter.convertToDto(competitionCategory);

        restCompetitionCategoryMockMvc.perform(post("/api/competition-categories").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionCategoryDTO))).andExpect(status().isBadRequest());

        List<CompetitionCategory> competitionCategories = competitionCategoryRepository.findAll();
        assertThat(competitionCategories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompetitionCategories() throws Exception {
        // Initialize the database
        competitionCategoryRepository.saveAndFlush(competitionCategory);

        // Get all the competitionCategories
        restCompetitionCategoryMockMvc.perform(get("/api/competition-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitionCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].checkMinAge").value(hasItem(DEFAULT_CHECK_MIN_AGE.booleanValue())))
            .andExpect(jsonPath("$.[*].checkMaxAge").value(hasItem(DEFAULT_CHECK_MAX_AGE.booleanValue())));
    }

    @Test
    @Transactional
    public void getCompetitionCategory() throws Exception {
        // Initialize the database
        competitionCategoryRepository.saveAndFlush(competitionCategory);

        // Get the competitionCategory
        restCompetitionCategoryMockMvc.perform(get("/api/competition-categories/{id}", competitionCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competitionCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.checkMinAge").value(DEFAULT_CHECK_MIN_AGE.booleanValue()))
            .andExpect(jsonPath("$.checkMaxAge").value(DEFAULT_CHECK_MAX_AGE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompetitionCategory() throws Exception {
        // Get the competitionCategory
        restCompetitionCategoryMockMvc.perform(get("/api/competition-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitionCategory() throws Exception {
        // Initialize the database
        competitionCategoryRepository.saveAndFlush(competitionCategory);
        int databaseSizeBeforeUpdate = competitionCategoryRepository.findAll().size();

        // Update the competitionCategory
        CompetitionCategory updatedCompetitionCategory = competitionCategoryRepository.findOne(competitionCategory.getId());
        updatedCompetitionCategory.setName(UPDATED_NAME);
        updatedCompetitionCategory.setDescription(UPDATED_DESCRIPTION);
        updatedCompetitionCategory.setActive(UPDATED_ACTIVE);
        updatedCompetitionCategory.setCheckMinAge(UPDATED_CHECK_MIN_AGE);
        updatedCompetitionCategory.setCheckMaxAge(UPDATED_CHECK_MAX_AGE);
        updatedCompetitionCategory.setCompetitionId(UPDATED_COMPETITION_ID);
        CompetitionCategoryDTO competitionCategoryDTO = competitionCategoryConverter.convertToDto(
            updatedCompetitionCategory);

        restCompetitionCategoryMockMvc.perform(put("/api/competition-categories").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitionCategoryDTO))).andExpect(status().isOk());

        // Validate the CompetitionCategory in the database
        List<CompetitionCategory> competitionCategories = competitionCategoryRepository.findAll();
        assertThat(competitionCategories).hasSize(databaseSizeBeforeUpdate);
        CompetitionCategory testCompetitionCategory = competitionCategories.get(competitionCategories.size() - 1);
        assertThat(testCompetitionCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompetitionCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompetitionCategory.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testCompetitionCategory.isCheckMinAge()).isEqualTo(UPDATED_CHECK_MIN_AGE);
        assertThat(testCompetitionCategory.isCheckMaxAge()).isEqualTo(UPDATED_CHECK_MAX_AGE);
    }

    @Test
    @Transactional
    public void deleteCompetitionCategory() throws Exception {
        // Initialize the database
        competitionCategoryRepository.saveAndFlush(competitionCategory);
        int databaseSizeBeforeDelete = competitionCategoryRepository.findAll().size();

        // Get the competitionCategory
        restCompetitionCategoryMockMvc.perform(delete("/api/competition-categories/{id}",
            competitionCategory.getId()).accept(TestUtil.APPLICATION_JSON_UTF8)).andExpect(status().isOk());

        // Validate the database is empty
        List<CompetitionCategory> competitionCategories = competitionCategoryRepository.findAll();
        assertThat(competitionCategories).hasSize(databaseSizeBeforeDelete - 1);
    }
}
