package ir.redmind.paasho.web.rest;

import ir.redmind.paasho.PaashoApp;

import ir.redmind.paasho.domain.Setting;
import ir.redmind.paasho.repository.SettingRepository;
import ir.redmind.paasho.repository.search.SettingSearchRepository;
import ir.redmind.paasho.service.SettingService;
import ir.redmind.paasho.service.dto.SettingDTO;
import ir.redmind.paasho.service.mapper.SettingMapper;
import ir.redmind.paasho.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static ir.redmind.paasho.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ir.redmind.paasho.domain.enumeration.SettingKey;
/**
 * Test class for the SettingResource REST controller.
 *
 * @see SettingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaashoApp.class)
public class SettingResourceIntTest {

    private static final SettingKey DEFAULT_KEY = SettingKey.APP_VERSION;
    private static final SettingKey UPDATED_KEY = SettingKey.APP_VERSION;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    private SettingService settingService;

    /**
     * This repository is mocked in the ir.redmind.paasho.repository.search test package.
     *
     * @see ir.redmind.paasho.repository.search.SettingSearchRepositoryMockConfiguration
     */
    @Autowired
    private SettingSearchRepository mockSettingSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSettingMockMvc;

    private Setting setting;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SettingResource settingResource = new SettingResource(settingService);
        this.restSettingMockMvc = MockMvcBuilders.standaloneSetup(settingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Setting createEntity(EntityManager em) {
        Setting setting = new Setting()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return setting;
    }

    @Before
    public void initTest() {
        setting = createEntity(em);
    }

    @Test
    @Transactional
    public void createSetting() throws Exception {
        int databaseSizeBeforeCreate = settingRepository.findAll().size();

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(setting);
        restSettingMockMvc.perform(post("/api/settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isCreated());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeCreate + 1);
        Setting testSetting = settingList.get(settingList.size() - 1);
        assertThat(testSetting.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testSetting.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the Setting in Elasticsearch
        verify(mockSettingSearchRepository, times(1)).save(testSetting);
    }

    @Test
    @Transactional
    public void createSettingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = settingRepository.findAll().size();

        // Create the Setting with an existing ID
        setting.setId(1L);
        SettingDTO settingDTO = settingMapper.toDto(setting);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSettingMockMvc.perform(post("/api/settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeCreate);

        // Validate the Setting in Elasticsearch
        verify(mockSettingSearchRepository, times(0)).save(setting);
    }

    @Test
    @Transactional
    public void getAllSettings() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList
        restSettingMockMvc.perform(get("/api/settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(setting.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get the setting
        restSettingMockMvc.perform(get("/api/settings/{id}", setting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(setting.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSetting() throws Exception {
        // Get the setting
        restSettingMockMvc.perform(get("/api/settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        int databaseSizeBeforeUpdate = settingRepository.findAll().size();

        // Update the setting
        Setting updatedSetting = settingRepository.findById(setting.getId()).get();
        // Disconnect from session so that the updates on updatedSetting are not directly saved in db
        em.detach(updatedSetting);
        updatedSetting
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);
        SettingDTO settingDTO = settingMapper.toDto(updatedSetting);

        restSettingMockMvc.perform(put("/api/settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isOk());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
        Setting testSetting = settingList.get(settingList.size() - 1);
        assertThat(testSetting.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testSetting.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the Setting in Elasticsearch
        verify(mockSettingSearchRepository, times(1)).save(testSetting);
    }

    @Test
    @Transactional
    public void updateNonExistingSetting() throws Exception {
        int databaseSizeBeforeUpdate = settingRepository.findAll().size();

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(setting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingMockMvc.perform(put("/api/settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Setting in Elasticsearch
        verify(mockSettingSearchRepository, times(0)).save(setting);
    }

    @Test
    @Transactional
    public void deleteSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        int databaseSizeBeforeDelete = settingRepository.findAll().size();

        // Delete the setting
        restSettingMockMvc.perform(delete("/api/settings/{id}", setting.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Setting in Elasticsearch
        verify(mockSettingSearchRepository, times(1)).deleteById(setting.getId());
    }

    @Test
    @Transactional
    public void searchSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);
        when(mockSettingSearchRepository.search(queryStringQuery("id:" + setting.getId())))
            .thenReturn(Collections.singletonList(setting));
        // Search the setting
        restSettingMockMvc.perform(get("/api/_search/settings?query=id:" + setting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(setting.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Setting.class);
        Setting setting1 = new Setting();
        setting1.setId(1L);
        Setting setting2 = new Setting();
        setting2.setId(setting1.getId());
        assertThat(setting1).isEqualTo(setting2);
        setting2.setId(2L);
        assertThat(setting1).isNotEqualTo(setting2);
        setting1.setId(null);
        assertThat(setting1).isNotEqualTo(setting2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SettingDTO.class);
        SettingDTO settingDTO1 = new SettingDTO();
        settingDTO1.setId(1L);
        SettingDTO settingDTO2 = new SettingDTO();
        assertThat(settingDTO1).isNotEqualTo(settingDTO2);
        settingDTO2.setId(settingDTO1.getId());
        assertThat(settingDTO1).isEqualTo(settingDTO2);
        settingDTO2.setId(2L);
        assertThat(settingDTO1).isNotEqualTo(settingDTO2);
        settingDTO1.setId(null);
        assertThat(settingDTO1).isNotEqualTo(settingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(settingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(settingMapper.fromId(null)).isNull();
    }
}
