package ir.redmind.paasho.service;

import ir.redmind.paasho.service.dto.FactorDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Factor.
 */
public interface FactorService {

    /**
     * Save a factor.
     *
     * @param factorDTO the entity to save
     * @return the persisted entity
     */
    FactorDTO save(FactorDTO factorDTO);

    /**
     * Get all the factors.
     *
     * @return the list of entities
     */
    List<FactorDTO> findAll();


    /**
     * Get the "id" factor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<FactorDTO> findOne(Long id);

    /**
     * Delete the "id" factor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the factor corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<FactorDTO> search(String query);
}
