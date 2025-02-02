package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.EducationalInstitutionRepository;
import com.awtar.myapp.service.EducationalInstitutionService;
import com.awtar.myapp.service.dto.EducationalInstitutionDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.EducationalInstitution}.
 */
@RestController
@RequestMapping("/api")
public class EducationalInstitutionResource {

    private final Logger log = LoggerFactory.getLogger(EducationalInstitutionResource.class);

    private static final String ENTITY_NAME = "educationalInstitution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EducationalInstitutionService educationalInstitutionService;

    private final EducationalInstitutionRepository educationalInstitutionRepository;

    public EducationalInstitutionResource(
        EducationalInstitutionService educationalInstitutionService,
        EducationalInstitutionRepository educationalInstitutionRepository
    ) {
        this.educationalInstitutionService = educationalInstitutionService;
        this.educationalInstitutionRepository = educationalInstitutionRepository;
    }

    /**
     * {@code POST  /educational-institutions} : Create a new educationalInstitution.
     *
     * @param educationalInstitutionDTO the educationalInstitutionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new educationalInstitutionDTO, or with status {@code 400 (Bad Request)} if the educationalInstitution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/educational-institutions")
    public ResponseEntity<EducationalInstitutionDTO> createEducationalInstitution(
        @Valid @RequestBody EducationalInstitutionDTO educationalInstitutionDTO
    ) throws URISyntaxException {
        log.debug("REST request to save EducationalInstitution : {}", educationalInstitutionDTO);
        if (educationalInstitutionDTO.getId() != null) {
            throw new BadRequestAlertException("A new educationalInstitution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EducationalInstitutionDTO result = educationalInstitutionService.save(educationalInstitutionDTO);
        return ResponseEntity
            .created(new URI("/api/educational-institutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /educational-institutions/:id} : Updates an existing educationalInstitution.
     *
     * @param id the id of the educationalInstitutionDTO to save.
     * @param educationalInstitutionDTO the educationalInstitutionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educationalInstitutionDTO,
     * or with status {@code 400 (Bad Request)} if the educationalInstitutionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the educationalInstitutionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/educational-institutions/{id}")
    public ResponseEntity<EducationalInstitutionDTO> updateEducationalInstitution(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EducationalInstitutionDTO educationalInstitutionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EducationalInstitution : {}, {}", id, educationalInstitutionDTO);
        if (educationalInstitutionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, educationalInstitutionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!educationalInstitutionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EducationalInstitutionDTO result = educationalInstitutionService.update(educationalInstitutionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, educationalInstitutionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /educational-institutions/:id} : Partial updates given fields of an existing educationalInstitution, field will ignore if it is null
     *
     * @param id the id of the educationalInstitutionDTO to save.
     * @param educationalInstitutionDTO the educationalInstitutionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educationalInstitutionDTO,
     * or with status {@code 400 (Bad Request)} if the educationalInstitutionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the educationalInstitutionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the educationalInstitutionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/educational-institutions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EducationalInstitutionDTO> partialUpdateEducationalInstitution(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EducationalInstitutionDTO educationalInstitutionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EducationalInstitution partially : {}, {}", id, educationalInstitutionDTO);
        if (educationalInstitutionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, educationalInstitutionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!educationalInstitutionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EducationalInstitutionDTO> result = educationalInstitutionService.partialUpdate(educationalInstitutionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, educationalInstitutionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /educational-institutions} : get all the educationalInstitutions.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of educationalInstitutions in body.
     */
    @GetMapping("/educational-institutions")
    public ResponseEntity<List<EducationalInstitutionDTO>> getAllEducationalInstitutions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of EducationalInstitutions");
        Page<EducationalInstitutionDTO> page;
        if (eagerload) {
            page = educationalInstitutionService.findAllWithEagerRelationships(pageable);
        } else {
            page = educationalInstitutionService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /educational-institutions/:id} : get the "id" educationalInstitution.
     *
     * @param id the id of the educationalInstitutionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the educationalInstitutionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/educational-institutions/{id}")
    public ResponseEntity<EducationalInstitutionDTO> getEducationalInstitution(@PathVariable Long id) {
        log.debug("REST request to get EducationalInstitution : {}", id);
        Optional<EducationalInstitutionDTO> educationalInstitutionDTO = educationalInstitutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(educationalInstitutionDTO);
    }

    /**
     * {@code DELETE  /educational-institutions/:id} : delete the "id" educationalInstitution.
     *
     * @param id the id of the educationalInstitutionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/educational-institutions/{id}")
    public ResponseEntity<Void> deleteEducationalInstitution(@PathVariable Long id) {
        log.debug("REST request to delete EducationalInstitution : {}", id);
        educationalInstitutionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
