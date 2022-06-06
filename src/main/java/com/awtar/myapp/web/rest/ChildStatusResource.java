package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.ChildStatusRepository;
import com.awtar.myapp.service.ChildStatusService;
import com.awtar.myapp.service.dto.ChildStatusDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.ChildStatus}.
 */
@RestController
@RequestMapping("/api")
public class ChildStatusResource {

    private final Logger log = LoggerFactory.getLogger(ChildStatusResource.class);

    private static final String ENTITY_NAME = "childStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChildStatusService childStatusService;

    private final ChildStatusRepository childStatusRepository;

    public ChildStatusResource(ChildStatusService childStatusService, ChildStatusRepository childStatusRepository) {
        this.childStatusService = childStatusService;
        this.childStatusRepository = childStatusRepository;
    }

    /**
     * {@code POST  /child-statuses} : Create a new childStatus.
     *
     * @param childStatusDTO the childStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new childStatusDTO, or with status {@code 400 (Bad Request)} if the childStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/child-statuses")
    public ResponseEntity<ChildStatusDTO> createChildStatus(@Valid @RequestBody ChildStatusDTO childStatusDTO) throws URISyntaxException {
        log.debug("REST request to save ChildStatus : {}", childStatusDTO);
        if (childStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new childStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChildStatusDTO result = childStatusService.save(childStatusDTO);
        return ResponseEntity
            .created(new URI("/api/child-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /child-statuses/:id} : Updates an existing childStatus.
     *
     * @param id the id of the childStatusDTO to save.
     * @param childStatusDTO the childStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated childStatusDTO,
     * or with status {@code 400 (Bad Request)} if the childStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the childStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/child-statuses/{id}")
    public ResponseEntity<ChildStatusDTO> updateChildStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChildStatusDTO childStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ChildStatus : {}, {}", id, childStatusDTO);
        if (childStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, childStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!childStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChildStatusDTO result = childStatusService.update(childStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, childStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /child-statuses/:id} : Partial updates given fields of an existing childStatus, field will ignore if it is null
     *
     * @param id the id of the childStatusDTO to save.
     * @param childStatusDTO the childStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated childStatusDTO,
     * or with status {@code 400 (Bad Request)} if the childStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the childStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the childStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/child-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChildStatusDTO> partialUpdateChildStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChildStatusDTO childStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChildStatus partially : {}, {}", id, childStatusDTO);
        if (childStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, childStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!childStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChildStatusDTO> result = childStatusService.partialUpdate(childStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, childStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /child-statuses} : get all the childStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of childStatuses in body.
     */
    @GetMapping("/child-statuses")
    public ResponseEntity<List<ChildStatusDTO>> getAllChildStatuses(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ChildStatuses");
        Page<ChildStatusDTO> page = childStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /child-statuses/:id} : get the "id" childStatus.
     *
     * @param id the id of the childStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the childStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/child-statuses/{id}")
    public ResponseEntity<ChildStatusDTO> getChildStatus(@PathVariable Long id) {
        log.debug("REST request to get ChildStatus : {}", id);
        Optional<ChildStatusDTO> childStatusDTO = childStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(childStatusDTO);
    }

    /**
     * {@code DELETE  /child-statuses/:id} : delete the "id" childStatus.
     *
     * @param id the id of the childStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/child-statuses/{id}")
    public ResponseEntity<Void> deleteChildStatus(@PathVariable Long id) {
        log.debug("REST request to delete ChildStatus : {}", id);
        childStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
