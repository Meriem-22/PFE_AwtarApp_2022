package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.ChildRepository;
import com.awtar.myapp.service.ChildService;
import com.awtar.myapp.service.dto.ChildDTO;
import com.awtar.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
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
 * REST controller for managing {@link com.awtar.myapp.domain.Child}.
 */
@RestController
@RequestMapping("/api")
public class ChildResource {

    private final Logger log = LoggerFactory.getLogger(ChildResource.class);

    private static final String ENTITY_NAME = "child";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChildService childService;

    private final ChildRepository childRepository;

    public ChildResource(ChildService childService, ChildRepository childRepository) {
        this.childService = childService;
        this.childRepository = childRepository;
    }

    /**
     * {@code POST  /children} : Create a new child.
     *
     * @param childDTO the childDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new childDTO, or with status {@code 400 (Bad Request)} if the child has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/children")
    public ResponseEntity<ChildDTO> createChild(@RequestBody ChildDTO childDTO) throws URISyntaxException {
        log.debug("REST request to save Child : {}", childDTO);
        if (childDTO.getId() != null) {
            throw new BadRequestAlertException("A new child cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChildDTO result = childService.save(childDTO);
        return ResponseEntity
            .created(new URI("/api/children/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /children/:id} : Updates an existing child.
     *
     * @param id the id of the childDTO to save.
     * @param childDTO the childDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated childDTO,
     * or with status {@code 400 (Bad Request)} if the childDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the childDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/children/{id}")
    public ResponseEntity<ChildDTO> updateChild(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChildDTO childDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Child : {}, {}", id, childDTO);
        if (childDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, childDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!childRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChildDTO result = childService.update(childDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, childDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /children/:id} : Partial updates given fields of an existing child, field will ignore if it is null
     *
     * @param id the id of the childDTO to save.
     * @param childDTO the childDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated childDTO,
     * or with status {@code 400 (Bad Request)} if the childDTO is not valid,
     * or with status {@code 404 (Not Found)} if the childDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the childDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/children/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChildDTO> partialUpdateChild(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChildDTO childDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Child partially : {}, {}", id, childDTO);
        if (childDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, childDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!childRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChildDTO> result = childService.partialUpdate(childDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, childDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /children} : get all the children.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of children in body.
     */
    @GetMapping("/children")
    public ResponseEntity<List<ChildDTO>> getAllChildren(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("childprofile-is-null".equals(filter)) {
            log.debug("REST request to get all Childs where childProfile is null");
            return new ResponseEntity<>(childService.findAllWhereChildProfileIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Children");
        Page<ChildDTO> page = childService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /children/:id} : get the "id" child.
     *
     * @param id the id of the childDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the childDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/children/{id}")
    public ResponseEntity<ChildDTO> getChild(@PathVariable Long id) {
        log.debug("REST request to get Child : {}", id);
        Optional<ChildDTO> childDTO = childService.findOne(id);
        return ResponseUtil.wrapOrNotFound(childDTO);
    }

    /**
     * {@code DELETE  /children/:id} : delete the "id" child.
     *
     * @param id the id of the childDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/children/{id}")
    public ResponseEntity<Void> deleteChild(@PathVariable Long id) {
        log.debug("REST request to delete Child : {}", id);
        childService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code POST  /children} : Create a new child.
     *
     * @param childDTO the childDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new childDTO, or with status {@code 400 (Bad Request)} if the child has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/children/add")
    public ResponseEntity<ChildDTO> createChildAllDetails(@Valid @RequestBody ChildDTO childDTO) throws URISyntaxException {
        log.debug("REST request to save Child : {}", childDTO);
        if (childDTO.getId() != null) {
            throw new BadRequestAlertException("A new child cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChildDTO result = childService.saveChildAllDetails(childDTO);
        return ResponseEntity
            .created(new URI("/api/children/add/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     *
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the childDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/children/{beginningYear}/details")
    public ResponseEntity<List<ChildDTO>> getAllChildrenDetails(@PathVariable String beginningYear) {
        log.debug("REST request to get  all children  : {}");
        List<ChildDTO> childDTOC = childService.getAllChildrenDetails(beginningYear);
        return ResponseEntity.ok().body(childDTOC);
    }

    /**
     *
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the childDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/children/details")
    public ResponseEntity<List<ChildDTO>> getChildrenDetails() {
        log.debug("REST request to get  all children  : {}");
        List<ChildDTO> childDTOC = childService.getChildrenDetails();
        return ResponseEntity.ok().body(childDTOC);
    }

    /**
     *
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the childDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/children/{beginningYear}/school-details")
    public ResponseEntity<List<ChildDTO>> getAllChildrenSchoolDetails(@PathVariable String beginningYear) {
        log.debug("REST request to get  all children  : {}");
        List<ChildDTO> childDTOC = childService.getAllChildrenSchoolDetails(beginningYear);
        return ResponseEntity.ok().body(childDTOC);
    }

    /**
     *
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the childDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/children/without-family/details")
    public ResponseEntity<List<ChildDTO>> getChildrenWithoutFamilyDetails() {
        log.debug("REST request to get  all children  : {}");
        List<ChildDTO> childDTOC = childService.getChildrenWithoutFamilyDetails();
        return ResponseEntity.ok().body(childDTOC);
    }

    /**
     *
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the childDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/children/place-of-residence/{id}")
    public ResponseEntity<ChildDTO> getChildrenNumberByCity(@PathVariable Long id) {
        log.debug("REST request to get  number of children in a city : {}");
        ChildDTO childDTOC = childService.getChildrenNumberByCity(id);
        return ResponseEntity.ok().body(childDTOC);
    }
}
