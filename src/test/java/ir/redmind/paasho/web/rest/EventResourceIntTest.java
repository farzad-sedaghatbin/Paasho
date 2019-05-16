package ir.redmind.paasho.web.rest;

import ir.redmind.paasho.PaashoApp;

import ir.redmind.paasho.domain.Event;
import ir.redmind.paasho.repository.EventRepository;
import ir.redmind.paasho.repository.search.EventSearchRepository;
import ir.redmind.paasho.service.EventService;
import ir.redmind.paasho.service.dto.EventDTO;
import ir.redmind.paasho.service.mapper.EventMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
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

import ir.redmind.paasho.domain.enumeration.PriceType;
import ir.redmind.paasho.domain.enumeration.EventStatus;
/**
 * Test class for the EventResource REST controller.
 *
 * @see EventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaashoApp.class)
public class EventResourceIntTest {

    private static final ZonedDateTime DEFAULT_EVENT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EVENT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAX_AGE = 1;
    private static final Integer UPDATED_MAX_AGE = 2;

    private static final Integer DEFAULT_MIN_AGE = 1;
    private static final Integer UPDATED_MIN_AGE = 2;

    private static final PriceType DEFAULT_PRICE_TYPE = PriceType.FREE;
    private static final PriceType UPDATED_PRICE_TYPE = PriceType.DUTCH;

    private static final EventStatus DEFAULT_STATUS = EventStatus.OPEN;
    private static final EventStatus UPDATED_STATUS = EventStatus.CLOSE;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_VISIT_COUNT = 1;
    private static final Integer UPDATED_VISIT_COUNT = 2;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Integer DEFAULT_LIKES = 1;
    private static final Integer UPDATED_LIKES = 2;

    private static final byte[] DEFAULT_FILES = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILES = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILES_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILES_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM = "BBBBBBBBBB";

    private static final String DEFAULT_TELEGRAM = "AAAAAAAAAA";
    private static final String UPDATED_TELEGRAM = "BBBBBBBBBB";

    private static final Long DEFAULT_CAPACITY = 1L;
    private static final Long UPDATED_CAPACITY = 2L;

    private static final String DEFAULT_CUSTOM_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_STRING = "AAAAAAAAAA";
    private static final String UPDATED_DATE_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_TIME_STRING = "AAAAAAAAAA";
    private static final String UPDATED_TIME_STRING = "BBBBBBBBBB";

    @Autowired
    private EventRepository eventRepository;

    @Mock
    private EventRepository eventRepositoryMock;

    @Autowired
    private EventMapper eventMapper;

    @Mock
    private EventService eventServiceMock;

    @Autowired
    private EventService eventService;

    /**
     * This repository is mocked in the ir.redmind.paasho.repository.search test package.
     *
     * @see ir.redmind.paasho.repository.search.EventSearchRepositoryMockConfiguration
     */
    @Autowired
    private EventSearchRepository mockEventSearchRepository;

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

    private MockMvc restEventMockMvc;

    private Event event;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventResource eventResource = new EventResource(eventService);
        this.restEventMockMvc = MockMvcBuilders.standaloneSetup(eventResource)
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
    public static Event createEntity(EntityManager em) {
        Event event = new Event()
            .eventTime(DEFAULT_EVENT_TIME)
            .description(DEFAULT_DESCRIPTION)
            .code(DEFAULT_CODE)
            .title(DEFAULT_TITLE)
            .maxAge(DEFAULT_MAX_AGE)
            .minAge(DEFAULT_MIN_AGE)
            .priceType(DEFAULT_PRICE_TYPE)
            .status(DEFAULT_STATUS)
            .address(DEFAULT_ADDRESS)
            .visitCount(DEFAULT_VISIT_COUNT)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .likes(DEFAULT_LIKES)
            .files(DEFAULT_FILES)
            .filesContentType(DEFAULT_FILES_CONTENT_TYPE)
            .tel(DEFAULT_TEL)
            .instagram(DEFAULT_INSTAGRAM)
            .telegram(DEFAULT_TELEGRAM)
            .capacity(DEFAULT_CAPACITY)
            .customTitle(DEFAULT_CUSTOM_TITLE)
            .dateString(DEFAULT_DATE_STRING)
            .timeString(DEFAULT_TIME_STRING);
        return event;
    }

    @Before
    public void initTest() {
        event = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);
        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getEventTime()).isEqualTo(DEFAULT_EVENT_TIME);
        assertThat(testEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEvent.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEvent.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEvent.getMaxAge()).isEqualTo(DEFAULT_MAX_AGE);
        assertThat(testEvent.getMinAge()).isEqualTo(DEFAULT_MIN_AGE);
        assertThat(testEvent.getPriceType()).isEqualTo(DEFAULT_PRICE_TYPE);
        assertThat(testEvent.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEvent.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEvent.getVisitCount()).isEqualTo(DEFAULT_VISIT_COUNT);
        assertThat(testEvent.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testEvent.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testEvent.getLikes()).isEqualTo(DEFAULT_LIKES);
        assertThat(testEvent.getFiles()).isEqualTo(DEFAULT_FILES);
        assertThat(testEvent.getFilesContentType()).isEqualTo(DEFAULT_FILES_CONTENT_TYPE);
        assertThat(testEvent.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testEvent.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testEvent.getTelegram()).isEqualTo(DEFAULT_TELEGRAM);
        assertThat(testEvent.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testEvent.getCustomTitle()).isEqualTo(DEFAULT_CUSTOM_TITLE);
        assertThat(testEvent.getDateString()).isEqualTo(DEFAULT_DATE_STRING);
        assertThat(testEvent.getTimeString()).isEqualTo(DEFAULT_TIME_STRING);

        // Validate the Event in Elasticsearch
        verify(mockEventSearchRepository, times(1)).save(testEvent);
    }

    @Test
    @Transactional
    public void createEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event with an existing ID
        event.setId(1L);
        EventDTO eventDTO = eventMapper.toDto(event);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);

        // Validate the Event in Elasticsearch
        verify(mockEventSearchRepository, times(0)).save(event);
    }

    @Test
    @Transactional
    public void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList
        restEventMockMvc.perform(get("/api/events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventTime").value(hasItem(sameInstant(DEFAULT_EVENT_TIME))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].maxAge").value(hasItem(DEFAULT_MAX_AGE)))
            .andExpect(jsonPath("$.[*].minAge").value(hasItem(DEFAULT_MIN_AGE)))
            .andExpect(jsonPath("$.[*].priceType").value(hasItem(DEFAULT_PRICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].visitCount").value(hasItem(DEFAULT_VISIT_COUNT)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].likes").value(hasItem(DEFAULT_LIKES)))
            .andExpect(jsonPath("$.[*].filesContentType").value(hasItem(DEFAULT_FILES_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].files").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILES))))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM.toString())))
            .andExpect(jsonPath("$.[*].telegram").value(hasItem(DEFAULT_TELEGRAM.toString())))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY.intValue())))
            .andExpect(jsonPath("$.[*].customTitle").value(hasItem(DEFAULT_CUSTOM_TITLE.toString())))
            .andExpect(jsonPath("$.[*].dateString").value(hasItem(DEFAULT_DATE_STRING.toString())))
            .andExpect(jsonPath("$.[*].timeString").value(hasItem(DEFAULT_TIME_STRING.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllEventsWithEagerRelationshipsIsEnabled() throws Exception {
        EventResource eventResource = new EventResource(eventServiceMock);
        when(eventServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restEventMockMvc = MockMvcBuilders.standaloneSetup(eventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEventMockMvc.perform(get("/api/events?eagerload=true"))
        .andExpect(status().isOk());

        verify(eventServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllEventsWithEagerRelationshipsIsNotEnabled() throws Exception {
        EventResource eventResource = new EventResource(eventServiceMock);
            when(eventServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restEventMockMvc = MockMvcBuilders.standaloneSetup(eventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEventMockMvc.perform(get("/api/events?eagerload=true"))
        .andExpect(status().isOk());

            verify(eventServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(event.getId().intValue()))
            .andExpect(jsonPath("$.eventTime").value(sameInstant(DEFAULT_EVENT_TIME)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.maxAge").value(DEFAULT_MAX_AGE))
            .andExpect(jsonPath("$.minAge").value(DEFAULT_MIN_AGE))
            .andExpect(jsonPath("$.priceType").value(DEFAULT_PRICE_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.visitCount").value(DEFAULT_VISIT_COUNT))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.likes").value(DEFAULT_LIKES))
            .andExpect(jsonPath("$.filesContentType").value(DEFAULT_FILES_CONTENT_TYPE))
            .andExpect(jsonPath("$.files").value(Base64Utils.encodeToString(DEFAULT_FILES)))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL.toString()))
            .andExpect(jsonPath("$.instagram").value(DEFAULT_INSTAGRAM.toString()))
            .andExpect(jsonPath("$.telegram").value(DEFAULT_TELEGRAM.toString()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY.intValue()))
            .andExpect(jsonPath("$.customTitle").value(DEFAULT_CUSTOM_TITLE.toString()))
            .andExpect(jsonPath("$.dateString").value(DEFAULT_DATE_STRING.toString()))
            .andExpect(jsonPath("$.timeString").value(DEFAULT_TIME_STRING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        Event updatedEvent = eventRepository.findById(event.getId()).get();
        // Disconnect from session so that the updates on updatedEvent are not directly saved in db
        em.detach(updatedEvent);
        updatedEvent
            .eventTime(UPDATED_EVENT_TIME)
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .maxAge(UPDATED_MAX_AGE)
            .minAge(UPDATED_MIN_AGE)
            .priceType(UPDATED_PRICE_TYPE)
            .status(UPDATED_STATUS)
            .address(UPDATED_ADDRESS)
            .visitCount(UPDATED_VISIT_COUNT)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .likes(UPDATED_LIKES)
            .files(UPDATED_FILES)
            .filesContentType(UPDATED_FILES_CONTENT_TYPE)
            .tel(UPDATED_TEL)
            .instagram(UPDATED_INSTAGRAM)
            .telegram(UPDATED_TELEGRAM)
            .capacity(UPDATED_CAPACITY)
            .customTitle(UPDATED_CUSTOM_TITLE)
            .dateString(UPDATED_DATE_STRING)
            .timeString(UPDATED_TIME_STRING);
        EventDTO eventDTO = eventMapper.toDto(updatedEvent);

        restEventMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getEventTime()).isEqualTo(UPDATED_EVENT_TIME);
        assertThat(testEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvent.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEvent.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEvent.getMaxAge()).isEqualTo(UPDATED_MAX_AGE);
        assertThat(testEvent.getMinAge()).isEqualTo(UPDATED_MIN_AGE);
        assertThat(testEvent.getPriceType()).isEqualTo(UPDATED_PRICE_TYPE);
        assertThat(testEvent.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEvent.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEvent.getVisitCount()).isEqualTo(UPDATED_VISIT_COUNT);
        assertThat(testEvent.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testEvent.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testEvent.getLikes()).isEqualTo(UPDATED_LIKES);
        assertThat(testEvent.getFiles()).isEqualTo(UPDATED_FILES);
        assertThat(testEvent.getFilesContentType()).isEqualTo(UPDATED_FILES_CONTENT_TYPE);
        assertThat(testEvent.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testEvent.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testEvent.getTelegram()).isEqualTo(UPDATED_TELEGRAM);
        assertThat(testEvent.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testEvent.getCustomTitle()).isEqualTo(UPDATED_CUSTOM_TITLE);
        assertThat(testEvent.getDateString()).isEqualTo(UPDATED_DATE_STRING);
        assertThat(testEvent.getTimeString()).isEqualTo(UPDATED_TIME_STRING);

        // Validate the Event in Elasticsearch
        verify(mockEventSearchRepository, times(1)).save(testEvent);
    }

    @Test
    @Transactional
    public void updateNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Create the Event
        EventDTO eventDTO = eventMapper.toDto(event);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Event in Elasticsearch
        verify(mockEventSearchRepository, times(0)).save(event);
    }

    @Test
    @Transactional
    public void deleteEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Delete the event
        restEventMockMvc.perform(delete("/api/events/{id}", event.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Event in Elasticsearch
        verify(mockEventSearchRepository, times(1)).deleteById(event.getId());
    }

    @Test
    @Transactional
    public void searchEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);
        when(mockEventSearchRepository.search(queryStringQuery("id:" + event.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(event), PageRequest.of(0, 1), 1));
        // Search the event
        restEventMockMvc.perform(get("/api/_search/events?query=id:" + event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventTime").value(hasItem(sameInstant(DEFAULT_EVENT_TIME))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].maxAge").value(hasItem(DEFAULT_MAX_AGE)))
            .andExpect(jsonPath("$.[*].minAge").value(hasItem(DEFAULT_MIN_AGE)))
            .andExpect(jsonPath("$.[*].priceType").value(hasItem(DEFAULT_PRICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].visitCount").value(hasItem(DEFAULT_VISIT_COUNT)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].likes").value(hasItem(DEFAULT_LIKES)))
            .andExpect(jsonPath("$.[*].filesContentType").value(hasItem(DEFAULT_FILES_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].files").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILES))))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM)))
            .andExpect(jsonPath("$.[*].telegram").value(hasItem(DEFAULT_TELEGRAM)))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY.intValue())))
            .andExpect(jsonPath("$.[*].customTitle").value(hasItem(DEFAULT_CUSTOM_TITLE)))
            .andExpect(jsonPath("$.[*].dateString").value(hasItem(DEFAULT_DATE_STRING)))
            .andExpect(jsonPath("$.[*].timeString").value(hasItem(DEFAULT_TIME_STRING)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Event.class);
        Event event1 = new Event();
        event1.setId(1L);
        Event event2 = new Event();
        event2.setId(event1.getId());
        assertThat(event1).isEqualTo(event2);
        event2.setId(2L);
        assertThat(event1).isNotEqualTo(event2);
        event1.setId(null);
        assertThat(event1).isNotEqualTo(event2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventDTO.class);
        EventDTO eventDTO1 = new EventDTO();
        eventDTO1.setId(1L);
        EventDTO eventDTO2 = new EventDTO();
        assertThat(eventDTO1).isNotEqualTo(eventDTO2);
        eventDTO2.setId(eventDTO1.getId());
        assertThat(eventDTO1).isEqualTo(eventDTO2);
        eventDTO2.setId(2L);
        assertThat(eventDTO1).isNotEqualTo(eventDTO2);
        eventDTO1.setId(null);
        assertThat(eventDTO1).isNotEqualTo(eventDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(eventMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(eventMapper.fromId(null)).isNull();
    }
}
