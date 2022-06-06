package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.ParentRepository;
import com.awtar.myapp.service.ParentService;
import com.awtar.myapp.service.dto.ParentDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.Parent}.
 */
@RestController
@RequestMapping("/api")
public class ParentResource {

    private final Logger log = LoggerFactory.getLogger(ParentResource.class);

    private static final String ENTITY_NAME = "parent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParentService parentService;

    private final ParentRepository parentRepository;

    public ParentResource(ParentService parentService, ParentRepository parentRepository) {
        this.parentService = parentService;
        this.parentRepository = parentRepository;
    }

    /**
     * {@code POST  /parents} : Create a new parent.
     *
     * @param parentDTO the parentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parentDTO, or with status {@code 400 (Bad Request)} if the parent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parents")
    public ResponseEntity<ParentDTO> createParent(@Valid @RequestBody ParentDTO parentDTO) throws URISyntaxException {
        log.debug("REST request to save Parent : {}", parentDTO);
        if (parentDTO.getId() != null) {
            throw new BadRequestAlertException("A new parent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParentDTO result = parentService.save(parentDTO);
        return ResponseEntity
            .created(new URI("/api/parents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parents/:id} : Updates an existing parent.
     *
     * @param id the id of the parentDTO to save.
     * @param parentDTO the parentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parentDTO,
     * or with status {@code 400 (Bad Request)} if the parentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parents/{id}")
    public ResponseEntity<ParentDTO> updateParent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ParentDTO parentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Parent : {}, {}", id, parentDTO);
        if (parentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ParentDTO result = parentService.update(parentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /parents/:id} : Partial updates given fields of an existing parent, field will ignore if it is null
     *
     * @param id the id of the parentDTO to save.
     * @param parentDTO the parentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parentDTO,
     * or with status {@code 400 (Bad Request)} if the parentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the parentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the parentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/parents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ParentDTO> partialUpdateParent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ParentDTO parentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Parent partially : {}, {}", id, parentDTO);
        if (parentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParentDTO> result = parentService.partialUpdate(parentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /parents} : get all the parents.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parents in body.
     */
    @GetMapping("/parents")
    public ResponseEntity<List<ParentDTO>> getAllParents(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("parentprofile-is-null".equals(filter)) {
            log.debug("REST request to get all Parents where parentProfile is null");
            return new ResponseEntity<>(parentService.findAllWhereParentProfileIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Parents");
        Page<ParentDTO> page = parentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /parents/:id} : get the "id" parent.
     *
     * @param id the id of the parentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parents/{id}")
    public ResponseEntity<ParentDTO> getParent(@PathVariable Long id) {
        log.debug("REST request to get Parent : {}", id);
        Optional<ParentDTO> parentDTO = parentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parentDTO);
    }

    /**
     * {@code DELETE  /parents/:id} : delete the "id" parent.
     *
     * @param id the id of the parentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parents/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        log.debug("REST request to delete Parent : {}", id);
        parentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code POST  /parents} : Create a new parent.
     *
     * @param parentDTO the parentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parentDTO, or with status {@code 400 (Bad Request)} if the parent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parents/add")
    public ResponseEntity<ParentDTO> createParentAllDetails(@Valid @RequestBody ParentDTO parentDTO) throws URISyntaxException {
        log.debug("REST request to save Parent : {}", parentDTO);
        if (parentDTO.getId() != null) {
            throw new BadRequestAlertException("A new parent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParentDTO result = parentService.saveParentAllDetails(parentDTO);
        return ResponseEntity
            .created(new URI("/api/parents/add/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
