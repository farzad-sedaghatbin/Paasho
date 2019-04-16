package ir.redmind.paasho.web.rest;

import ir.redmind.paasho.PaashoApp;

import ir.redmind.paasho.domain.Media;
import ir.redmind.paasho.repository.MediaRepository;
import ir.redmind.paasho.repository.search.MediaSearchRepository;
import ir.redmind.paasho.service.MediaService;
import ir.redmind.paasho.service.dto.MediaDTO;
import ir.redmind.paasho.service.mapper.MediaMapper;
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

import ir.redmind.paasho.domain.enumeration.MediaType;
/**
 * Test class for the MediaResource REST controller.
 *
 * @see MediaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaashoApp.class)
public class MediaResourceIntTest {

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final MediaType DEFAULT_TYPE = MediaType.VIDEO;
    private static final MediaType UPDATED_TYPE = MediaType.PHOTO;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private MediaMapper mediaMapper;

    @Autowired
    private MediaService mediaService;

    /**
     * This repository is mocked in the ir.redmind.paasho.repository.search test package.
     *
     * @see ir.redmind.paasho.repository.search.MediaSearchRepositoryMockConfiguration
     */
    @Autowired
    private MediaSearchRepository mockMediaSearchRepository;

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

    private MockMvc restMediaMockMvc;

    private Media media;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MediaResource mediaResource = new MediaResource(mediaService);
        this.restMediaMockMvc = MockMvcBuilders.standaloneSetup(mediaResource)
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
    public static Media createEntity(EntityManager em) {
        Media media = new Media()
            .path(DEFAULT_PATH)
            .type(DEFAULT_TYPE);
        return media;
    }

    @Before
    public void initTest() {
        media = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedia() throws Exception {
        int databaseSizeBeforeCreate = mediaRepository.findAll().size();

        // Create the Media
        MediaDTO mediaDTO = mediaMapper.toDto(media);
        restMediaMockMvc.perform(post("/api/media")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isCreated());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeCreate + 1);
        Media testMedia = mediaList.get(mediaList.size() - 1);
        assertThat(testMedia.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testMedia.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the Media in Elasticsearch
        verify(mockMediaSearchRepository, times(1)).save(testMedia);
    }

    @Test
    @Transactional
    public void createMediaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mediaRepository.findAll().size();

        // Create the Media with an existing ID
        media.setId(1L);
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMediaMockMvc.perform(post("/api/media")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Media in Elasticsearch
        verify(mockMediaSearchRepository, times(0)).save(media);
    }

    @Test
    @Transactional
    public void getAllMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);

        // Get all the mediaList
        restMediaMockMvc.perform(get("/api/media?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(media.getId().intValue())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);

        // Get the media
        restMediaMockMvc.perform(get("/api/media/{id}", media.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(media.getId().intValue()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedia() throws Exception {
        // Get the media
        restMediaMockMvc.perform(get("/api/media/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);

        int databaseSizeBeforeUpdate = mediaRepository.findAll().size();

        // Update the media
        Media updatedMedia = mediaRepository.findById(media.getId()).get();
        // Disconnect from session so that the updates on updatedMedia are not directly saved in db
        em.detach(updatedMedia);
        updatedMedia
            .path(UPDATED_PATH)
            .type(UPDATED_TYPE);
        MediaDTO mediaDTO = mediaMapper.toDto(updatedMedia);

        restMediaMockMvc.perform(put("/api/media")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isOk());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeUpdate);
        Media testMedia = mediaList.get(mediaList.size() - 1);
        assertThat(testMedia.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testMedia.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the Media in Elasticsearch
        verify(mockMediaSearchRepository, times(1)).save(testMedia);
    }

    @Test
    @Transactional
    public void updateNonExistingMedia() throws Exception {
        int databaseSizeBeforeUpdate = mediaRepository.findAll().size();

        // Create the Media
        MediaDTO mediaDTO = mediaMapper.toDto(media);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMediaMockMvc.perform(put("/api/media")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Media in Elasticsearch
        verify(mockMediaSearchRepository, times(0)).save(media);
    }

    @Test
    @Transactional
    public void deleteMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);

        int databaseSizeBeforeDelete = mediaRepository.findAll().size();

        // Delete the media
        restMediaMockMvc.perform(delete("/api/media/{id}", media.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Media in Elasticsearch
        verify(mockMediaSearchRepository, times(1)).deleteById(media.getId());
    }

    @Test
    @Transactional
    public void searchMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);
        when(mockMediaSearchRepository.search(queryStringQuery("id:" + media.getId())))
            .thenReturn(Collections.singletonList(media));
        // Search the media
        restMediaMockMvc.perform(get("/api/_search/media?query=id:" + media.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(media.getId().intValue())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Media.class);
        Media media1 = new Media();
        media1.setId(1L);
        Media media2 = new Media();
        media2.setId(media1.getId());
        assertThat(media1).isEqualTo(media2);
        media2.setId(2L);
        assertThat(media1).isNotEqualTo(media2);
        media1.setId(null);
        assertThat(media1).isNotEqualTo(media2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MediaDTO.class);
        MediaDTO mediaDTO1 = new MediaDTO();
        mediaDTO1.setId(1L);
        MediaDTO mediaDTO2 = new MediaDTO();
        assertThat(mediaDTO1).isNotEqualTo(mediaDTO2);
        mediaDTO2.setId(mediaDTO1.getId());
        assertThat(mediaDTO1).isEqualTo(mediaDTO2);
        mediaDTO2.setId(2L);
        assertThat(mediaDTO1).isNotEqualTo(mediaDTO2);
        mediaDTO1.setId(null);
        assertThat(mediaDTO1).isNotEqualTo(mediaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mediaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mediaMapper.fromId(null)).isNull();
    }
}
