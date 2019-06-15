package ir.redmind.paasho.web.rest;
import ir.redmind.paasho.service.MediaService;
import ir.redmind.paasho.web.rest.errors.BadRequestAlertException;
import ir.redmind.paasho.web.rest.util.HeaderUtil;
import ir.redmind.paasho.service.dto.MediaDTO;
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
 * REST controller for managing Media.
 */
@RestController
@RequestMapping("/api")
public class MediaResource {

    private final Logger log = LoggerFactory.getLogger(MediaResource.class);

    private static final String ENTITY_NAME = "media";

    private final MediaService mediaService;

    public MediaResource(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    /**
     * POST  /media : Create a new media.
     *
     * @param mediaDTO the mediaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mediaDTO, or with status 400 (Bad Request) if the media has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/media")
    public ResponseEntity<MediaDTO> createMedia(@RequestBody MediaDTO mediaDTO) throws URISyntaxException {
        log.debug("REST request to save Media : {}", mediaDTO);
        if (mediaDTO.getId() != null) {
            throw new BadRequestAlertException("A new media cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MediaDTO result = mediaService.save(mediaDTO);
        return ResponseEntity.created(new URI("/api/media/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /media : Updates an existing media.
     *
     * @param mediaDTO the mediaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mediaDTO,
     * or with status 400 (Bad Request) if the mediaDTO is not valid,
     * or with status 500 (Internal Server Error) if the mediaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/media")
    public ResponseEntity<MediaDTO> updateMedia(@RequestBody MediaDTO mediaDTO) throws URISyntaxException {
        log.debug("REST request to update Media : {}", mediaDTO);
        if (mediaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MediaDTO result = mediaService.save(mediaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mediaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /media : get all the media.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of media in body
     */
    @GetMapping("/media")
    public List<MediaDTO> getAllMedia() {
        log.debug("REST request to get all Media");
        return mediaService.findAll();
    }

    @GetMapping("/media/avatar")
    public List<MediaDTO> getAllMediaByType() {
        log.debug("REST request to get all Media");
        return mediaService.findAvatar();
    }

    /**
     * GET  /media/:id : get the "id" media.
     *
     * @param id the id of the mediaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mediaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/media/{id}")
    public ResponseEntity<MediaDTO> getMedia(@PathVariable Long id) {
        log.debug("REST request to get Media : {}", id);
        Optional<MediaDTO> mediaDTO = mediaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mediaDTO);
    }
    @GetMapping("/media/category/{id}")
    public ResponseEntity<List<String>> getCategoryMedia(@PathVariable Long id) {
        log.debug("REST request to get Media : {}", id);
        List<String> mediaDTO = mediaService.findCategory(id);
        return ResponseUtil.wrapOrNotFound(Optional.of(mediaDTO));
    }

    /**
     * DELETE  /media/:id : delete the "id" media.
     *
     * @param id the id of the mediaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/media/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long id) {
        log.debug("REST request to delete Media : {}", id);
        mediaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/media?query=:query : search for the media corresponding
     * to the query.
     *
     * @param query the query of the media search
     * @return the result of the search
     */
    @GetMapping("/_search/media")
    public List<MediaDTO> searchMedia(@RequestParam String query) {
        log.debug("REST request to search Media for query {}", query);
        return mediaService.search(query);
    }

}
