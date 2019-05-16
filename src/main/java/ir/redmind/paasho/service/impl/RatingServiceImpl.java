package ir.redmind.paasho.service.impl;

import ir.redmind.paasho.service.RatingService;
import ir.redmind.paasho.domain.Rating;
import ir.redmind.paasho.repository.RatingRepository;
import ir.redmind.paasho.repository.search.RatingSearchRepository;
import ir.redmind.paasho.service.dto.RatingDTO;
import ir.redmind.paasho.service.mapper.RatingMapper;
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
 * Service Implementation for managing Rating.
 */
@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    private final Logger log = LoggerFactory.getLogger(RatingServiceImpl.class);

    private final RatingRepository ratingRepository;

    private final RatingMapper ratingMapper;

//    private final RatingSearchRepository ratingSearchRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
//        this.ratingSearchRepository = ratingSearchRepository;
    }

    /**
     * Save a rating.
     *
     * @param ratingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RatingDTO save(RatingDTO ratingDTO) {
        log.debug("Request to save Rating : {}", ratingDTO);
        Rating rating = ratingMapper.toEntity(ratingDTO);
        rating = ratingRepository.save(rating);
        RatingDTO result = ratingMapper.toDto(rating);
//        ratingSearchRepository.save(rating);
        return result;
    }

    /**
     * Get all the ratings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RatingDTO> findAll() {
        log.debug("Request to get all Ratings");
        return ratingRepository.findAll().stream()
            .map(ratingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one rating by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RatingDTO> findOne(Long id) {
        log.debug("Request to get Rating : {}", id);
        return ratingRepository.findById(id)
            .map(ratingMapper::toDto);
    }

    /**
     * Delete the rating by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rating : {}", id);
        ratingRepository.deleteById(id);
//        ratingSearchRepository.deleteById(id);
    }

    /**
     * Search for the rating corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
//    @Transactional(readOnly = true)
    public List<RatingDTO> search(String query) {
        log.debug("Request to search Ratings for query {}", query);
        return null;
    }
}
