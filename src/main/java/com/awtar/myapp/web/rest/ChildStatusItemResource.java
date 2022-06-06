package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.ChildStatusItemRepository;
import com.awtar.myapp.service.ChildStatusItemService;
import com.awtar.myapp.service.dto.ChildStatusItemDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.ChildStatusItem}.
 */
@RestController
@RequestMapping("/api")
public class ChildStatusItemResource {

    private final Logger log = LoggerFactory.getLogger(ChildStatusItemResource.class);

    private static final String ENTITY_NAME = "childStatusItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChildStatusItemService childStatusItemService;

    private final ChildStatusItemRepository childStatusItemRepository;

    public ChildStatusItemResource(ChildStatusItemService childStatusItemService, ChildStatusItemRepository childStatusItemRepository) {
        this.childStatusItemService = childStatusItemService;
        this.childStatusItemRepository = childStatusItemRepository;
    }

    /**
     * {@code POST  /child-status-items} : Create a new childStatusItem.
     *
     * @param childStatusItemDTO the childStatusItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new childStatusItemDTO, or with status {@code 400 (Bad Request)} if the childStatusItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/child-status-items")
    public ResponseEntity<ChildStatusItemDTO> createChildStatusItem(@Valid @RequestBody ChildStatusItemDTO childStatusItemDTO)
        throws URISyntaxException {
        log.debug("REST request to save ChildStatusItem : {}", childStatusItemDTO);
        if (childStatusItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new childStatusItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChildStatusItemDTO result = childStatusItemService.save(childStatusItemDTO);
        return ResponseEntity
            .created(new URI("/api/child-status-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /child-status-items/:id} : Updates an existing childStatusItem.
     *
     * @param id the id of the childStatusItemDTO to save.
     * @param childStatusItemDTO the childStatusItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated childStatusItemDTO,
     * or with status {@code 400 (Bad Request)} if the childStatusItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the childStatusItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/child-status-items/{id}")
    public ResponseEntity<ChildStatusItemDTO> updateChildStatusItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChildStatusItemDTO childStatusItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ChildStatusItem : {}, {}", id, childStatusItemDTO);
        if (childStatusItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, childStatusItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!childStatusItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChildStatusItemDTO result = childStatusItemService.update(childStatusItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, childStatusItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /child-status-items/:id} : Partial updates given fields of an existing childStatusItem, field will ignore if it is null
     *
     * @param id the id of the childStatusItemDTO to save.
     * @param childStatusItemDTO the childStatusItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated childStatusItemDTO,
     * or with status {@code 400 (Bad Request)} if the childStatusItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the childStatusItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the childStatusItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/child-status-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChildStatusItemDTO> partialUpdateChildStatusItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChildStatusItemDTO childStatusItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChildStatusItem partially : {}, {}", id, childStatusItemDTO);
        if (childStatusItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, childStatusItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!childStatusItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChildStatusItemDTO> result = childStatusItemService.partialUpdate(childStatusItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, childStatusItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /child-status-items} : get all the childStatusItems.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of childStatusItems in body.
     */
    @GetMapping("/child-status-items")
    public ResponseEntity<List<ChildStatusItemDTO>> getAllChildStatusItems(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of ChildStatusItems");
        Page<ChildStatusItemDTO> page;
        if (eagerload) {
            page = childStatusItemService.findAllWithEagerRelationships(pageable);
        } else {
            page = childStatusItemService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /child-status-items/:id} : get the "id" childStatusItem.
     *
     * @param id the id of the childStatusItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the childStatusItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/child-status-items/{id}")
    public ResponseEntity<ChildStatusItemDTO> getChildStatusItem(@PathVariable Long id) {
        log.debug("REST request to get ChildStatusItem : {}", id);
        Optional<ChildStatusItemDTO> childStatusItemDTO = childStatusItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(childStatusItemDTO);
    }

    /**
     * {@code DELETE  /child-status-items/:id} : delete the "id" childStatusItem.
     *
     * @param id the id of the childStatusItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/child-status-items/{id}")
    public ResponseEntity<Void> deleteChildStatusItem(@PathVariable Long id) {
        log.debug("REST request to delete ChildStatusItem : {}", id);
        childStatusItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
