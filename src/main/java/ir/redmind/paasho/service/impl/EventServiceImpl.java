package ir.redmind.paasho.service.impl;

import ir.redmind.paasho.domain.Event;
import ir.redmind.paasho.domain.Media;
import ir.redmind.paasho.domain.enumeration.EventStatus;
import ir.redmind.paasho.domain.enumeration.MediaType;
import ir.redmind.paasho.repository.EventRepository;
import ir.redmind.paasho.service.EventService;
import ir.redmind.paasho.service.MediaService;
import ir.redmind.paasho.service.dto.EventDTO;
import ir.redmind.paasho.service.mapper.EventMapper;
import ir.redmind.paasho.service.mapper.MediaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Event.
 */
@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;
    private MediaService mediaService;
    private MediaMapper mediaMapper;


    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, MediaService mediaService, MediaMapper mediaMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.mediaService = mediaService;
        this.mediaMapper = mediaMapper;
    }

    /**
     * Save a event.
     *
     * @param eventDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EventDTO save(EventDTO eventDTO) {
        log.debug("Request to save Event : {}", eventDTO);
        Event event = eventMapper.toEntity(eventDTO);
        event = eventRepository.save(event);
        EventDTO result = eventMapper.toDto(event);
        return result;
    }

    /**
     * Get all the events.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Events");
        return eventRepository.findAll(pageable)
            .map(eventMapper::toDto);
    }

    /**
     * Get all the Event with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<EventDTO> findAllWithEagerRelationships(Pageable pageable) {
        return eventRepository.findAllWithEagerRelationships(pageable).map(eventMapper::toDto);
    }
    

    /**
     * Get one event by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Event> findOne(Long id) {
        log.debug("Request to get Event : {}", id);
        return eventRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the event by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Event : {}", id);
        eventRepository.deleteById(id);
    }

    @Override
    public void approve(Long id) {
        log.debug("Request to approve Event : {}", id);
        Optional<Event> event = eventRepository.findOneWithEagerRelationships(id);
        Event e = event.get();
        e.setStatus(EventStatus.APPROVED);
        eventRepository.save(e);
    }

    @Override
    public List<Event> searchByTitleOrDescription(String key) {
        return eventRepository.findByTitleIsContainingOrDescriptionContaining(key,key);
    }

    /**
     * Search for the event corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Events for query {}", query);
//        return eventSearchRepository.search(queryStringQuery(query), pageable)
//            .map(eventMapper::toDto);
        return null;
    }

    @Override
    public Page<EventDTO> searchByBuilder(String query, Pageable pageable) {
        return null;
    }

    @Override
    public Event findByCode(String code) {
        return eventRepository.findByCode(code);
    }

    @Override
    public Event addUrl(String url, String code) {
        Media media;
        Event event = findByCode(code);

//        mediaService.removeByEvent(event);
//        event.setMedias(new HashSet<>());
//        eventService.save(eventMapper.toDto(event));
        if (!url.contains(".yekupload.ir/images/direct/")) {
            String newUrl = url.replace("http://yekupload.ir/", "");
            String prefix = "https://s4.yekupload.ir/images/direct/";
//            media = new Media(prefix + newUrl, MediaType.PHOTO, event);
//        } else {
//            media = new Media(url, MediaType.PHOTO, event);

        }

//        mediaService.save(mediaMapper.toDto(media));
//        event.getMedias().add(media);

        save(eventMapper.toDto(event));

        return event;
    }
}
