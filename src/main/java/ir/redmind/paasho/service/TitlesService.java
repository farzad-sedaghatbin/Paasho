package ir.redmind.paasho.service;

import ir.redmind.paasho.service.dto.TitlesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Titles.
 */
public interface TitlesService {

    /**
     * Save a titles.
     *
     * @param titlesDTO the entity to save
     * @return the persisted entity
     */
    TitlesDTO save(TitlesDTO titlesDTO);

    /**
     * Get all the titles.
     *
     * @return the list of entities
     */
    List<TitlesDTO> findAll();


    /**
     * Get the "id" titles.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TitlesDTO> findOne(Long id);

    /**
     * Delete the "id" titles.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the titles corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<TitlesDTO> search(String query);
}
