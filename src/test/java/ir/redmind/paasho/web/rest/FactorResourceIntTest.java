package ir.redmind.paasho.web.rest;

import ir.redmind.paasho.PaashoApp;

import ir.redmind.paasho.domain.Factor;
import ir.redmind.paasho.repository.FactorRepository;
import ir.redmind.paasho.repository.search.FactorSearchRepository;
import ir.redmind.paasho.service.FactorService;
import ir.redmind.paasho.service.dto.FactorDTO;
import ir.redmind.paasho.service.mapper.FactorMapper;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static ir.redmind.paasho.web.rest.TestUtil.sameInstant;
import static ir.redmind.paasho.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ir.redmind.paasho.domain.enumeration.FactorStatus;
/**
 * Test class for the FactorResource REST controller.
 *
 * @see FactorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaashoApp.class)
public class FactorResourceIntTest {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final FactorStatus DEFAULT_STATUS = FactorStatus.PAID;
    private static final FactorStatus UPDATED_STATUS = FactorStatus.INIT;

    private static final ZonedDateTime DEFAULT_COMPLETE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COMPLETE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ISSUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ISSUE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private FactorRepository factorRepository;

    @Autowired
    private FactorMapper factorMapper;

    @Autowired
    private FactorService factorService;

    /**
     * This repository is mocked in the ir.redmind.paasho.repository.search test package.
     *
     * @see ir.redmind.paasho.repository.search.FactorSearchRepositoryMockConfiguration
     */
    @Autowired
    private FactorSearchRepository mockFactorSearchRepository;

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

    private MockMvc restFactorMockMvc;

    private Factor factor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FactorResource factorResource = new FactorResource(factorService);
        this.restFactorMockMvc = MockMvcBuilders.standaloneSetup(factorResource)
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
    public static Factor createEntity(EntityManager em) {
        Factor factor = new Factor()
            .price(DEFAULT_PRICE)
            .code(DEFAULT_CODE)
            .status(DEFAULT_STATUS)
            .completeDate(DEFAULT_COMPLETE_DATE)
            .issueDate(DEFAULT_ISSUE_DATE);
        return factor;
    }

    @Before
    public void initTest() {
        factor = createEntity(em);
    }

    @Test
    @Transactional
    public void createFactor() throws Exception {
        int databaseSizeBeforeCreate = factorRepository.findAll().size();

        // Create the Factor
        FactorDTO factorDTO = factorMapper.toDto(factor);
        restFactorMockMvc.perform(post("/api/factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorDTO)))
            .andExpect(status().isCreated());

        // Validate the Factor in the database
        List<Factor> factorList = factorRepository.findAll();
        assertThat(factorList).hasSize(databaseSizeBeforeCreate + 1);
        Factor testFactor = factorList.get(factorList.size() - 1);
        assertThat(testFactor.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testFactor.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFactor.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFactor.getCompleteDate()).isEqualTo(DEFAULT_COMPLETE_DATE);
        assertThat(testFactor.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);

        // Validate the Factor in Elasticsearch
        verify(mockFactorSearchRepository, times(1)).save(testFactor);
    }

    @Test
    @Transactional
    public void createFactorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = factorRepository.findAll().size();

        // Create the Factor with an existing ID
        factor.setId(1L);
        FactorDTO factorDTO = factorMapper.toDto(factor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactorMockMvc.perform(post("/api/factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Factor in the database
        List<Factor> factorList = factorRepository.findAll();
        assertThat(factorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Factor in Elasticsearch
        verify(mockFactorSearchRepository, times(0)).save(factor);
    }

    @Test
    @Transactional
    public void getAllFactors() throws Exception {
        // Initialize the database
        factorRepository.saveAndFlush(factor);

        // Get all the factorList
        restFactorMockMvc.perform(get("/api/factors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factor.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].completeDate").value(hasItem(sameInstant(DEFAULT_COMPLETE_DATE))))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(sameInstant(DEFAULT_ISSUE_DATE))));
    }
    
    @Test
    @Transactional
    public void getFactor() throws Exception {
        // Initialize the database
        factorRepository.saveAndFlush(factor);

        // Get the factor
        restFactorMockMvc.perform(get("/api/factors/{id}", factor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(factor.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.completeDate").value(sameInstant(DEFAULT_COMPLETE_DATE)))
            .andExpect(jsonPath("$.issueDate").value(sameInstant(DEFAULT_ISSUE_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingFactor() throws Exception {
        // Get the factor
        restFactorMockMvc.perform(get("/api/factors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFactor() throws Exception {
        // Initialize the database
        factorRepository.saveAndFlush(factor);

        int databaseSizeBeforeUpdate = factorRepository.findAll().size();

        // Update the factor
        Factor updatedFactor = factorRepository.findById(factor.getId()).get();
        // Disconnect from session so that the updates on updatedFactor are not directly saved in db
        em.detach(updatedFactor);
        updatedFactor
            .price(UPDATED_PRICE)
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS)
            .completeDate(UPDATED_COMPLETE_DATE)
            .issueDate(UPDATED_ISSUE_DATE);
        FactorDTO factorDTO = factorMapper.toDto(updatedFactor);

        restFactorMockMvc.perform(put("/api/factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorDTO)))
            .andExpect(status().isOk());

        // Validate the Factor in the database
        List<Factor> factorList = factorRepository.findAll();
        assertThat(factorList).hasSize(databaseSizeBeforeUpdate);
        Factor testFactor = factorList.get(factorList.size() - 1);
        assertThat(testFactor.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testFactor.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFactor.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFactor.getCompleteDate()).isEqualTo(UPDATED_COMPLETE_DATE);
        assertThat(testFactor.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);

        // Validate the Factor in Elasticsearch
        verify(mockFactorSearchRepository, times(1)).save(testFactor);
    }

    @Test
    @Transactional
    public void updateNonExistingFactor() throws Exception {
        int databaseSizeBeforeUpdate = factorRepository.findAll().size();

        // Create the Factor
        FactorDTO factorDTO = factorMapper.toDto(factor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactorMockMvc.perform(put("/api/factors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(factorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Factor in the database
        List<Factor> factorList = factorRepository.findAll();
        assertThat(factorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Factor in Elasticsearch
        verify(mockFactorSearchRepository, times(0)).save(factor);
    }

    @Test
    @Transactional
    public void deleteFactor() throws Exception {
        // Initialize the database
        factorRepository.saveAndFlush(factor);

        int databaseSizeBeforeDelete = factorRepository.findAll().size();

        // Delete the factor
        restFactorMockMvc.perform(delete("/api/factors/{id}", factor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Factor> factorList = factorRepository.findAll();
        assertThat(factorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Factor in Elasticsearch
        verify(mockFactorSearchRepository, times(1)).deleteById(factor.getId());
    }

    @Test
    @Transactional
    public void searchFactor() throws Exception {
        // Initialize the database
        factorRepository.saveAndFlush(factor);
        when(mockFactorSearchRepository.search(queryStringQuery("id:" + factor.getId())))
            .thenReturn(Collections.singletonList(factor));
        // Search the factor
        restFactorMockMvc.perform(get("/api/_search/factors?query=id:" + factor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factor.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].completeDate").value(hasItem(sameInstant(DEFAULT_COMPLETE_DATE))))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(sameInstant(DEFAULT_ISSUE_DATE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Factor.class);
        Factor factor1 = new Factor();
        factor1.setId(1L);
        Factor factor2 = new Factor();
        factor2.setId(factor1.getId());
        assertThat(factor1).isEqualTo(factor2);
        factor2.setId(2L);
        assertThat(factor1).isNotEqualTo(factor2);
        factor1.setId(null);
        assertThat(factor1).isNotEqualTo(factor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FactorDTO.class);
        FactorDTO factorDTO1 = new FactorDTO();
        factorDTO1.setId(1L);
        FactorDTO factorDTO2 = new FactorDTO();
        assertThat(factorDTO1).isNotEqualTo(factorDTO2);
        factorDTO2.setId(factorDTO1.getId());
        assertThat(factorDTO1).isEqualTo(factorDTO2);
        factorDTO2.setId(2L);
        assertThat(factorDTO1).isNotEqualTo(factorDTO2);
        factorDTO1.setId(null);
        assertThat(factorDTO1).isNotEqualTo(factorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(factorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(factorMapper.fromId(null)).isNull();
    }
}
