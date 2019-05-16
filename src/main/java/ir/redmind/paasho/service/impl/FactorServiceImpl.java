package ir.redmind.paasho.service.impl;

import ir.redmind.paasho.service.FactorService;
import ir.redmind.paasho.domain.Factor;
import ir.redmind.paasho.repository.FactorRepository;
import ir.redmind.paasho.repository.search.FactorSearchRepository;
import ir.redmind.paasho.service.dto.FactorDTO;
import ir.redmind.paasho.service.mapper.FactorMapper;
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
 * Service Implementation for managing Factor.
 */
@Service
@Transactional
public class FactorServiceImpl implements FactorService {

    private final Logger log = LoggerFactory.getLogger(FactorServiceImpl.class);

    private final FactorRepository factorRepository;

    private final FactorMapper factorMapper;

    private final FactorSearchRepository factorSearchRepository;

    public FactorServiceImpl(FactorRepository factorRepository, FactorMapper factorMapper, FactorSearchRepository factorSearchRepository) {
        this.factorRepository = factorRepository;
        this.factorMapper = factorMapper;
        this.factorSearchRepository = factorSearchRepository;
    }

    /**
     * Save a factor.
     *
     * @param factorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FactorDTO save(FactorDTO factorDTO) {
        log.debug("Request to save Factor : {}", factorDTO);
        Factor factor = factorMapper.toEntity(factorDTO);
        factor = factorRepository.save(factor);
        FactorDTO result = factorMapper.toDto(factor);
        factorSearchRepository.save(factor);
        return result;
    }

    /**
     * Get all the factors.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FactorDTO> findAll() {
        log.debug("Request to get all Factors");
        return factorRepository.findAll().stream()
            .map(factorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one factor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FactorDTO> findOne(Long id) {
        log.debug("Request to get Factor : {}", id);
        return factorRepository.findById(id)
            .map(factorMapper::toDto);
    }

    /**
     * Delete the factor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Factor : {}", id);
        factorRepository.deleteById(id);
        factorSearchRepository.deleteById(id);
    }

    /**
     * Search for the factor corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FactorDTO> search(String query) {
        log.debug("Request to search Factors for query {}", query);
        return StreamSupport
            .stream(factorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(factorMapper::toDto)
            .collect(Collectors.toList());
    }
}
