package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.AuthorizingOfficerRepository;
import com.awtar.myapp.service.AuthorizingOfficerService;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import com.awtar.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.awtar.myapp.domain.AuthorizingOfficer}.
 */
@RestController
@RequestMapping("/api")
public class AuthorizingOfficerResource {

    private final Logger log = LoggerFactory.getLogger(AuthorizingOfficerResource.class);

    private static final String ENTITY_NAME = "authorizingOfficer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuthorizingOfficerService authorizingOfficerService;

    private final AuthorizingOfficerRepository authorizingOfficerRepository;

    public AuthorizingOfficerResource(
        AuthorizingOfficerService authorizingOfficerService,
        AuthorizingOfficerRepository authorizingOfficerRepository
    ) {
        this.authorizingOfficerService = authorizingOfficerService;
        this.authorizingOfficerRepository = authorizingOfficerRepository;
    }

    /**
     * {@code POST  /authorizing-officers} : Create a new authorizingOfficer.
     *
     * @param authorizingOfficerDTO the authorizingOfficerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new authorizingOfficerDTO, or with status {@code 400 (Bad Request)} if the authorizingOfficer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/authorizing-officers")
    public ResponseEntity<AuthorizingOfficerDTO> createAuthorizingOfficer(@Valid @RequestBody AuthorizingOfficerDTO authorizingOfficerDTO)
        throws URISyntaxException {
        log.debug("REST request to save AuthorizingOfficer : {}", authorizingOfficerDTO);
        if (authorizingOfficerDTO.getId() != null) {
            throw new BadRequestAlertException("A new authorizingOfficer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuthorizingOfficerDTO result = authorizingOfficerService.save(authorizingOfficerDTO);
        return ResponseEntity
            .created(new URI("/api/authorizing-officers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /authorizing-officers/:id} : Updates an existing authorizingOfficer.
     *
     * @param id the id of the authorizingOfficerDTO to save.
     * @param authorizingOfficerDTO the authorizingOfficerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authorizingOfficerDTO,
     * or with status {@code 400 (Bad Request)} if the authorizingOfficerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the authorizingOfficerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/authorizing-officers/{id}")
    public ResponseEntity<AuthorizingOfficerDTO> updateAuthorizingOfficer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AuthorizingOfficerDTO authorizingOfficerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AuthorizingOfficer : {}, {}", id, authorizingOfficerDTO);
        if (authorizingOfficerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, authorizingOfficerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authorizingOfficerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AuthorizingOfficerDTO result = authorizingOfficerService.update(authorizingOfficerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, authorizingOfficerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /authorizing-officers/:id} : Partial updates given fields of an existing authorizingOfficer, field will ignore if it is null
     *
     * @param id the id of the authorizingOfficerDTO to save.
     * @param authorizingOfficerDTO the authorizingOfficerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authorizingOfficerDTO,
     * or with status {@code 400 (Bad Request)} if the authorizingOfficerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the authorizingOfficerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the authorizingOfficerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/authorizing-officers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AuthorizingOfficerDTO> partialUpdateAuthorizingOfficer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AuthorizingOfficerDTO authorizingOfficerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AuthorizingOfficer partially : {}, {}", id, authorizingOfficerDTO);
        if (authorizingOfficerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, authorizingOfficerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authorizingOfficerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AuthorizingOfficerDTO> result = authorizingOfficerService.partialUpdate(authorizingOfficerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, authorizingOfficerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /authorizing-officers} : get all the authorizingOfficers.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of authorizingOfficers in body.
     */
    @GetMapping("/authorizing-officers")
    public ResponseEntity<List<AuthorizingOfficerDTO>> getAllAuthorizingOfficers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("authorizingofficerprofile-is-null".equals(filter)) {
            log.debug("REST request to get all AuthorizingOfficers where authorizingOfficerProfile is null");
            return new ResponseEntity<>(authorizingOfficerService.findAllWhereAuthorizingOfficerProfileIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of AuthorizingOfficers");
        Page<AuthorizingOfficerDTO> page = authorizingOfficerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /authorizing-officers/:id} : get the "id" authorizingOfficer.
     *
     * @param id the id of the authorizingOfficerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the authorizingOfficerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/authorizing-officers/{id}")
    public ResponseEntity<AuthorizingOfficerDTO> getAuthorizingOfficer(@PathVariable Long id) {
        log.debug("REST request to get AuthorizingOfficer : {}", id);
        Optional<AuthorizingOfficerDTO> authorizingOfficerDTO = authorizingOfficerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(authorizingOfficerDTO);
    }

    /**
     * {@code DELETE  /authorizing-officers/:id} : delete the "id" authorizingOfficer.
     *
     * @param id the id of the authorizingOfficerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/authorizing-officers/{id}")
    public ResponseEntity<Void> deleteAuthorizingOfficer(@PathVariable Long id) {
        log.debug("REST request to delete AuthorizingOfficer : {}", id);
        authorizingOfficerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code POST  /authorizing-officers} : Create a new authorizingOfficer with profile info.
     *
     * @param authorizingOfficerDTO the authorizingOfficerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new authorizingOfficerDTO, or with status {@code 400 (Bad Request)} if the authorizingOfficer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/authorizing-officers/add")
    public ResponseEntity<AuthorizingOfficerDTO> addAuthorizingOfficer(@Valid @RequestBody AuthorizingOfficerDTO authorizingOfficerDTO)
        throws URISyntaxException {
        log.debug("REST request to save AuthorizingOfficer : {}", authorizingOfficerDTO);
        if (authorizingOfficerDTO.getId() != null) {
            throw new BadRequestAlertException("A new authorizingOfficer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuthorizingOfficerDTO result = authorizingOfficerService.add(authorizingOfficerDTO);
        return ResponseEntity
            .created(new URI("/api/authorizing-officers/add/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     *
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of authorizingOfficers in body.
     */
    @GetMapping("/authorizing-officers/details")
    public ResponseEntity<List<AuthorizingOfficerDTO>> getAuthorizingOfficersDetails() {
        log.debug("REST request to get authorizing Officers details  : {}");
        List<AuthorizingOfficerDTO> authorizingOfficerDTOC = authorizingOfficerService.findAuthorizingOfficersDetails();
        return ResponseEntity.ok().body(authorizingOfficerDTOC);
    }
}
