package ir.redmind.paasho.web.rest;
import ir.redmind.paasho.service.FactorService;
import ir.redmind.paasho.web.rest.errors.BadRequestAlertException;
import ir.redmind.paasho.web.rest.util.HeaderUtil;
import ir.redmind.paasho.service.dto.FactorDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Factor.
 */
@RestController
@RequestMapping("/api")
public class FactorResource {

    private final Logger log = LoggerFactory.getLogger(FactorResource.class);

    private static final String ENTITY_NAME = "factor";

    private final FactorService factorService;

    public FactorResource(FactorService factorService) {
        this.factorService = factorService;
    }

    /**
     * POST  /factors : Create a new factor.
     *
     * @param factorDTO the factorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new factorDTO, or with status 400 (Bad Request) if the factor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/factors")
    public ResponseEntity<FactorDTO> createFactor(@RequestBody FactorDTO factorDTO) throws URISyntaxException {
        log.debug("REST request to save Factor : {}", factorDTO);
        if (factorDTO.getId() != null) {
            throw new BadRequestAlertException("A new factor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FactorDTO result = factorService.save(factorDTO);
        return ResponseEntity.created(new URI("/api/factors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /factors : Updates an existing factor.
     *
     * @param factorDTO the factorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated factorDTO,
     * or with status 400 (Bad Request) if the factorDTO is not valid,
     * or with status 500 (Internal Server Error) if the factorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/factors")
    public ResponseEntity<FactorDTO> updateFactor(@RequestBody FactorDTO factorDTO) throws URISyntaxException {
        log.debug("REST request to update Factor : {}", factorDTO);
        if (factorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FactorDTO result = factorService.save(factorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, factorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /factors : get all the factors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of factors in body
     */
    @GetMapping("/factors")
    public List<FactorDTO> getAllFactors() {
        log.debug("REST request to get all Factors");
        return factorService.findAll();
    }

    /**
     * GET  /factors/:id : get the "id" factor.
     *
     * @param id the id of the factorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the factorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/factors/{id}")
    public ResponseEntity<FactorDTO> getFactor(@PathVariable Long id) {
        log.debug("REST request to get Factor : {}", id);
        Optional<FactorDTO> factorDTO = factorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(factorDTO);
    }

    /**
     * DELETE  /factors/:id : delete the "id" factor.
     *
     * @param id the id of the factorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/factors/{id}")
    public ResponseEntity<Void> deleteFactor(@PathVariable Long id) {
        log.debug("REST request to delete Factor : {}", id);
        factorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/factors?query=:query : search for the factor corresponding
     * to the query.
     *
     * @param query the query of the factor search
     * @return the result of the search
     */
    @GetMapping("/_search/factors")
    public List<FactorDTO> searchFactors(@RequestParam String query) {
        log.debug("REST request to search Factors for query {}", query);
        return factorService.search(query);
    }

}
