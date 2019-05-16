package ir.redmind.paasho.service.impl;

import ir.redmind.paasho.service.MediaService;
import ir.redmind.paasho.domain.Media;
import ir.redmind.paasho.repository.MediaRepository;
import ir.redmind.paasho.repository.search.MediaSearchRepository;
import ir.redmind.paasho.service.dto.MediaDTO;
import ir.redmind.paasho.service.mapper.MediaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Media.
 */
@Service
@Transactional
public class MediaServiceImpl implements MediaService {

    private final Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);

    private final MediaRepository mediaRepository;

    private final MediaMapper mediaMapper;

//    private final MediaSearchRepository mediaSearchRepository;

    public MediaServiceImpl(MediaRepository mediaRepository, MediaMapper mediaMapper) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
//        this.mediaSearchRepository = mediaSearchRepository;
    }

    /**
     * Save a media.
     *
     * @param mediaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MediaDTO save(MediaDTO mediaDTO) {
        log.debug("Request to save Media : {}", mediaDTO);
        Media media = mediaMapper.toEntity(mediaDTO);
        media = mediaRepository.save(media);
        MediaDTO result = mediaMapper.toDto(media);
//        mediaSearchRepository.save(media);
        return result;
    }

    /**
     * Get all the media.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MediaDTO> findAll() {
        log.debug("Request to get all Media");
        return mediaRepository.findAll().stream()
            .map(mediaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one media by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MediaDTO> findOne(Long id) {
        log.debug("Request to get Media : {}", id);
        return mediaRepository.findById(id)
            .map(mediaMapper::toDto);
    }

    /**
     * Delete the media by id.
     *
//     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Media : {}", id);
        mediaRepository.deleteById(id);
//        mediaSearchRepository.deleteById(id);
    }

    /**
     * Search for the media corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<MediaDTO> search(String query) {
        log.debug("Request to search Media for query {}", query);
        return null;
//            .stream(/mediaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
//            .map(mediaMapper::toDto)
//            .collect(Collectors.toList());
    }
}
