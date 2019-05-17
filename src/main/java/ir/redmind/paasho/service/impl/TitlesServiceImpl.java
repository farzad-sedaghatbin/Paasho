package ir.redmind.paasho.service.impl;

import ir.redmind.paasho.domain.Titles;
import ir.redmind.paasho.repository.TitlesRepository;
import ir.redmind.paasho.service.TitlesService;
import ir.redmind.paasho.service.dto.TitlesDTO;
import ir.redmind.paasho.service.mapper.TitlesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Titles.
 */
@Service
@Transactional
public class TitlesServiceImpl implements TitlesService {

    private final Logger log = LoggerFactory.getLogger(TitlesServiceImpl.class);

    private final TitlesRepository titlesRepository;

    private final TitlesMapper titlesMapper;


    public TitlesServiceImpl(TitlesRepository titlesRepository, TitlesMapper titlesMapper) {
        this.titlesRepository = titlesRepository;
        this.titlesMapper = titlesMapper;
    }

    /**
     * Save a titles.
     *
     * @param titlesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TitlesDTO save(TitlesDTO titlesDTO) {
        log.debug("Request to save Titles : {}", titlesDTO);
        Titles titles = titlesMapper.toEntity(titlesDTO);
        titles = titlesRepository.save(titles);
        TitlesDTO result = titlesMapper.toDto(titles);
        return result;
    }

    /**
     * Get all the titles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TitlesDTO> findAll() {
        log.debug("Request to get all Titles");
        return titlesRepository.findAll().stream()
            .map(titlesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one titles by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TitlesDTO> findOne(Long id) {
        log.debug("Request to get Titles : {}", id);
        return titlesRepository.findById(id)
            .map(titlesMapper::toDto);
    }

    /**
     * Delete the titles by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Titles : {}", id);
        titlesRepository.deleteById(id);
    }

    /**
     * Search for the titles corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TitlesDTO> search(String query) {
        log.debug("Request to search Titles for query {}", query);
        return null;
//        return StreamSupport
//            .stream(titlesRepository.findAll(queryStringQuery(query)).spliterator(), false)
//            .map(titlesMapper::toDto)
//            .collect(Collectors.toList());
    }
}
