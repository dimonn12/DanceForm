package by.danceform.app.web.rest;

import by.danceform.app.DanceFormApp;
import by.danceform.app.converter.couple.RegisteredCoupleConverter;
import by.danceform.app.domain.config.DanceClass;
import by.danceform.app.domain.couple.RegisteredCouple;
import by.danceform.app.dto.couple.RegisteredCoupleDTO;
import by.danceform.app.repository.couple.RegisteredCoupleRepository;
import by.danceform.app.service.couple.RegisteredCoupleService;
import by.danceform.app.web.rest.couple.RegisteredCoupleResource;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the RegisteredCoupleResource REST controller.
 *
 * @see RegisteredCoupleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DanceFormApp.class)
public class RegisteredCoupleResourceIntTest {

    private static final String DEFAULT_PARTNER_1_NAME = "A";
    private static final String UPDATED_PARTNER_1_NAME = "B";
    private static final String DEFAULT_PARTNER_1_SURNAME = "A";
    private static final String UPDATED_PARTNER_1_SURNAME = "B";
    private static final String DEFAULT_PARTNER_2_NAME = "A";
    private static final String UPDATED_PARTNER_2_NAME = "B";
    private static final String DEFAULT_PARTNER_2_SURNAME = "A";
    private static final String UPDATED_PARTNER_2_SURNAME = "B";

    private static final LocalDate DEFAULT_PARTNER_1_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PARTNER_1_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PARTNER_2_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PARTNER_2_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_ORGANIZATION = "A";
    private static final String UPDATED_ORGANIZATION = "B";
    private static final String DEFAULT_LOCATION = "A";
    private static final String UPDATED_LOCATION = "B";
    private static final String DEFAULT_TRAINER_1 = "A";
    private static final String UPDATED_TRAINER_1 = "B";

    @Inject
    private RegisteredCoupleRepository registeredCoupleRepository;

    @Inject
    private RegisteredCoupleConverter registeredCoupleConverter;

    @Inject
    private RegisteredCoupleService registeredCoupleService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRegisteredCoupleMockMvc;

