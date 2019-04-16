package ir.redmind.paasho.web.rest;
import ir.redmind.paasho.service.User1Service;
import ir.redmind.paasho.web.rest.errors.BadRequestAlertException;
import ir.redmind.paasho.web.rest.util.HeaderUtil;
import ir.redmind.paasho.service.dto.User1DTO;
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
 * REST controller for managing User1.
 */
@RestController
@RequestMapping("/api")
public class User1Resource {

    private final Logger log = LoggerFactory.getLogger(User1Resource.class);

    private static final String ENTITY_NAME = "user1";

    private final User1Service user1Service;

    public User1Resource(User1Service user1Service) {
        this.user1Service = user1Service;
    }

    /**
     * POST  /user-1-s : Create a new user1.
     *
     * @param user1DTO the user1DTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user1DTO, or with status 400 (Bad Request) if the user1 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-1-s")
    public ResponseEntity<User1DTO> createUser1(@RequestBody User1DTO user1DTO) throws URISyntaxException {
        log.debug("REST request to save User1 : {}", user1DTO);
        if (user1DTO.getId() != null) {
            throw new BadRequestAlertException("A new user1 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User1DTO result = user1Service.save(user1DTO);
        return ResponseEntity.created(new URI("/api/user-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-1-s : Updates an existing user1.
     *
     * @param user1DTO the user1DTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user1DTO,
     * or with status 400 (Bad Request) if the user1DTO is not valid,
     * or with status 500 (Internal Server Error) if the user1DTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-1-s")
    public ResponseEntity<User1DTO> updateUser1(@RequestBody User1DTO user1DTO) throws URISyntaxException {
        log.debug("REST request to update User1 : {}", user1DTO);
        if (user1DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        User1DTO result = user1Service.save(user1DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, user1DTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-1-s : get all the user1S.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of user1S in body
     */
    @GetMapping("/user-1-s")
    public List<User1DTO> getAllUser1S(@RequestParam(required = false) String filter,@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        if ("event-is-null".equals(filter)) {
            log.debug("REST request to get all User1s where event is null");
            return user1Service.findAllWhereEventIsNull();
        }
        log.debug("REST request to get all User1S");
        return user1Service.findAll();
    }

    /**
     * GET  /user-1-s/:id : get the "id" user1.
     *
     * @param id the id of the user1DTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the user1DTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-1-s/{id}")
    public ResponseEntity<User1DTO> getUser1(@PathVariable Long id) {
        log.debug("REST request to get User1 : {}", id);
        Optional<User1DTO> user1DTO = user1Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(user1DTO);
    }

    /**
     * DELETE  /user-1-s/:id : delete the "id" user1.
     *
     * @param id the id of the user1DTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-1-s/{id}")
    public ResponseEntity<Void> deleteUser1(@PathVariable Long id) {
        log.debug("REST request to delete User1 : {}", id);
        user1Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-1-s?query=:query : search for the user1 corresponding
     * to the query.
     *
     * @param query the query of the user1 search
     * @return the result of the search
     */
    @GetMapping("/_search/user-1-s")
    public List<User1DTO> searchUser1S(@RequestParam String query) {
        log.debug("REST request to search User1S for query {}", query);
        return user1Service.search(query);
    }

}
