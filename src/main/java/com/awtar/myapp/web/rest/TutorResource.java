package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.TutorRepository;
import com.awtar.myapp.service.TutorService;
import com.awtar.myapp.service.dto.TutorDTO;
import com.awtar.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.awtar.myapp.domain.Tutor}.
 */
@RestController
@RequestMapping("/api")
public class TutorResource {

    private final Logger log = LoggerFactory.getLogger(TutorResource.class);

    private static final String ENTITY_NAME = "tutor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TutorService tutorService;

    private final TutorRepository tutorRepository;

    public TutorResource(TutorService tutorService, TutorRepository tutorRepository) {
        this.tutorService = tutorService;
        this.tutorRepository = tutorRepository;
    }

    /**
     * {@code POST  /tutors} : Create a new tutor.
     *
     * @param tutorDTO the tutorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tutorDTO, or with status {@code 400 (Bad Request)} if the tutor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tutors")
    public ResponseEntity<TutorDTO> createTutor(@RequestBody TutorDTO tutorDTO) throws URISyntaxException {
        log.debug("REST request to save Tutor : {}", tutorDTO);
        if (tutorDTO.getId() != null) {
            throw new BadRequestAlertException("A new tutor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TutorDTO result = tutorService.save(tutorDTO);
        return ResponseEntity
            .created(new URI("/api/tutors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tutors/:id} : Updates an existing tutor.
     *
     * @param id the id of the tutorDTO to save.
     * @param tutorDTO the tutorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tutorDTO,
     * or with status {@code 400 (Bad Request)} if the tutorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tutorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tutors/{id}")
    public ResponseEntity<TutorDTO> updateTutor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TutorDTO tutorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tutor : {}, {}", id, tutorDTO);
        if (tutorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tutorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tutorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TutorDTO result = tutorService.update(tutorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tutorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tutors/:id} : Partial updates given fields of an existing tutor, field will ignore if it is null
     *
     * @param id the id of the tutorDTO to save.
     * @param tutorDTO the tutorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tutorDTO,
     * or with status {@code 400 (Bad Request)} if the tutorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tutorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tutorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tutors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TutorDTO> partialUpdateTutor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TutorDTO tutorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tutor partially : {}, {}", id, tutorDTO);
        if (tutorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tutorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tutorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TutorDTO> result = tutorService.partialUpdate(tutorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tutorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tutors} : get all the tutors.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tutors in body.
     */
    @GetMapping("/tutors")
    public ResponseEntity<List<TutorDTO>> getAllTutors(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("tutorprofile-is-null".equals(filter)) {
            log.debug("REST request to get all Tutors where tutorProfile is null");
            return new ResponseEntity<>(tutorService.findAllWhereTutorProfileIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Tutors");
        Page<TutorDTO> page = tutorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tutors/:id} : get the "id" tutor.
     *
     * @param id the id of the tutorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tutorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tutors/{id}")
    public ResponseEntity<TutorDTO> getTutor(@PathVariable Long id) {
        log.debug("REST request to get Tutor : {}", id);
        Optional<TutorDTO> tutorDTO = tutorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tutorDTO);
    }

    /**
     * {@code DELETE  /tutors/:id} : delete the "id" tutor.
     *
     * @param id the id of the tutorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tutors/{id}")
    public ResponseEntity<Void> deleteTutor(@PathVariable Long id) {
        log.debug("REST request to delete Tutor : {}", id);
        tutorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code POST  /tutors} : Create a new tutor with profile info.
     *
     * @param tutorDTO the tutorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tutorDTO, or with status {@code 400 (Bad Request)} if the tutor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tutors/add")
    public ResponseEntity<TutorDTO> addTutor(@RequestBody TutorDTO tutorDTO) throws URISyntaxException {
        log.debug("REST request to save Tutor : {}", tutorDTO);
        if (tutorDTO.getId() != null) {
            throw new BadRequestAlertException("A new tutor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TutorDTO result = tutorService.add(tutorDTO);
        return ResponseEntity
            .created(new URI("/api/tutors/add/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     *
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tutors in body.
     */
    @GetMapping("/tutors/details")
    public ResponseEntity<List<TutorDTO>> getTutorsDetails() {
        log.debug("REST request to get  tutors details : {}");
        List<TutorDTO> tutorDTOC = tutorService.findTutorsDetails();
        return ResponseEntity.ok().body(tutorDTOC);
    }
}
