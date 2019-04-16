package ir.redmind.paasho.service;

import ir.redmind.paasho.service.dto.User1DTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing User1.
 */
public interface User1Service {

    /**
     * Save a user1.
     *
     * @param user1DTO the entity to save
     * @return the persisted entity
     */
    User1DTO save(User1DTO user1DTO);

    /**
     * Get all the user1S.
     *
     * @return the list of entities
     */
    List<User1DTO> findAll();
    /**
     * Get all the User1DTO where Event is null.
     *
     * @return the list of entities
     */
    List<User1DTO> findAllWhereEventIsNull();

    /**
     * Get all the User1 with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<User1DTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" user1.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<User1DTO> findOne(Long id);

    /**
     * Delete the "id" user1.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the user1 corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<User1DTO> search(String query);
}
