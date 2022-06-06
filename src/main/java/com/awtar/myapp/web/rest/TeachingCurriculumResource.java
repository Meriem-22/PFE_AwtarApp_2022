package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.TeachingCurriculumRepository;
import com.awtar.myapp.service.TeachingCurriculumService;
import com.awtar.myapp.service.dto.TeachingCurriculumDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.TeachingCurriculum}.
 */
@RestController
@RequestMapping("/api")
public class TeachingCurriculumResource {

    private final Logger log = LoggerFactory.getLogger(TeachingCurriculumResource.class);

    private static final String ENTITY_NAME = "teachingCurriculum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeachingCurriculumService teachingCurriculumService;

    private final TeachingCurriculumRepository teachingCurriculumRepository;

    public TeachingCurriculumResource(
        TeachingCurriculumService teachingCurriculumService,
        TeachingCurriculumRepository teachingCurriculumRepository
    ) {
        this.teachingCurriculumService = teachingCurriculumService;
        this.teachingCurriculumRepository = teachingCurriculumRepository;
    }

    /**
     * {@code POST  /teaching-curricula} : Create a new teachingCurriculum.
     *
     * @param teachingCurriculumDTO the teachingCurriculumDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teachingCurriculumDTO, or with status {@code 400 (Bad Request)} if the teachingCurriculum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/teaching-curricula")
    public ResponseEntity<TeachingCurriculumDTO> createTeachingCurriculum(@Valid @RequestBody TeachingCurriculumDTO teachingCurriculumDTO)
        throws URISyntaxException {
        log.debug("REST request to save TeachingCurriculum : {}", teachingCurriculumDTO);
        if (teachingCurriculumDTO.getId() != null) {
            throw new BadRequestAlertException("A new teachingCurriculum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeachingCurriculumDTO result = teachingCurriculumService.save(teachingCurriculumDTO);
        return ResponseEntity
            .created(new URI("/api/teaching-curricula/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /teaching-curricula/:id} : Updates an existing teachingCurriculum.
     *
     * @param id the id of the teachingCurriculumDTO to save.
     * @param teachingCurriculumDTO the teachingCurriculumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teachingCurriculumDTO,
     * or with status {@code 400 (Bad Request)} if the teachingCurriculumDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teachingCurriculumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/teaching-curricula/{id}")
    public ResponseEntity<TeachingCurriculumDTO> updateTeachingCurriculum(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TeachingCurriculumDTO teachingCurriculumDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TeachingCurriculum : {}, {}", id, teachingCurriculumDTO);
        if (teachingCurriculumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teachingCurriculumDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teachingCurriculumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TeachingCurriculumDTO result = teachingCurriculumService.update(teachingCurriculumDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teachingCurriculumDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /teaching-curricula/:id} : Partial updates given fields of an existing teachingCurriculum, field will ignore if it is null
     *
     * @param id the id of the teachingCurriculumDTO to save.
     * @param teachingCurriculumDTO the teachingCurriculumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teachingCurriculumDTO,
     * or with status {@code 400 (Bad Request)} if the teachingCurriculumDTO is not valid,
     * or with status {@code 404 (Not Found)} if the teachingCurriculumDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the teachingCurriculumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/teaching-curricula/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TeachingCurriculumDTO> partialUpdateTeachingCurriculum(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TeachingCurriculumDTO teachingCurriculumDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TeachingCurriculum partially : {}, {}", id, teachingCurriculumDTO);
        if (teachingCurriculumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, teachingCurriculumDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!teachingCurriculumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TeachingCurriculumDTO> result = teachingCurriculumService.partialUpdate(teachingCurriculumDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teachingCurriculumDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /teaching-curricula} : get all the teachingCurricula.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teachingCurricula in body.
     */
    @GetMapping("/teaching-curricula")
    public ResponseEntity<List<TeachingCurriculumDTO>> getAllTeachingCurricula(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of TeachingCurricula");
        Page<TeachingCurriculumDTO> page;
        if (eagerload) {
            page = teachingCurriculumService.findAllWithEagerRelationships(pageable);
        } else {
            page = teachingCurriculumService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /teaching-curricula/:id} : get the "id" teachingCurriculum.
     *
     * @param id the id of the teachingCurriculumDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teachingCurriculumDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/teaching-curricula/{id}")
    public ResponseEntity<TeachingCurriculumDTO> getTeachingCurriculum(@PathVariable Long id) {
        log.debug("REST request to get TeachingCurriculum : {}", id);
        Optional<TeachingCurriculumDTO> teachingCurriculumDTO = teachingCurriculumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teachingCurriculumDTO);
    }

    /**
     * {@code DELETE  /teaching-curricula/:id} : delete the "id" teachingCurriculum.
     *
     * @param id the id of the teachingCurriculumDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/teaching-curricula/{id}")
    public ResponseEntity<Void> deleteTeachingCurriculum(@PathVariable Long id) {
        log.debug("REST request to delete TeachingCurriculum : {}", id);
        teachingCurriculumService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
