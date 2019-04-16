package ir.redmind.paasho.web.rest;

import ir.redmind.paasho.PaashoApp;

import ir.redmind.paasho.domain.User1;
import ir.redmind.paasho.repository.User1Repository;
import ir.redmind.paasho.repository.search.User1SearchRepository;
import ir.redmind.paasho.service.User1Service;
import ir.redmind.paasho.service.dto.User1DTO;
import ir.redmind.paasho.service.mapper.User1Mapper;
import ir.redmind.paasho.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static ir.redmind.paasho.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ir.redmind.paasho.domain.enumeration.GenderType;
/**
 * Test class for the User1Resource REST controller.
 *
 * @see User1Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaashoApp.class)
public class User1ResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final GenderType DEFAULT_GENDER = GenderType.FEMALE;
    private static final GenderType UPDATED_GENDER = GenderType.MALE;

    private static final String DEFAULT_BIRTH_DATE = "AAAAAAAAAA";
    private static final String UPDATED_BIRTH_DATE = "BBBBBBBBBB";

    @Autowired
    private User1Repository user1Repository;

    @Mock
    private User1Repository user1RepositoryMock;

    @Autowired
    private User1Mapper user1Mapper;

    @Mock
    private User1Service user1ServiceMock;

    @Autowired
    private User1Service user1Service;

    /**
     * This repository is mocked in the ir.redmind.paasho.repository.search test package.
     *
     * @see ir.redmind.paasho.repository.search.User1SearchRepositoryMockConfiguration
     */
    @Autowired
    private User1SearchRepository mockUser1SearchRepository;

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

    private MockMvc restUser1MockMvc;

    private User1 user1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final User1Resource user1Resource = new User1Resource(user1Service);
        this.restUser1MockMvc = MockMvcBuilders.standaloneSetup(user1Resource)
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
    public static User1 createEntity(EntityManager em) {
        User1 user1 = new User1()
            .name(DEFAULT_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .gender(DEFAULT_GENDER)
            .birthDate(DEFAULT_BIRTH_DATE);
        return user1;
    }

    @Before
    public void initTest() {
        user1 = createEntity(em);
    }

    @Test
    @Transactional
    public void createUser1() throws Exception {
        int databaseSizeBeforeCreate = user1Repository.findAll().size();

        // Create the User1
        User1DTO user1DTO = user1Mapper.toDto(user1);
        restUser1MockMvc.perform(post("/api/user-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user1DTO)))
            .andExpect(status().isCreated());

        // Validate the User1 in the database
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeCreate + 1);
        User1 testUser1 = user1List.get(user1List.size() - 1);
        assertThat(testUser1.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUser1.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUser1.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testUser1.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);

        // Validate the User1 in Elasticsearch
        verify(mockUser1SearchRepository, times(1)).save(testUser1);
    }

    @Test
    @Transactional
    public void createUser1WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = user1Repository.findAll().size();

        // Create the User1 with an existing ID
        user1.setId(1L);
        User1DTO user1DTO = user1Mapper.toDto(user1);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUser1MockMvc.perform(post("/api/user-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user1DTO)))
            .andExpect(status().isBadRequest());

        // Validate the User1 in the database
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeCreate);

        // Validate the User1 in Elasticsearch
        verify(mockUser1SearchRepository, times(0)).save(user1);
    }

    @Test
    @Transactional
    public void getAllUser1S() throws Exception {
        // Initialize the database
        user1Repository.saveAndFlush(user1);

        // Get all the user1List
        restUser1MockMvc.perform(get("/api/user-1-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(user1.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUser1SWithEagerRelationshipsIsEnabled() throws Exception {
        User1Resource user1Resource = new User1Resource(user1ServiceMock);
        when(user1ServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restUser1MockMvc = MockMvcBuilders.standaloneSetup(user1Resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUser1MockMvc.perform(get("/api/user-1-s?eagerload=true"))
        .andExpect(status().isOk());

        verify(user1ServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUser1SWithEagerRelationshipsIsNotEnabled() throws Exception {
        User1Resource user1Resource = new User1Resource(user1ServiceMock);
            when(user1ServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restUser1MockMvc = MockMvcBuilders.standaloneSetup(user1Resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUser1MockMvc.perform(get("/api/user-1-s?eagerload=true"))
        .andExpect(status().isOk());

            verify(user1ServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUser1() throws Exception {
        // Initialize the database
        user1Repository.saveAndFlush(user1);

        // Get the user1
        restUser1MockMvc.perform(get("/api/user-1-s/{id}", user1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(user1.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUser1() throws Exception {
        // Get the user1
        restUser1MockMvc.perform(get("/api/user-1-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUser1() throws Exception {
        // Initialize the database
        user1Repository.saveAndFlush(user1);

        int databaseSizeBeforeUpdate = user1Repository.findAll().size();

        // Update the user1
        User1 updatedUser1 = user1Repository.findById(user1.getId()).get();
        // Disconnect from session so that the updates on updatedUser1 are not directly saved in db
        em.detach(updatedUser1);
        updatedUser1
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE);
        User1DTO user1DTO = user1Mapper.toDto(updatedUser1);

        restUser1MockMvc.perform(put("/api/user-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user1DTO)))
            .andExpect(status().isOk());

        // Validate the User1 in the database
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeUpdate);
        User1 testUser1 = user1List.get(user1List.size() - 1);
        assertThat(testUser1.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUser1.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUser1.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUser1.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);

        // Validate the User1 in Elasticsearch
        verify(mockUser1SearchRepository, times(1)).save(testUser1);
    }

    @Test
    @Transactional
    public void updateNonExistingUser1() throws Exception {
        int databaseSizeBeforeUpdate = user1Repository.findAll().size();

        // Create the User1
        User1DTO user1DTO = user1Mapper.toDto(user1);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUser1MockMvc.perform(put("/api/user-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user1DTO)))
            .andExpect(status().isBadRequest());

        // Validate the User1 in the database
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeUpdate);

        // Validate the User1 in Elasticsearch
        verify(mockUser1SearchRepository, times(0)).save(user1);
    }

    @Test
    @Transactional
    public void deleteUser1() throws Exception {
        // Initialize the database
        user1Repository.saveAndFlush(user1);

        int databaseSizeBeforeDelete = user1Repository.findAll().size();

        // Delete the user1
        restUser1MockMvc.perform(delete("/api/user-1-s/{id}", user1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<User1> user1List = user1Repository.findAll();
        assertThat(user1List).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the User1 in Elasticsearch
        verify(mockUser1SearchRepository, times(1)).deleteById(user1.getId());
    }

    @Test
    @Transactional
    public void searchUser1() throws Exception {
        // Initialize the database
        user1Repository.saveAndFlush(user1);
        when(mockUser1SearchRepository.search(queryStringQuery("id:" + user1.getId())))
            .thenReturn(Collections.singletonList(user1));
        // Search the user1
        restUser1MockMvc.perform(get("/api/_search/user-1-s?query=id:" + user1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(user1.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(User1.class);
        User1 user11 = new User1();
        user11.setId(1L);
        User1 user12 = new User1();
        user12.setId(user11.getId());
        assertThat(user11).isEqualTo(user12);
        user12.setId(2L);
        assertThat(user11).isNotEqualTo(user12);
        user11.setId(null);
        assertThat(user11).isNotEqualTo(user12);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(User1DTO.class);
        User1DTO user1DTO1 = new User1DTO();
        user1DTO1.setId(1L);
        User1DTO user1DTO2 = new User1DTO();
        assertThat(user1DTO1).isNotEqualTo(user1DTO2);
        user1DTO2.setId(user1DTO1.getId());
        assertThat(user1DTO1).isEqualTo(user1DTO2);
        user1DTO2.setId(2L);
        assertThat(user1DTO1).isNotEqualTo(user1DTO2);
        user1DTO1.setId(null);
        assertThat(user1DTO1).isNotEqualTo(user1DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(user1Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(user1Mapper.fromId(null)).isNull();
    }
}
