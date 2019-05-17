package ir.redmind.paasho.web.rest;
import ir.redmind.paasho.service.TitlesService;
import ir.redmind.paasho.web.rest.errors.BadRequestAlertException;
import ir.redmind.paasho.web.rest.util.HeaderUtil;
import ir.redmind.paasho.service.dto.TitlesDTO;
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

/**
 * REST controller for managing Titles.
 */
@RestController
@RequestMapping("/api")
public class TitlesResource {

    private final Logger log = LoggerFactory.getLogger(TitlesResource.class);

    private static final String ENTITY_NAME = "titles";

    private final TitlesService titlesService;

    public TitlesResource(TitlesService titlesService) {
        this.titlesService = titlesService;
    }

    /**
     * POST  /titles : Create a new titles.
     *
     * @param titlesDTO the titlesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new titlesDTO, or with status 400 (Bad Request) if the titles has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/titles")
    public ResponseEntity<TitlesDTO> createTitles(@RequestBody TitlesDTO titlesDTO) throws URISyntaxException {
        log.debug("REST request to save Titles : {}", titlesDTO);
        if (titlesDTO.getId() != null) {
            throw new BadRequestAlertException("A new titles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TitlesDTO result = titlesService.save(titlesDTO);
        return ResponseEntity.created(new URI("/api/titles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /titles : Updates an existing titles.
     *
     * @param titlesDTO the titlesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated titlesDTO,
     * or with status 400 (Bad Request) if the titlesDTO is not valid,
     * or with status 500 (Internal Server Error) if the titlesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/titles")
    public ResponseEntity<TitlesDTO> updateTitles(@RequestBody TitlesDTO titlesDTO) throws URISyntaxException {
        log.debug("REST request to update Titles : {}", titlesDTO);
        if (titlesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TitlesDTO result = titlesService.save(titlesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, titlesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /titles : get all the titles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of titles in body
     */
    @GetMapping("/titles")
    public List<TitlesDTO> getAllTitles() {
        log.debug("REST request to get all Titles");
        return titlesService.findAll();
    }

    /**
     * GET  /titles/:id : get the "id" titles.
     *
     * @param id the id of the titlesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the titlesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/titles/{id}")
    public ResponseEntity<TitlesDTO> getTitles(@PathVariable Long id) {
        log.debug("REST request to get Titles : {}", id);
        Optional<TitlesDTO> titlesDTO = titlesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(titlesDTO);
    }

    /**
     * DELETE  /titles/:id : delete the "id" titles.
     *
     * @param id the id of the titlesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/titles/{id}")
    public ResponseEntity<Void> deleteTitles(@PathVariable Long id) {
        log.debug("REST request to delete Titles : {}", id);
        titlesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/titles?query=:query : search for the titles corresponding
     * to the query.
     *
     * @param query the query of the titles search
     * @return the result of the search
     */
    @GetMapping("/_search/titles")
    public List<TitlesDTO> searchTitles(@RequestParam String query) {
        log.debug("REST request to search Titles for query {}", query);
        return titlesService.search(query);
    }

}
