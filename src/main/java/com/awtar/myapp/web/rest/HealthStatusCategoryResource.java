package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.HealthStatusCategoryRepository;
import com.awtar.myapp.service.HealthStatusCategoryService;
import com.awtar.myapp.service.dto.HealthStatusCategoryDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.HealthStatusCategory}.
 */
@RestController
@RequestMapping("/api")
public class HealthStatusCategoryResource {

    private final Logger log = LoggerFactory.getLogger(HealthStatusCategoryResource.class);

    private static final String ENTITY_NAME = "healthStatusCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HealthStatusCategoryService healthStatusCategoryService;

    private final HealthStatusCategoryRepository healthStatusCategoryRepository;

    public HealthStatusCategoryResource(
        HealthStatusCategoryService healthStatusCategoryService,
        HealthStatusCategoryRepository healthStatusCategoryRepository
    ) {
        this.healthStatusCategoryService = healthStatusCategoryService;
        this.healthStatusCategoryRepository = healthStatusCategoryRepository;
    }

    /**
     * {@code POST  /health-status-categories} : Create a new healthStatusCategory.
     *
     * @param healthStatusCategoryDTO the healthStatusCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new healthStatusCategoryDTO, or with status {@code 400 (Bad Request)} if the healthStatusCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/health-status-categories")
    public ResponseEntity<HealthStatusCategoryDTO> createHealthStatusCategory(
        @Valid @RequestBody HealthStatusCategoryDTO healthStatusCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to save HealthStatusCategory : {}", healthStatusCategoryDTO);
        if (healthStatusCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new healthStatusCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HealthStatusCategoryDTO result = healthStatusCategoryService.save(healthStatusCategoryDTO);
        return ResponseEntity
            .created(new URI("/api/health-status-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /health-status-categories/:id} : Updates an existing healthStatusCategory.
     *
     * @param id the id of the healthStatusCategoryDTO to save.
     * @param healthStatusCategoryDTO the healthStatusCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated healthStatusCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the healthStatusCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the healthStatusCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/health-status-categories/{id}")
    public ResponseEntity<HealthStatusCategoryDTO> updateHealthStatusCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HealthStatusCategoryDTO healthStatusCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HealthStatusCategory : {}, {}", id, healthStatusCategoryDTO);
        if (healthStatusCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, healthStatusCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!healthStatusCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HealthStatusCategoryDTO result = healthStatusCategoryService.update(healthStatusCategoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, healthStatusCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /health-status-categories/:id} : Partial updates given fields of an existing healthStatusCategory, field will ignore if it is null
     *
     * @param id the id of the healthStatusCategoryDTO to save.
     * @param healthStatusCategoryDTO the healthStatusCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated healthStatusCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the healthStatusCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the healthStatusCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the healthStatusCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/health-status-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HealthStatusCategoryDTO> partialUpdateHealthStatusCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HealthStatusCategoryDTO healthStatusCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HealthStatusCategory partially : {}, {}", id, healthStatusCategoryDTO);
        if (healthStatusCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, healthStatusCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!healthStatusCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HealthStatusCategoryDTO> result = healthStatusCategoryService.partialUpdate(healthStatusCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, healthStatusCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /health-status-categories} : get all the healthStatusCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of healthStatusCategories in body.
     */
    @GetMapping("/health-status-categories")
    public ResponseEntity<List<HealthStatusCategoryDTO>> getAllHealthStatusCategories(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of HealthStatusCategories");
        Page<HealthStatusCategoryDTO> page = healthStatusCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /health-status-categories/:id} : get the "id" healthStatusCategory.
     *
     * @param id the id of the healthStatusCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the healthStatusCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/health-status-categories/{id}")
    public ResponseEntity<HealthStatusCategoryDTO> getHealthStatusCategory(@PathVariable Long id) {
        log.debug("REST request to get HealthStatusCategory : {}", id);
        Optional<HealthStatusCategoryDTO> healthStatusCategoryDTO = healthStatusCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(healthStatusCategoryDTO);
    }

    /**
     * {@code DELETE  /health-status-categories/:id} : delete the "id" healthStatusCategory.
     *
     * @param id the id of the healthStatusCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/health-status-categories/{id}")
    public ResponseEntity<Void> deleteHealthStatusCategory(@PathVariable Long id) {
        log.debug("REST request to delete HealthStatusCategory : {}", id);
        healthStatusCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
