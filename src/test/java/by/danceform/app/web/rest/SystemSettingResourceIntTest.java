package by.danceform.app.web.rest;

import by.danceform.app.DanceFormApp;
import by.danceform.app.converter.system.SystemSettingConverter;
import by.danceform.app.domain.system.SystemSetting;
import by.danceform.app.dto.system.SystemSettingDTO;
import by.danceform.app.repository.system.SystemSettingRepository;
import by.danceform.app.service.system.SystemSettingService;
import by.danceform.app.web.rest.system.SystemSettingResource;
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
 * Test class for the SystemSettingResource REST controller.
 *
 * @see SystemSettingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DanceFormApp.class)
public class SystemSettingResourceIntTest {

    private static final String DEFAULT_NAME = "A";
    private static final String UPDATED_NAME = "B";
    private static final String DEFAULT_VALUE = "A";
    private static final String UPDATED_VALUE = "B";

    @Inject
    private SystemSettingRepository systemSettingRepository;

    @Inject
    private SystemSettingConverter systemSettingConverter;

    @Inject
    private SystemSettingService systemSettingService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSystemSettingMockMvc;

    private SystemSetting systemSetting;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SystemSettingResource systemSettingResource = new SystemSettingResource();
        ReflectionTestUtils.setField(systemSettingResource, "systemSettingService", systemSettingService);
        this.restSystemSettingMockMvc = MockMvcBuilders.standaloneSetup(systemSettingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    /**
     * Create an entity for this test.
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemSetting createEntity(EntityManager em) {
        SystemSetting systemSetting = new SystemSetting();
        systemSetting.setName(DEFAULT_NAME);
        systemSetting.setValue(DEFAULT_VALUE);
        return systemSetting;
    }

    @Before
    public void initTest() {
        systemSetting = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystemSetting() throws Exception {
        int databaseSizeBeforeCreate = systemSettingRepository.findAll().size();

        // Create the SystemSetting
        SystemSettingDTO systemSettingDTO = systemSettingConverter.convertToDto(systemSetting);

        restSystemSettingMockMvc.perform(post("/api/system-settings").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO))).andExpect(status().isCreated());

        // Validate the SystemSetting in the database
        List<SystemSetting> systemSettings = systemSettingRepository.findAll();
        assertThat(systemSettings).hasSize(databaseSizeBeforeCreate + 1);
        SystemSetting testSystemSetting = systemSettings.get(systemSettings.size() - 1);
        assertThat(testSystemSetting.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSystemSetting.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemSettingRepository.findAll().size();
        // set the field null
        systemSetting.setName(null);

        // Create the SystemSetting, which fails.
        SystemSettingDTO systemSettingDTO = systemSettingConverter.convertToDto(systemSetting);

        restSystemSettingMockMvc.perform(post("/api/system-settings").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO))).andExpect(status().isBadRequest());

        List<SystemSetting> systemSettings = systemSettingRepository.findAll();
        assertThat(systemSettings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemSettingRepository.findAll().size();
        // set the field null
        systemSetting.setValue(null);

        // Create the SystemSetting, which fails.
        SystemSettingDTO systemSettingDTO = systemSettingConverter.convertToDto(systemSetting);

        restSystemSettingMockMvc.perform(post("/api/system-settings").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO))).andExpect(status().isBadRequest());

        List<SystemSetting> systemSettings = systemSettingRepository.findAll();
        assertThat(systemSettings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSystemSettings() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get all the systemSettings
        restSystemSettingMockMvc.perform(get("/api/system-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getSystemSetting() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);

        // Get the systemSetting
        restSystemSettingMockMvc.perform(get("/api/system-settings/{id}", systemSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(systemSetting.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSystemSetting() throws Exception {
        // Get the systemSetting
        restSystemSettingMockMvc.perform(get("/api/system-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystemSetting() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);
        int databaseSizeBeforeUpdate = systemSettingRepository.findAll().size();

        // Update the systemSetting
        SystemSetting updatedSystemSetting = systemSettingRepository.findOne(systemSetting.getId());
        updatedSystemSetting.setName(UPDATED_NAME);
        updatedSystemSetting.setValue(UPDATED_VALUE);
        SystemSettingDTO systemSettingDTO = systemSettingConverter.convertToDto(updatedSystemSetting);

        restSystemSettingMockMvc.perform(put("/api/system-settings").contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemSettingDTO))).andExpect(status().isOk());

        // Validate the SystemSetting in the database
        List<SystemSetting> systemSettings = systemSettingRepository.findAll();
        assertThat(systemSettings).hasSize(databaseSizeBeforeUpdate);
        SystemSetting testSystemSetting = systemSettings.get(systemSettings.size() - 1);
        assertThat(testSystemSetting.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSystemSetting.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteSystemSetting() throws Exception {
        // Initialize the database
        systemSettingRepository.saveAndFlush(systemSetting);
        int databaseSizeBeforeDelete = systemSettingRepository.findAll().size();

        // Get the systemSetting
        restSystemSettingMockMvc.perform(delete("/api/system-settings/{id}",
            systemSetting.getId()).accept(TestUtil.APPLICATION_JSON_UTF8)).andExpect(status().isOk());

        // Validate the database is empty
        List<SystemSetting> systemSettings = systemSettingRepository.findAll();
        assertThat(systemSettings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
