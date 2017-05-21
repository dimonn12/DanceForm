package by.danceform.app.web.rest;

import by.danceform.app.DanceFormApp;
import by.danceform.app.web.rest.competition.CompetitionNotificationResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class for the CompetitionNotificationResource REST controller.
 *
 * @see CompetitionNotificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DanceFormApp.class)
public class CompetitionNotificationResourceIntTest {

    @Test
    public void test() {

    }

    /*private static final Long DEFAULT_COMPETITION_ID = 1L;
    private static final Long UPDATED_COMPETITION_ID = 2L;
    private static final String DEFAULT_MESSAGE = "A";
    private static final String UPDATED_MESSAGE = "B";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    @Inject
    private CompetitionNotificationRepository competitionNotificationRepository;

    @Inject
    private CompetitionNotificationMapper competitionNotificationMapper;

    @Inject
    private CompetitionNotificationService competitionNotificationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCompetitionNotificationMockMvc;

    private CompetitionNotification competitionNotification;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompetitionNotificationResource competitionNotificationResource = new CompetitionNotificationResource();
        ReflectionTestUtils.setField(competitionNotificationResource, "competitionNotificationService", competitionNotificationService);
        this.restCompetitionNotificationMockMvc = MockMvcBuilders.standaloneSetup(competitionNotificationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    *//**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
    public static CompetitionNotification createEntity(EntityManager em) {
        CompetitionNotification competitionNotification = new CompetitionNotification()
                .competitionId(DEFAULT_COMPETITION_ID)
                .message(DEFAULT_MESSAGE)
                .isActive(DEFAULT_IS_ACTIVE);
        return competitionNotification;
    }

    @Before
    public void initTest() {
        competitionNotification = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitionNotification() throws Exception {
        int databaseSizeBeforeCreate = competitionNotificationRepository.findAll().size();

        // Create the CompetitionNotification
        CompetitionNotificationDTO competitionNotificationDTO = competitionNotificationMapper.competitionNotificationToCompetitionNotificationDTO(competitionNotification);

        restCompetitionNotificationMockMvc.perform(post("/api/competition-notifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(competitionNotificationDTO)))
                .andExpect(status().isCreated());

        // Validate the CompetitionNotification in the database
        List<CompetitionNotification> competitionNotifications = competitionNotificationRepository.findAll();
        assertThat(competitionNotifications).hasSize(databaseSizeBeforeCreate + 1);
        CompetitionNotification testCompetitionNotification = competitionNotifications.get(competitionNotifications.size() - 1);
        assertThat(testCompetitionNotification.getCompetitionId()).isEqualTo(DEFAULT_COMPETITION_ID);
        assertThat(testCompetitionNotification.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testCompetitionNotification.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void checkCompetitionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitionNotificationRepository.findAll().size();
        // set the field null
        competitionNotification.setCompetitionId(null);

        // Create the CompetitionNotification, which fails.
        CompetitionNotificationDTO competitionNotificationDTO = competitionNotificationMapper.competitionNotificationToCompetitionNotificationDTO(competitionNotification);

        restCompetitionNotificationMockMvc.perform(post("/api/competition-notifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(competitionNotificationDTO)))
                .andExpect(status().isBadRequest());

        List<CompetitionNotification> competitionNotifications = competitionNotificationRepository.findAll();
        assertThat(competitionNotifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = competitionNotificationRepository.findAll().size();
        // set the field null
        competitionNotification.setMessage(null);

        // Create the CompetitionNotification, which fails.
        CompetitionNotificationDTO competitionNotificationDTO = competitionNotificationMapper.competitionNotificationToCompetitionNotificationDTO(competitionNotification);

        restCompetitionNotificationMockMvc.perform(post("/api/competition-notifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(competitionNotificationDTO)))
                .andExpect(status().isBadRequest());

        List<CompetitionNotification> competitionNotifications = competitionNotificationRepository.findAll();
        assertThat(competitionNotifications).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompetitionNotifications() throws Exception {
        // Initialize the database
        competitionNotificationRepository.saveAndFlush(competitionNotification);

        // Get all the competitionNotifications
        restCompetitionNotificationMockMvc.perform(get("/api/competition-notifications?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(competitionNotification.getId().intValue())))
                .andExpect(jsonPath("$.[*].competitionId").value(hasItem(DEFAULT_COMPETITION_ID.intValue())))
                .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getCompetitionNotification() throws Exception {
        // Initialize the database
        competitionNotificationRepository.saveAndFlush(competitionNotification);

        // Get the competitionNotification
        restCompetitionNotificationMockMvc.perform(get("/api/competition-notifications/{id}", competitionNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competitionNotification.getId().intValue()))
            .andExpect(jsonPath("$.competitionId").value(DEFAULT_COMPETITION_ID.intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompetitionNotification() throws Exception {
        // Get the competitionNotification
        restCompetitionNotificationMockMvc.perform(get("/api/competition-notifications/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitionNotification() throws Exception {
        // Initialize the database
        competitionNotificationRepository.saveAndFlush(competitionNotification);
        int databaseSizeBeforeUpdate = competitionNotificationRepository.findAll().size();

        // Update the competitionNotification
        CompetitionNotification updatedCompetitionNotification = competitionNotificationRepository.findOne(competitionNotification.getId());
        updatedCompetitionNotification
                .competitionId(UPDATED_COMPETITION_ID)
                .message(UPDATED_MESSAGE)
                .isActive(UPDATED_IS_ACTIVE);
        CompetitionNotificationDTO competitionNotificationDTO = competitionNotificationMapper.competitionNotificationToCompetitionNotificationDTO(updatedCompetitionNotification);

        restCompetitionNotificationMockMvc.perform(put("/api/competition-notifications")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(competitionNotificationDTO)))
                .andExpect(status().isOk());

        // Validate the CompetitionNotification in the database
        List<CompetitionNotification> competitionNotifications = competitionNotificationRepository.findAll();
        assertThat(competitionNotifications).hasSize(databaseSizeBeforeUpdate);
        CompetitionNotification testCompetitionNotification = competitionNotifications.get(competitionNotifications.size() - 1);
        assertThat(testCompetitionNotification.getCompetitionId()).isEqualTo(UPDATED_COMPETITION_ID);
        assertThat(testCompetitionNotification.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testCompetitionNotification.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    public void deleteCompetitionNotification() throws Exception {
        // Initialize the database
        competitionNotificationRepository.saveAndFlush(competitionNotification);
        int databaseSizeBeforeDelete = competitionNotificationRepository.findAll().size();

        // Get the competitionNotification
        restCompetitionNotificationMockMvc.perform(delete("/api/competition-notifications/{id}", competitionNotification.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CompetitionNotification> competitionNotifications = competitionNotificationRepository.findAll();
        assertThat(competitionNotifications).hasSize(databaseSizeBeforeDelete - 1);
    }*/
}