    private RegisteredCouple registeredCouple;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RegisteredCoupleResource registeredCoupleResource = new RegisteredCoupleResource();
        ReflectionTestUtils.setField(registeredCoupleResource, "registeredCoupleService", registeredCoupleService);
        this.restRegisteredCoupleMockMvc = MockMvcBuilders.standaloneSetup(registeredCoupleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    /**
     * Create an entity for this test.
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegisteredCouple createEntity(EntityManager em) {
        RegisteredCouple registeredCouple = new RegisteredCouple();
        registeredCouple.setPartner1Name(DEFAULT_PARTNER_1_NAME);
        registeredCouple.setPartner1Surname(DEFAULT_PARTNER_1_SURNAME);
        registeredCouple.setPartner2Name(DEFAULT_PARTNER_2_NAME);
        registeredCouple.setPartner2Surname(DEFAULT_PARTNER_2_SURNAME);
        registeredCouple.setPartner1DateOfBirth(DEFAULT_PARTNER_1_DATE_OF_BIRTH);
        registeredCouple.setPartner2DateOfBirth(DEFAULT_PARTNER_2_DATE_OF_BIRTH);
        registeredCouple.setOrganization(DEFAULT_ORGANIZATION);
        registeredCouple.setLocation(DEFAULT_LOCATION);
        registeredCouple.setTrainer1(DEFAULT_TRAINER_1);
        // Add required entity
        DanceClass partner1DanceClassST = DanceClassResourceIntTest.createEntity(em);
        em.persist(partner1DanceClassST);
        em.flush();
        registeredCouple.setPartner1DanceClassST(partner1DanceClassST);
        // Add required entity
        DanceClass partner1DanceClassLA = DanceClassResourceIntTest.createEntity(em);
        em.persist(partner1DanceClassLA);
        em.flush();
        registeredCouple.setPartner1DanceClassLA(partner1DanceClassLA);
        // Add required entity
        DanceClass partner2DanceClassST = DanceClassResourceIntTest.createEntity(em);
        em.persist(partner2DanceClassST);
        em.flush();
        registeredCouple.setPartner2DanceClassST(partner2DanceClassST);
        // Add required entity
        DanceClass partner2DanceClassLA = DanceClassResourceIntTest.createEntity(em);
        em.persist(partner2DanceClassLA);
        em.flush();
        registeredCouple.setPartner2DanceClassLA(partner2DanceClassLA);
        return registeredCouple;
    }

    @Before
    public void initTest() {
        registeredCouple = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegisteredCouple() throws Exception {
        int databaseSizeBeforeCreate = registeredCoupleRepository.findAll().size();

        // Create the RegisteredCouple
        RegisteredCoupleDTO registeredCoupleDTO = registeredCoupleConverter.convertToDto(registeredCouple);

        restRegisteredCoupleMockMvc.perform(post("/api/registered-couples").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredCoupleDTO))).andExpect(status().isCreated());

        // Validate the RegisteredCouple in the database
        List<RegisteredCouple> registeredCouples = registeredCoupleRepository.findAll();
        assertThat(registeredCouples).hasSize(databaseSizeBeforeCreate + 1);
        RegisteredCouple testRegisteredCouple = registeredCouples.get(registeredCouples.size() - 1);
        assertThat(testRegisteredCouple.getPartner1Name()).isEqualTo(DEFAULT_PARTNER_1_NAME);
        assertThat(testRegisteredCouple.getPartner1Surname()).isEqualTo(DEFAULT_PARTNER_1_SURNAME);
        assertThat(testRegisteredCouple.getPartner2Name()).isEqualTo(DEFAULT_PARTNER_2_NAME);
        assertThat(testRegisteredCouple.getPartner2Surname()).isEqualTo(DEFAULT_PARTNER_2_SURNAME);
        assertThat(testRegisteredCouple.getPartner1DateOfBirth()).isEqualTo(DEFAULT_PARTNER_1_DATE_OF_BIRTH);
        assertThat(testRegisteredCouple.getPartner2DateOfBirth()).isEqualTo(DEFAULT_PARTNER_2_DATE_OF_BIRTH);
        assertThat(testRegisteredCouple.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testRegisteredCouple.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testRegisteredCouple.getTrainer1()).isEqualTo(DEFAULT_TRAINER_1);
    }

    @Test
    @Transactional
    public void checkPartner1NameIsRequired() throws Exception {
        int databaseSizeBeforeTest = registeredCoupleRepository.findAll().size();
        // set the field null
        registeredCouple.setPartner1Name(null);

        // Create the RegisteredCouple, which fails.
        RegisteredCoupleDTO registeredCoupleDTO = registeredCoupleConverter.convertToDto(registeredCouple);

        restRegisteredCoupleMockMvc.perform(post("/api/registered-couples").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredCoupleDTO))).andExpect(status().isBadRequest());

        List<RegisteredCouple> registeredCouples = registeredCoupleRepository.findAll();
        assertThat(registeredCouples).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartner1SurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = registeredCoupleRepository.findAll().size();
        // set the field null
        registeredCouple.setPartner1Surname(null);

        // Create the RegisteredCouple, which fails.
        RegisteredCoupleDTO registeredCoupleDTO = registeredCoupleConverter.convertToDto(registeredCouple);

        restRegisteredCoupleMockMvc.perform(post("/api/registered-couples").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredCoupleDTO))).andExpect(status().isBadRequest());

        List<RegisteredCouple> registeredCouples = registeredCoupleRepository.findAll();
        assertThat(registeredCouples).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartner2NameIsRequired() throws Exception {
        int databaseSizeBeforeTest = registeredCoupleRepository.findAll().size();
        // set the field null
        registeredCouple.setPartner2Name(null);

        // Create the RegisteredCouple, which fails.
        RegisteredCoupleDTO registeredCoupleDTO = registeredCoupleConverter.convertToDto(registeredCouple);

        restRegisteredCoupleMockMvc.perform(post("/api/registered-couples").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredCoupleDTO))).andExpect(status().isBadRequest());

        List<RegisteredCouple> registeredCouples = registeredCoupleRepository.findAll();
        assertThat(registeredCouples).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartner2SurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = registeredCoupleRepository.findAll().size();
        // set the field null
        registeredCouple.setPartner2Surname(null);

        // Create the RegisteredCouple, which fails.
        RegisteredCoupleDTO registeredCoupleDTO = registeredCoupleConverter.convertToDto(registeredCouple);

        restRegisteredCoupleMockMvc.perform(post("/api/registered-couples").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredCoupleDTO))).andExpect(status().isBadRequest());

        List<RegisteredCouple> registeredCouples = registeredCoupleRepository.findAll();
        assertThat(registeredCouples).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartner1DateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = registeredCoupleRepository.findAll().size();
        // set the field null
        registeredCouple.setPartner1DateOfBirth(null);

        // Create the RegisteredCouple, which fails.
        RegisteredCoupleDTO registeredCoupleDTO = registeredCoupleConverter.convertToDto(registeredCouple);

        restRegisteredCoupleMockMvc.perform(post("/api/registered-couples").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredCoupleDTO))).andExpect(status().isBadRequest());

        List<RegisteredCouple> registeredCouples = registeredCoupleRepository.findAll();
        assertThat(registeredCouples).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartner2DateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = registeredCoupleRepository.findAll().size();
        // set the field null
        registeredCouple.setPartner2DateOfBirth(null);

        // Create the RegisteredCouple, which fails.
        RegisteredCoupleDTO registeredCoupleDTO = registeredCoupleConverter.convertToDto(registeredCouple);

        restRegisteredCoupleMockMvc.perform(post("/api/registered-couples").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredCoupleDTO))).andExpect(status().isBadRequest());

        List<RegisteredCouple> registeredCouples = registeredCoupleRepository.findAll();
        assertThat(registeredCouples).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartner1OrganizationIsRequired() throws Exception {
        int databaseSizeBeforeTest = registeredCoupleRepository.findAll().size();
        // set the field null
        registeredCouple.setOrganization(null);

        // Create the RegisteredCouple, which fails.
        RegisteredCoupleDTO registeredCoupleDTO = registeredCoupleConverter.convertToDto(registeredCouple);

        restRegisteredCoupleMockMvc.perform(post("/api/registered-couples").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredCoupleDTO))).andExpect(status().isBadRequest());

        List<RegisteredCouple> registeredCouples = registeredCoupleRepository.findAll();
        assertThat(registeredCouples).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartner1LocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = registeredCoupleRepository.findAll().size();
        // set the field null
        registeredCouple.setLocation(null);

        // Create the RegisteredCouple, which fails.
        RegisteredCoupleDTO registeredCoupleDTO = registeredCoupleConverter.convertToDto(registeredCouple);

        restRegisteredCoupleMockMvc.perform(post("/api/registered-couples").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredCoupleDTO))).andExpect(status().isBadRequest());

        List<RegisteredCouple> registeredCouples = registeredCoupleRepository.findAll();
        assertThat(registeredCouples).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrainer1IsRequired() throws Exception {
        int databaseSizeBeforeTest = registeredCoupleRepository.findAll().size();
        // set the field null
        registeredCouple.setTrainer1(null);

        // Create the RegisteredCouple, which fails.
        RegisteredCoupleDTO registeredCoupleDTO = registeredCoupleConverter.convertToDto(registeredCouple);

        restRegisteredCoupleMockMvc.perform(post("/api/registered-couples").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredCoupleDTO))).andExpect(status().isBadRequest());

        List<RegisteredCouple> registeredCouples = registeredCoupleRepository.findAll();
        assertThat(registeredCouples).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegisteredCouples() throws Exception {
        // Initialize the database
        registeredCoupleRepository.saveAndFlush(registeredCouple);

        // Get all the registeredCouples
        restRegisteredCoupleMockMvc.perform(get("/api/registered-couples?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registeredCouple.getId().intValue())))
            .andExpect(jsonPath("$.[*].partner1Name").value(hasItem(DEFAULT_PARTNER_1_NAME.toString())))
            .andExpect(jsonPath("$.[*].partner1Surname").value(hasItem(DEFAULT_PARTNER_1_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].partner2Name").value(hasItem(DEFAULT_PARTNER_2_NAME.toString())))
            .andExpect(jsonPath("$.[*].partner2Surname").value(hasItem(DEFAULT_PARTNER_2_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].partner1DateOfBirth").value(hasItem(DEFAULT_PARTNER_1_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].partner2DateOfBirth").value(hasItem(DEFAULT_PARTNER_2_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].organization").value(hasItem(DEFAULT_ORGANIZATION.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].trainer1").value(hasItem(DEFAULT_TRAINER_1.toString())));
    }

    @Test
    @Transactional
    public void getRegisteredCouple() throws Exception {
        // Initialize the database
        registeredCoupleRepository.saveAndFlush(registeredCouple);

        // Get the registeredCouple
        restRegisteredCoupleMockMvc.perform(get("/api/registered-couples/{id}", registeredCouple.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(registeredCouple.getId().intValue()))
            .andExpect(jsonPath("$.partner1Name").value(DEFAULT_PARTNER_1_NAME.toString()))
            .andExpect(jsonPath("$.partner1Surname").value(DEFAULT_PARTNER_1_SURNAME.toString()))
            .andExpect(jsonPath("$.partner2Name").value(DEFAULT_PARTNER_2_NAME.toString()))
            .andExpect(jsonPath("$.partner2Surname").value(DEFAULT_PARTNER_2_SURNAME.toString()))
            .andExpect(jsonPath("$.partner1DateOfBirth").value(DEFAULT_PARTNER_1_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.partner2DateOfBirth").value(DEFAULT_PARTNER_2_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.trainer1").value(DEFAULT_TRAINER_1.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRegisteredCouple() throws Exception {
        // Get the registeredCouple
        restRegisteredCoupleMockMvc.perform(get("/api/registered-couples/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegisteredCouple() throws Exception {
        // Initialize the database
        registeredCoupleRepository.saveAndFlush(registeredCouple);
        int databaseSizeBeforeUpdate = registeredCoupleRepository.findAll().size();

        // Update the registeredCouple
        RegisteredCouple updatedRegisteredCouple = registeredCoupleRepository.findOne(registeredCouple.getId());
        updatedRegisteredCouple.setPartner1Name(UPDATED_PARTNER_1_NAME);
        updatedRegisteredCouple.setPartner1Surname(UPDATED_PARTNER_1_SURNAME);
        updatedRegisteredCouple.setPartner2Name(UPDATED_PARTNER_2_NAME);
        updatedRegisteredCouple.setPartner2Surname(UPDATED_PARTNER_2_SURNAME);
        updatedRegisteredCouple.setPartner1DateOfBirth(UPDATED_PARTNER_1_DATE_OF_BIRTH);
        updatedRegisteredCouple.setPartner2DateOfBirth(UPDATED_PARTNER_2_DATE_OF_BIRTH);
        updatedRegisteredCouple.setOrganization(UPDATED_ORGANIZATION);
        updatedRegisteredCouple.setLocation(UPDATED_LOCATION);
        updatedRegisteredCouple.setTrainer1(UPDATED_TRAINER_1);
        RegisteredCoupleDTO registeredCoupleDTO = registeredCoupleConverter.convertToDto(updatedRegisteredCouple);

        restRegisteredCoupleMockMvc.perform(put("/api/registered-couples").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredCoupleDTO))).andExpect(status().isOk());

        // Validate the RegisteredCouple in the database
        List<RegisteredCouple> registeredCouples = registeredCoupleRepository.findAll();
        assertThat(registeredCouples).hasSize(databaseSizeBeforeUpdate);
        RegisteredCouple testRegisteredCouple = registeredCouples.get(registeredCouples.size() - 1);
        assertThat(testRegisteredCouple.getPartner1Name()).isEqualTo(UPDATED_PARTNER_1_NAME);
        assertThat(testRegisteredCouple.getPartner1Surname()).isEqualTo(UPDATED_PARTNER_1_SURNAME);
        assertThat(testRegisteredCouple.getPartner2Name()).isEqualTo(UPDATED_PARTNER_2_NAME);
        assertThat(testRegisteredCouple.getPartner2Surname()).isEqualTo(UPDATED_PARTNER_2_SURNAME);
        assertThat(testRegisteredCouple.getPartner1DateOfBirth()).isEqualTo(UPDATED_PARTNER_1_DATE_OF_BIRTH);
        assertThat(testRegisteredCouple.getPartner2DateOfBirth()).isEqualTo(UPDATED_PARTNER_2_DATE_OF_BIRTH);
        assertThat(testRegisteredCouple.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testRegisteredCouple.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testRegisteredCouple.getTrainer1()).isEqualTo(UPDATED_TRAINER_1);
    }

    @Test
    @Transactional
    public void deleteRegisteredCouple() throws Exception {
        // Initialize the database
        registeredCoupleRepository.saveAndFlush(registeredCouple);
        int databaseSizeBeforeDelete = registeredCoupleRepository.findAll().size();

        // Get the registeredCouple
        restRegisteredCoupleMockMvc.perform(delete("/api/registered-couples/{id}", registeredCouple.getId()).accept(
            TestUtil.APPLICATION_JSON_UTF8)).andExpect(status().isOk());

        // Validate the database is empty
        List<RegisteredCouple> registeredCouples = registeredCoupleRepository.findAll();
        assertThat(registeredCouples).hasSize(databaseSizeBeforeDelete - 1);
    }
}
