package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.SchoolLevelRepository;
import com.awtar.myapp.service.SchoolLevelService;
import com.awtar.myapp.service.dto.SchoolLevelDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.SchoolLevel}.
 */
@RestController
@RequestMapping("/api")
public class SchoolLevelResource {

    private final Logger log = LoggerFactory.getLogger(SchoolLevelResource.class);

    private static final String ENTITY_NAME = "schoolLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchoolLevelService schoolLevelService;

    private final SchoolLevelRepository schoolLevelRepository;

    public SchoolLevelResource(SchoolLevelService schoolLevelService, SchoolLevelRepository schoolLevelRepository) {
        this.schoolLevelService = schoolLevelService;
        this.schoolLevelRepository = schoolLevelRepository;
    }

    /**
     * {@code POST  /school-levels} : Create a new schoolLevel.
     *
     * @param schoolLevelDTO the schoolLevelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schoolLevelDTO, or with status {@code 400 (Bad Request)} if the schoolLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/school-levels")
    public ResponseEntity<SchoolLevelDTO> createSchoolLevel(@Valid @RequestBody SchoolLevelDTO schoolLevelDTO) throws URISyntaxException {
        log.debug("REST request to save SchoolLevel : {}", schoolLevelDTO);
        if (schoolLevelDTO.getId() != null) {
            throw new BadRequestAlertException("A new schoolLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchoolLevelDTO result = schoolLevelService.save(schoolLevelDTO);
        return ResponseEntity
            .created(new URI("/api/school-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /school-levels/:id} : Updates an existing schoolLevel.
     *
     * @param id the id of the schoolLevelDTO to save.
     * @param schoolLevelDTO the schoolLevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schoolLevelDTO,
     * or with status {@code 400 (Bad Request)} if the schoolLevelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schoolLevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/school-levels/{id}")
    public ResponseEntity<SchoolLevelDTO> updateSchoolLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SchoolLevelDTO schoolLevelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SchoolLevel : {}, {}", id, schoolLevelDTO);
        if (schoolLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolLevelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schoolLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SchoolLevelDTO result = schoolLevelService.update(schoolLevelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolLevelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /school-levels/:id} : Partial updates given fields of an existing schoolLevel, field will ignore if it is null
     *
     * @param id the id of the schoolLevelDTO to save.
     * @param schoolLevelDTO the schoolLevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schoolLevelDTO,
     * or with status {@code 400 (Bad Request)} if the schoolLevelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the schoolLevelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the schoolLevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/school-levels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SchoolLevelDTO> partialUpdateSchoolLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SchoolLevelDTO schoolLevelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SchoolLevel partially : {}, {}", id, schoolLevelDTO);
        if (schoolLevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolLevelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!schoolLevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SchoolLevelDTO> result = schoolLevelService.partialUpdate(schoolLevelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolLevelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /school-levels} : get all the schoolLevels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schoolLevels in body.
     */
    @GetMapping("/school-levels")
    public ResponseEntity<List<SchoolLevelDTO>> getAllSchoolLevels(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SchoolLevels");
        Page<SchoolLevelDTO> page = schoolLevelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /school-levels/:id} : get the "id" schoolLevel.
     *
     * @param id the id of the schoolLevelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schoolLevelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/school-levels/{id}")
    public ResponseEntity<SchoolLevelDTO> getSchoolLevel(@PathVariable Long id) {
        log.debug("REST request to get SchoolLevel : {}", id);
        Optional<SchoolLevelDTO> schoolLevelDTO = schoolLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(schoolLevelDTO);
    }

    /**
     * {@code DELETE  /school-levels/:id} : delete the "id" schoolLevel.
     *
     * @param id the id of the schoolLevelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/school-levels/{id}")
    public ResponseEntity<Void> deleteSchoolLevel(@PathVariable Long id) {
        log.debug("REST request to delete SchoolLevel : {}", id);
        schoolLevelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
