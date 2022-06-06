package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.StatusOfHealthRepository;
import com.awtar.myapp.service.StatusOfHealthService;
import com.awtar.myapp.service.dto.StatusOfHealthDTO;
import com.awtar.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.awtar.myapp.domain.StatusOfHealth}.
 */
@RestController
@RequestMapping("/api")
public class StatusOfHealthResource {

    private final Logger log = LoggerFactory.getLogger(StatusOfHealthResource.class);

    private static final String ENTITY_NAME = "statusOfHealth";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatusOfHealthService statusOfHealthService;

    private final StatusOfHealthRepository statusOfHealthRepository;

    public StatusOfHealthResource(StatusOfHealthService statusOfHealthService, StatusOfHealthRepository statusOfHealthRepository) {
        this.statusOfHealthService = statusOfHealthService;
        this.statusOfHealthRepository = statusOfHealthRepository;
    }

    /**
     * {@code POST  /status-of-healths} : Create a new statusOfHealth.
     *
     * @param statusOfHealthDTO the statusOfHealthDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statusOfHealthDTO, or with status {@code 400 (Bad Request)} if the statusOfHealth has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/status-of-healths")
    public ResponseEntity<StatusOfHealthDTO> createStatusOfHealth(@Valid @RequestBody StatusOfHealthDTO statusOfHealthDTO)
        throws URISyntaxException {
        log.debug("REST request to save StatusOfHealth : {}", statusOfHealthDTO);
        if (statusOfHealthDTO.getId() != null) {
            throw new BadRequestAlertException("A new statusOfHealth cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatusOfHealthDTO result = statusOfHealthService.save(statusOfHealthDTO);
        return ResponseEntity
            .created(new URI("/api/status-of-healths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /status-of-healths/:id} : Updates an existing statusOfHealth.
     *
     * @param id the id of the statusOfHealthDTO to save.
     * @param statusOfHealthDTO the statusOfHealthDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusOfHealthDTO,
     * or with status {@code 400 (Bad Request)} if the statusOfHealthDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statusOfHealthDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/status-of-healths/{id}")
    public ResponseEntity<StatusOfHealthDTO> updateStatusOfHealth(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StatusOfHealthDTO statusOfHealthDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StatusOfHealth : {}, {}", id, statusOfHealthDTO);
        if (statusOfHealthDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statusOfHealthDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statusOfHealthRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StatusOfHealthDTO result = statusOfHealthService.update(statusOfHealthDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusOfHealthDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /status-of-healths/:id} : Partial updates given fields of an existing statusOfHealth, field will ignore if it is null
     *
     * @param id the id of the statusOfHealthDTO to save.
     * @param statusOfHealthDTO the statusOfHealthDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusOfHealthDTO,
     * or with status {@code 400 (Bad Request)} if the statusOfHealthDTO is not valid,
     * or with status {@code 404 (Not Found)} if the statusOfHealthDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the statusOfHealthDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/status-of-healths/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StatusOfHealthDTO> partialUpdateStatusOfHealth(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StatusOfHealthDTO statusOfHealthDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StatusOfHealth partially : {}, {}", id, statusOfHealthDTO);
        if (statusOfHealthDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statusOfHealthDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statusOfHealthRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StatusOfHealthDTO> result = statusOfHealthService.partialUpdate(statusOfHealthDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusOfHealthDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /status-of-healths} : get all the statusOfHealths.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statusOfHealths in body.
     */
    @GetMapping("/status-of-healths")
    public ResponseEntity<List<StatusOfHealthDTO>> getAllStatusOfHealths(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of StatusOfHealths");
        Page<StatusOfHealthDTO> page;
        if (eagerload) {
            page = statusOfHealthService.findAllWithEagerRelationships(pageable);
        } else {
            page = statusOfHealthService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /status-of-healths/:id} : get the "id" statusOfHealth.
     *
     * @param id the id of the statusOfHealthDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statusOfHealthDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/status-of-healths/{id}")
    public ResponseEntity<StatusOfHealthDTO> getStatusOfHealth(@PathVariable Long id) {
        log.debug("REST request to get StatusOfHealth : {}", id);
        Optional<StatusOfHealthDTO> statusOfHealthDTO = statusOfHealthService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statusOfHealthDTO);
    }

    /**
     * {@code DELETE  /status-of-healths/:id} : delete the "id" statusOfHealth.
     *
     * @param id the id of the statusOfHealthDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/status-of-healths/{id}")
    public ResponseEntity<Void> deleteStatusOfHealth(@PathVariable Long id) {
        log.debug("REST request to delete StatusOfHealth : {}", id);
        statusOfHealthService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
