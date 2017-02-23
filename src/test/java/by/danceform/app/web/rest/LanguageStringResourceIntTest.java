package by.danceform.app.web.rest;

import by.danceform.app.DanceFormApp;

import by.danceform.app.web.rest.system.LanguageStringResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the LanguageStringResource REST controller.
 *
 * @see LanguageStringResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DanceFormApp.class)
public class LanguageStringResourceIntTest {

    @Test
    public void test() {

    }
   /* private static final String DEFAULT_LANG = "AA";
    private static final String UPDATED_LANG = "BB";
    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_VALUE = "A";
    private static final String UPDATED_VALUE = "B";

    @Inject
    private LanguageStringRepository languageStringRepository;

    @Inject
    private LanguageStringMapper languageStringMapper;

    @Inject
    private LanguageStringService languageStringService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLanguageStringMockMvc;

    private LanguageString languageString;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LanguageStringResource languageStringResource = new LanguageStringResource();
        ReflectionTestUtils.setField(languageStringResource, "languageStringService", languageStringService);
        this.restLanguageStringMockMvc = MockMvcBuilders.standaloneSetup(languageStringResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    *//**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     *//*
    public static LanguageString createEntity(EntityManager em) {
        LanguageString languageString = new LanguageString()
                .lang(DEFAULT_LANG)
                .name(DEFAULT_NAME)
                .value(DEFAULT_VALUE);
        return languageString;
    }

    @Before
    public void initTest() {
        languageString = createEntity(em);
    }

    @Test
    @Transactional
    public void createLanguageString() throws Exception {
        int databaseSizeBeforeCreate = languageStringRepository.findAll().size();

        // Create the LanguageString
        LanguageStringDTO languageStringDTO = languageStringMapper.languageStringToLanguageStringDTO(languageString);

        restLanguageStringMockMvc.perform(post("/api/language-strings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(languageStringDTO)))
                .andExpect(status().isCreated());

        // Validate the LanguageString in the database
        List<LanguageString> languageStrings = languageStringRepository.findAll();
        assertThat(languageStrings).hasSize(databaseSizeBeforeCreate + 1);
        LanguageString testLanguageString = languageStrings.get(languageStrings.size() - 1);
        assertThat(testLanguageString.getLang()).isEqualTo(DEFAULT_LANG);
        assertThat(testLanguageString.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLanguageString.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkLangIsRequired() throws Exception {
        int databaseSizeBeforeTest = languageStringRepository.findAll().size();
        // set the field null
        languageString.setLang(null);

        // Create the LanguageString, which fails.
        LanguageStringDTO languageStringDTO = languageStringMapper.languageStringToLanguageStringDTO(languageString);

        restLanguageStringMockMvc.perform(post("/api/language-strings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(languageStringDTO)))
                .andExpect(status().isBadRequest());

        List<LanguageString> languageStrings = languageStringRepository.findAll();
        assertThat(languageStrings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = languageStringRepository.findAll().size();
        // set the field null
        languageString.setName(null);

        // Create the LanguageString, which fails.
        LanguageStringDTO languageStringDTO = languageStringMapper.languageStringToLanguageStringDTO(languageString);

        restLanguageStringMockMvc.perform(post("/api/language-strings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(languageStringDTO)))
                .andExpect(status().isBadRequest());

        List<LanguageString> languageStrings = languageStringRepository.findAll();
        assertThat(languageStrings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = languageStringRepository.findAll().size();
        // set the field null
        languageString.setValue(null);

        // Create the LanguageString, which fails.
        LanguageStringDTO languageStringDTO = languageStringMapper.languageStringToLanguageStringDTO(languageString);

        restLanguageStringMockMvc.perform(post("/api/language-strings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(languageStringDTO)))
                .andExpect(status().isBadRequest());

        List<LanguageString> languageStrings = languageStringRepository.findAll();
        assertThat(languageStrings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLanguageStrings() throws Exception {
        // Initialize the database
        languageStringRepository.saveAndFlush(languageString);

        // Get all the languageStrings
        restLanguageStringMockMvc.perform(get("/api/language-strings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(languageString.getId().intValue())))
                .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getLanguageString() throws Exception {
        // Initialize the database
        languageStringRepository.saveAndFlush(languageString);

        // Get the languageString
        restLanguageStringMockMvc.perform(get("/api/language-strings/{id}", languageString.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(languageString.getId().intValue()))
            .andExpect(jsonPath("$.lang").value(DEFAULT_LANG.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLanguageString() throws Exception {
        // Get the languageString
        restLanguageStringMockMvc.perform(get("/api/language-strings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLanguageString() throws Exception {
        // Initialize the database
        languageStringRepository.saveAndFlush(languageString);
        int databaseSizeBeforeUpdate = languageStringRepository.findAll().size();

        // Update the languageString
        LanguageString updatedLanguageString = languageStringRepository.findOne(languageString.getId());
        updatedLanguageString
                .lang(UPDATED_LANG)
                .name(UPDATED_NAME)
                .value(UPDATED_VALUE);
        LanguageStringDTO languageStringDTO = languageStringMapper.languageStringToLanguageStringDTO(updatedLanguageString);

        restLanguageStringMockMvc.perform(put("/api/language-strings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(languageStringDTO)))
                .andExpect(status().isOk());

        // Validate the LanguageString in the database
        List<LanguageString> languageStrings = languageStringRepository.findAll();
        assertThat(languageStrings).hasSize(databaseSizeBeforeUpdate);
        LanguageString testLanguageString = languageStrings.get(languageStrings.size() - 1);
        assertThat(testLanguageString.getLang()).isEqualTo(UPDATED_LANG);
        assertThat(testLanguageString.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLanguageString.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteLanguageString() throws Exception {
        // Initialize the database
        languageStringRepository.saveAndFlush(languageString);
        int databaseSizeBeforeDelete = languageStringRepository.findAll().size();

        // Get the languageString
        restLanguageStringMockMvc.perform(delete("/api/language-strings/{id}", languageString.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LanguageString> languageStrings = languageStringRepository.findAll();
        assertThat(languageStrings).hasSize(databaseSizeBeforeDelete - 1);
    }*/
}
