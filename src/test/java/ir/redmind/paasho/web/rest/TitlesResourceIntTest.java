package ir.redmind.paasho.web.rest;

import ir.redmind.paasho.PaashoApp;

import ir.redmind.paasho.domain.Titles;
import ir.redmind.paasho.repository.TitlesRepository;
import ir.redmind.paasho.repository.search.TitlesSearchRepository;
import ir.redmind.paasho.service.TitlesService;
import ir.redmind.paasho.service.dto.TitlesDTO;
import ir.redmind.paasho.service.mapper.TitlesMapper;
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

/**
 * Test class for the TitlesResource REST controller.
 *
 * @see TitlesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaashoApp.class)
public class TitlesResourceIntTest {

    @Autowired
    private TitlesRepository titlesRepository;

    @Autowired
    private TitlesMapper titlesMapper;

    @Autowired
    private TitlesService titlesService;

    /**
     * This repository is mocked in the ir.redmind.paasho.repository.search test package.
     *
     * @see ir.redmind.paasho.repository.search.TitlesSearchRepositoryMockConfiguration
     */
    @Autowired
    private TitlesSearchRepository mockTitlesSearchRepository;

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

    private MockMvc restTitlesMockMvc;

    private Titles titles;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TitlesResource titlesResource = new TitlesResource(titlesService);
        this.restTitlesMockMvc = MockMvcBuilders.standaloneSetup(titlesResource)
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
    public static Titles createEntity(EntityManager em) {
        Titles titles = new Titles();
        return titles;
    }

    @Before
    public void initTest() {
        titles = createEntity(em);
    }

    @Test
    @Transactional
    public void createTitles() throws Exception {
        int databaseSizeBeforeCreate = titlesRepository.findAll().size();

        // Create the Titles
        TitlesDTO titlesDTO = titlesMapper.toDto(titles);
        restTitlesMockMvc.perform(post("/api/titles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(titlesDTO)))
            .andExpect(status().isCreated());

        // Validate the Titles in the database
        List<Titles> titlesList = titlesRepository.findAll();
        assertThat(titlesList).hasSize(databaseSizeBeforeCreate + 1);
        Titles testTitles = titlesList.get(titlesList.size() - 1);

        // Validate the Titles in Elasticsearch
        verify(mockTitlesSearchRepository, times(1)).save(testTitles);
    }

    @Test
    @Transactional
    public void createTitlesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = titlesRepository.findAll().size();

        // Create the Titles with an existing ID
        titles.setId(1L);
        TitlesDTO titlesDTO = titlesMapper.toDto(titles);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTitlesMockMvc.perform(post("/api/titles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(titlesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Titles in the database
        List<Titles> titlesList = titlesRepository.findAll();
        assertThat(titlesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Titles in Elasticsearch
        verify(mockTitlesSearchRepository, times(0)).save(titles);
    }

    @Test
    @Transactional
    public void getAllTitles() throws Exception {
        // Initialize the database
        titlesRepository.saveAndFlush(titles);

        // Get all the titlesList
        restTitlesMockMvc.perform(get("/api/titles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(titles.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getTitles() throws Exception {
        // Initialize the database
        titlesRepository.saveAndFlush(titles);

        // Get the titles
        restTitlesMockMvc.perform(get("/api/titles/{id}", titles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(titles.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTitles() throws Exception {
        // Get the titles
        restTitlesMockMvc.perform(get("/api/titles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTitles() throws Exception {
        // Initialize the database
        titlesRepository.saveAndFlush(titles);

        int databaseSizeBeforeUpdate = titlesRepository.findAll().size();

        // Update the titles
        Titles updatedTitles = titlesRepository.findById(titles.getId()).get();
        // Disconnect from session so that the updates on updatedTitles are not directly saved in db
        em.detach(updatedTitles);
        TitlesDTO titlesDTO = titlesMapper.toDto(updatedTitles);

        restTitlesMockMvc.perform(put("/api/titles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(titlesDTO)))
            .andExpect(status().isOk());

        // Validate the Titles in the database
        List<Titles> titlesList = titlesRepository.findAll();
        assertThat(titlesList).hasSize(databaseSizeBeforeUpdate);
        Titles testTitles = titlesList.get(titlesList.size() - 1);

        // Validate the Titles in Elasticsearch
        verify(mockTitlesSearchRepository, times(1)).save(testTitles);
    }

    @Test
    @Transactional
    public void updateNonExistingTitles() throws Exception {
        int databaseSizeBeforeUpdate = titlesRepository.findAll().size();

        // Create the Titles
        TitlesDTO titlesDTO = titlesMapper.toDto(titles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTitlesMockMvc.perform(put("/api/titles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(titlesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Titles in the database
        List<Titles> titlesList = titlesRepository.findAll();
        assertThat(titlesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Titles in Elasticsearch
        verify(mockTitlesSearchRepository, times(0)).save(titles);
    }

    @Test
    @Transactional
    public void deleteTitles() throws Exception {
        // Initialize the database
        titlesRepository.saveAndFlush(titles);

        int databaseSizeBeforeDelete = titlesRepository.findAll().size();

        // Delete the titles
        restTitlesMockMvc.perform(delete("/api/titles/{id}", titles.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Titles> titlesList = titlesRepository.findAll();
        assertThat(titlesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Titles in Elasticsearch
        verify(mockTitlesSearchRepository, times(1)).deleteById(titles.getId());
    }

    @Test
    @Transactional
    public void searchTitles() throws Exception {
        // Initialize the database
        titlesRepository.saveAndFlush(titles);
        when(mockTitlesSearchRepository.search(queryStringQuery("id:" + titles.getId())))
            .thenReturn(Collections.singletonList(titles));
        // Search the titles
        restTitlesMockMvc.perform(get("/api/_search/titles?query=id:" + titles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(titles.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Titles.class);
        Titles titles1 = new Titles();
        titles1.setId(1L);
        Titles titles2 = new Titles();
        titles2.setId(titles1.getId());
        assertThat(titles1).isEqualTo(titles2);
        titles2.setId(2L);
        assertThat(titles1).isNotEqualTo(titles2);
        titles1.setId(null);
        assertThat(titles1).isNotEqualTo(titles2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TitlesDTO.class);
        TitlesDTO titlesDTO1 = new TitlesDTO();
        titlesDTO1.setId(1L);
        TitlesDTO titlesDTO2 = new TitlesDTO();
        assertThat(titlesDTO1).isNotEqualTo(titlesDTO2);
        titlesDTO2.setId(titlesDTO1.getId());
        assertThat(titlesDTO1).isEqualTo(titlesDTO2);
        titlesDTO2.setId(2L);
        assertThat(titlesDTO1).isNotEqualTo(titlesDTO2);
        titlesDTO1.setId(null);
        assertThat(titlesDTO1).isNotEqualTo(titlesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(titlesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(titlesMapper.fromId(null)).isNull();
    }
}
