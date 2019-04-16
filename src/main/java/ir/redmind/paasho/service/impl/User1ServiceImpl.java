package ir.redmind.paasho.service.impl;

import ir.redmind.paasho.service.User1Service;
import ir.redmind.paasho.domain.User1;
import ir.redmind.paasho.repository.User1Repository;
import ir.redmind.paasho.repository.search.User1SearchRepository;
import ir.redmind.paasho.service.dto.User1DTO;
import ir.redmind.paasho.service.mapper.User1Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing User1.
 */
@Service
@Transactional
public class User1ServiceImpl implements User1Service {

    private final Logger log = LoggerFactory.getLogger(User1ServiceImpl.class);

    private final User1Repository user1Repository;

    private final User1Mapper user1Mapper;

    private final User1SearchRepository user1SearchRepository;

    public User1ServiceImpl(User1Repository user1Repository, User1Mapper user1Mapper, User1SearchRepository user1SearchRepository) {
        this.user1Repository = user1Repository;
        this.user1Mapper = user1Mapper;
        this.user1SearchRepository = user1SearchRepository;
    }

    /**
     * Save a user1.
     *
     * @param user1DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public User1DTO save(User1DTO user1DTO) {
        log.debug("Request to save User1 : {}", user1DTO);
        User1 user1 = user1Mapper.toEntity(user1DTO);
        user1 = user1Repository.save(user1);
        User1DTO result = user1Mapper.toDto(user1);
        user1SearchRepository.save(user1);
        return result;
    }

    /**
     * Get all the user1S.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<User1DTO> findAll() {
        log.debug("Request to get all User1S");
        return user1Repository.findAllWithEagerRelationships().stream()
            .map(user1Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the User1 with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<User1DTO> findAllWithEagerRelationships(Pageable pageable) {
        return user1Repository.findAllWithEagerRelationships(pageable).map(user1Mapper::toDto);
    }
    


    /**
     *  get all the user1S where Id is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<User1DTO> findAllWhereIdIsNull() {
        log.debug("Request to get all user1S where Id is null");
        return StreamSupport
            .stream(user1Repository.findAll().spliterator(), false)
            .filter(user1 -> user1.getId() == null)
            .map(user1Mapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one user1 by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User1DTO> findOne(Long id) {
        log.debug("Request to get User1 : {}", id);
        return user1Repository.findOneWithEagerRelationships(id)
            .map(user1Mapper::toDto);
    }

    /**
     * Delete the user1 by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete User1 : {}", id);
        user1Repository.deleteById(id);
        user1SearchRepository.deleteById(id);
    }

    /**
     * Search for the user1 corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<User1DTO> search(String query) {
        log.debug("Request to search User1S for query {}", query);
        return StreamSupport
            .stream(user1SearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(user1Mapper::toDto)
            .collect(Collectors.toList());
    }
}
