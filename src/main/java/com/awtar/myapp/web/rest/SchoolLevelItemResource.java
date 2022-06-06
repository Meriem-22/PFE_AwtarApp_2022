package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.SchoolLevelItemRepository;
import com.awtar.myapp.service.SchoolLevelItemService;
import com.awtar.myapp.service.dto.SchoolLevelItemDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.SchoolLevelItem}.
 */
@RestController
@RequestMapping("/api")
public class SchoolLevelItemResource {

    private final Logger log = LoggerFactory.getLogger(SchoolLevelItemResource.class);

    private static final String ENTITY_NAME = "schoolLevelItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchoolLevelItemService schoolLevelItemService;

    private final SchoolLevelItemRepository schoolLevelItemRepository;

    public SchoolLevelItemResource(SchoolLevelItemService schoolLevelItemService, SchoolLevelItemRepository schoolLevelItemRepository) {
        this.schoolLevelItemService = schoolLevelItemService;
        this.schoolLevelItemRepository = schoolLevelItemRepository;
    }

    /**
     * {@code POST  /school-level-items} : Create a new schoolLevelItem.
     *
     * @param schoolLevelItemDTO the schoolLevelItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schoolLevelItemDTO, or with status {@code 400 (Bad Request)} if the schoolLevelItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/school-level-items")
    public ResponseEntity<SchoolLevelItemDTO> createSchoolLevelItem(@Valid @RequestBody SchoolLevelItemDTO schoolLevelItemDTO)
        throws URISyntaxException {
        log.debug("REST request to save SchoolLevelItem : {}", schoolLevelItemDTO);
        if (schoolLevelItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new schoolLevelItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchoolLevelItemDTO result = schoolLevelItemService.save(schoolLevelItemDTO);
        return ResponseEntity
            .created(new URI("/api/school-level-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /school-level-items/:id} : Updates an existing schoolLevelItem.
     *
     * @param id the id of the schoolLevelItemDTO to save.
     * @param schoolLevelItemDTO the schoolLevelItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schoolLevelItemDTO,
     * or with status {@code 400 (Bad Request)} if the schoolLevelItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schoolLevelItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/school-level-items/{id}")
    public ResponseEntity<SchoolLevelItemDTO> updateSchoolLevelItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SchoolLevelItemDTO schoolLevelItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SchoolLevelItem : {}, {}", id, schoolLevelItemDTO);
        if (schoolLevelItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolLevelItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schoolLevelItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SchoolLevelItemDTO result = schoolLevelItemService.update(schoolLevelItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolLevelItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /school-level-items/:id} : Partial updates given fields of an existing schoolLevelItem, field will ignore if it is null
     *
     * @param id the id of the schoolLevelItemDTO to save.
     * @param schoolLevelItemDTO the schoolLevelItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schoolLevelItemDTO,
     * or with status {@code 400 (Bad Request)} if the schoolLevelItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the schoolLevelItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the schoolLevelItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/school-level-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SchoolLevelItemDTO> partialUpdateSchoolLevelItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SchoolLevelItemDTO schoolLevelItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SchoolLevelItem partially : {}, {}", id, schoolLevelItemDTO);
        if (schoolLevelItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolLevelItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schoolLevelItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SchoolLevelItemDTO> result = schoolLevelItemService.partialUpdate(schoolLevelItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolLevelItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /school-level-items} : get all the schoolLevelItems.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schoolLevelItems in body.
     */
    @GetMapping("/school-level-items")
    public ResponseEntity<List<SchoolLevelItemDTO>> getAllSchoolLevelItems(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of SchoolLevelItems");
        Page<SchoolLevelItemDTO> page;
        if (eagerload) {
            page = schoolLevelItemService.findAllWithEagerRelationships(pageable);
        } else {
            page = schoolLevelItemService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /school-level-items/:id} : get the "id" schoolLevelItem.
     *
     * @param id the id of the schoolLevelItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schoolLevelItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/school-level-items/{id}")
    public ResponseEntity<SchoolLevelItemDTO> getSchoolLevelItem(@PathVariable Long id) {
        log.debug("REST request to get SchoolLevelItem : {}", id);
        Optional<SchoolLevelItemDTO> schoolLevelItemDTO = schoolLevelItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(schoolLevelItemDTO);
    }

    /**
     * {@code DELETE  /school-level-items/:id} : delete the "id" schoolLevelItem.
     *
     * @param id the id of the schoolLevelItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/school-level-items/{id}")
    public ResponseEntity<Void> deleteSchoolLevelItem(@PathVariable Long id) {
        log.debug("REST request to delete SchoolLevelItem : {}", id);
        schoolLevelItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
