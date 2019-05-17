package ir.redmind.paasho.web.rest;
import ir.redmind.paasho.service.SettingService;
import ir.redmind.paasho.web.rest.errors.BadRequestAlertException;
import ir.redmind.paasho.web.rest.util.HeaderUtil;
import ir.redmind.paasho.service.dto.SettingDTO;
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
 * REST controller for managing Setting.
 */
@RestController
@RequestMapping("/api")
public class SettingResource {

    private final Logger log = LoggerFactory.getLogger(SettingResource.class);

    private static final String ENTITY_NAME = "setting";

    private final SettingService settingService;

    public SettingResource(SettingService settingService) {
        this.settingService = settingService;
    }

    /**
     * POST  /settings : Create a new setting.
     *
     * @param settingDTO the settingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new settingDTO, or with status 400 (Bad Request) if the setting has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/settings")
    public ResponseEntity<SettingDTO> createSetting(@RequestBody SettingDTO settingDTO) throws URISyntaxException {
        log.debug("REST request to save Setting : {}", settingDTO);
        if (settingDTO.getId() != null) {
            throw new BadRequestAlertException("A new setting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SettingDTO result = settingService.save(settingDTO);
        return ResponseEntity.created(new URI("/api/settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /settings : Updates an existing setting.
     *
     * @param settingDTO the settingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated settingDTO,
     * or with status 400 (Bad Request) if the settingDTO is not valid,
     * or with status 500 (Internal Server Error) if the settingDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/settings")
    public ResponseEntity<SettingDTO> updateSetting(@RequestBody SettingDTO settingDTO) throws URISyntaxException {
        log.debug("REST request to update Setting : {}", settingDTO);
        if (settingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SettingDTO result = settingService.save(settingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, settingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /settings : get all the settings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of settings in body
     */
    @GetMapping("/settings")
    public List<SettingDTO> getAllSettings() {
        log.debug("REST request to get all Settings");
        return settingService.findAll();
    }

    /**
     * GET  /settings/:id : get the "id" setting.
     *
     * @param id the id of the settingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the settingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/settings/{id}")
    public ResponseEntity<SettingDTO> getSetting(@PathVariable Long id) {
        log.debug("REST request to get Setting : {}", id);
        Optional<SettingDTO> settingDTO = settingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(settingDTO);
    }

    /**
     * DELETE  /settings/:id : delete the "id" setting.
     *
     * @param id the id of the settingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/settings/{id}")
    public ResponseEntity<Void> deleteSetting(@PathVariable Long id) {
        log.debug("REST request to delete Setting : {}", id);
        settingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/settings?query=:query : search for the setting corresponding
     * to the query.
     *
     * @param query the query of the setting search
     * @return the result of the search
     */
    @GetMapping("/_search/settings")
    public List<SettingDTO> searchSettings(@RequestParam String query) {
        log.debug("REST request to search Settings for query {}", query);
        return settingService.search(query);
    }

}
