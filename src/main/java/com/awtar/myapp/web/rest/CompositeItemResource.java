package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.CompositeItemRepository;
import com.awtar.myapp.service.CompositeItemService;
import com.awtar.myapp.service.dto.CompositeItemDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.CompositeItem}.
 */
@RestController
@RequestMapping("/api")
public class CompositeItemResource {

    private final Logger log = LoggerFactory.getLogger(CompositeItemResource.class);

    private static final String ENTITY_NAME = "compositeItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompositeItemService compositeItemService;

    private final CompositeItemRepository compositeItemRepository;

    public CompositeItemResource(CompositeItemService compositeItemService, CompositeItemRepository compositeItemRepository) {
        this.compositeItemService = compositeItemService;
        this.compositeItemRepository = compositeItemRepository;
    }

    /**
     * {@code POST  /composite-items} : Create a new compositeItem.
     *
     * @param compositeItemDTO the compositeItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compositeItemDTO, or with status {@code 400 (Bad Request)} if the compositeItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/composite-items")
    public ResponseEntity<CompositeItemDTO> createCompositeItem(@Valid @RequestBody CompositeItemDTO compositeItemDTO)
        throws URISyntaxException {
        log.debug("REST request to save CompositeItem : {}", compositeItemDTO);
        if (compositeItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new compositeItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompositeItemDTO result = compositeItemService.save(compositeItemDTO);
        return ResponseEntity
            .created(new URI("/api/composite-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /composite-items/:id} : Updates an existing compositeItem.
     *
     * @param id the id of the compositeItemDTO to save.
     * @param compositeItemDTO the compositeItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compositeItemDTO,
     * or with status {@code 400 (Bad Request)} if the compositeItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compositeItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/composite-items/{id}")
    public ResponseEntity<CompositeItemDTO> updateCompositeItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompositeItemDTO compositeItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CompositeItem : {}, {}", id, compositeItemDTO);
        if (compositeItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, compositeItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compositeItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompositeItemDTO result = compositeItemService.update(compositeItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compositeItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /composite-items/:id} : Partial updates given fields of an existing compositeItem, field will ignore if it is null
     *
     * @param id the id of the compositeItemDTO to save.
     * @param compositeItemDTO the compositeItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compositeItemDTO,
     * or with status {@code 400 (Bad Request)} if the compositeItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the compositeItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the compositeItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/composite-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompositeItemDTO> partialUpdateCompositeItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompositeItemDTO compositeItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompositeItem partially : {}, {}", id, compositeItemDTO);
        if (compositeItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, compositeItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compositeItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompositeItemDTO> result = compositeItemService.partialUpdate(compositeItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compositeItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /composite-items} : get all the compositeItems.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compositeItems in body.
     */
    @GetMapping("/composite-items")
    public ResponseEntity<List<CompositeItemDTO>> getAllCompositeItems(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of CompositeItems");
        Page<CompositeItemDTO> page;
        if (eagerload) {
            page = compositeItemService.findAllWithEagerRelationships(pageable);
        } else {
            page = compositeItemService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /composite-items/:id} : get the "id" compositeItem.
     *
     * @param id the id of the compositeItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compositeItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/composite-items/{id}")
    public ResponseEntity<CompositeItemDTO> getCompositeItem(@PathVariable Long id) {
        log.debug("REST request to get CompositeItem : {}", id);
        Optional<CompositeItemDTO> compositeItemDTO = compositeItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compositeItemDTO);
    }

    /**
     * {@code DELETE  /composite-items/:id} : delete the "id" compositeItem.
     *
     * @param id the id of the compositeItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/composite-items/{id}")
    public ResponseEntity<Void> deleteCompositeItem(@PathVariable Long id) {
        log.debug("REST request to delete CompositeItem : {}", id);
        compositeItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
