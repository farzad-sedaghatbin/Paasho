package ir.redmind.paasho.web.rest;
import ir.redmind.paasho.service.RatingService;
import ir.redmind.paasho.web.rest.errors.BadRequestAlertException;
import ir.redmind.paasho.web.rest.util.HeaderUtil;
import ir.redmind.paasho.service.dto.RatingDTO;
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
 * REST controller for managing Rating.
 */
@RestController
@RequestMapping("/api")
public class RatingResource {

    private final Logger log = LoggerFactory.getLogger(RatingResource.class);

    private static final String ENTITY_NAME = "rating";

    private final RatingService ratingService;

    public RatingResource(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * POST  /ratings : Create a new rating.
     *
     * @param ratingDTO the ratingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ratingDTO, or with status 400 (Bad Request) if the rating has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ratings")
    public ResponseEntity<RatingDTO> createRating(@RequestBody RatingDTO ratingDTO) throws URISyntaxException {
        log.debug("REST request to save Rating : {}", ratingDTO);
        if (ratingDTO.getId() != null) {
            throw new BadRequestAlertException("A new rating cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RatingDTO result = ratingService.save(ratingDTO);
        return ResponseEntity.created(new URI("/api/ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ratings : Updates an existing rating.
     *
     * @param ratingDTO the ratingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ratingDTO,
     * or with status 400 (Bad Request) if the ratingDTO is not valid,
     * or with status 500 (Internal Server Error) if the ratingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ratings")
    public ResponseEntity<RatingDTO> updateRating(@RequestBody RatingDTO ratingDTO) throws URISyntaxException {
        log.debug("REST request to update Rating : {}", ratingDTO);
        if (ratingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RatingDTO result = ratingService.save(ratingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ratingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ratings : get all the ratings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ratings in body
     */
    @GetMapping("/ratings")
    public List<RatingDTO> getAllRatings() {
        log.debug("REST request to get all Ratings");
        return ratingService.findAll();
    }

    /**
     * GET  /ratings/:id : get the "id" rating.
     *
     * @param id the id of the ratingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ratingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ratings/{id}")
    public ResponseEntity<RatingDTO> getRating(@PathVariable Long id) {
        log.debug("REST request to get Rating : {}", id);
        Optional<RatingDTO> ratingDTO = ratingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ratingDTO);
    }

    /**
     * DELETE  /ratings/:id : delete the "id" rating.
     *
     * @param id the id of the ratingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ratings/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        log.debug("REST request to delete Rating : {}", id);
        ratingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ratings?query=:query : search for the rating corresponding
     * to the query.
     *
     * @param query the query of the rating search
     * @return the result of the search
     */
    @GetMapping("/_search/ratings")
    public List<RatingDTO> searchRatings(@RequestParam String query) {
        log.debug("REST request to search Ratings for query {}", query);
        return ratingService.search(query);
    }

}
