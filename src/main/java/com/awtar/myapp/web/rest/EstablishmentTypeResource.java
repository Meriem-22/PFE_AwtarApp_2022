package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.EstablishmentTypeRepository;
import com.awtar.myapp.service.EstablishmentTypeService;
import com.awtar.myapp.service.dto.EstablishmentTypeDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.EstablishmentType}.
 */
@RestController
@RequestMapping("/api")
public class EstablishmentTypeResource {

    private final Logger log = LoggerFactory.getLogger(EstablishmentTypeResource.class);

    private static final String ENTITY_NAME = "establishmentType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstablishmentTypeService establishmentTypeService;

    private final EstablishmentTypeRepository establishmentTypeRepository;

    public EstablishmentTypeResource(
        EstablishmentTypeService establishmentTypeService,
        EstablishmentTypeRepository establishmentTypeRepository
    ) {
        this.establishmentTypeService = establishmentTypeService;
        this.establishmentTypeRepository = establishmentTypeRepository;
    }

    /**
     * {@code POST  /establishment-types} : Create a new establishmentType.
     *
     * @param establishmentTypeDTO the establishmentTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new establishmentTypeDTO, or with status {@code 400 (Bad Request)} if the establishmentType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/establishment-types")
    public ResponseEntity<EstablishmentTypeDTO> createEstablishmentType(@Valid @RequestBody EstablishmentTypeDTO establishmentTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save EstablishmentType : {}", establishmentTypeDTO);
        if (establishmentTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new establishmentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstablishmentTypeDTO result = establishmentTypeService.save(establishmentTypeDTO);
        return ResponseEntity
            .created(new URI("/api/establishment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /establishment-types/:id} : Updates an existing establishmentType.
     *
     * @param id the id of the establishmentTypeDTO to save.
     * @param establishmentTypeDTO the establishmentTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated establishmentTypeDTO,
     * or with status {@code 400 (Bad Request)} if the establishmentTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the establishmentTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/establishment-types/{id}")
    public ResponseEntity<EstablishmentTypeDTO> updateEstablishmentType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EstablishmentTypeDTO establishmentTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EstablishmentType : {}, {}", id, establishmentTypeDTO);
        if (establishmentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, establishmentTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!establishmentTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EstablishmentTypeDTO result = establishmentTypeService.update(establishmentTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, establishmentTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /establishment-types/:id} : Partial updates given fields of an existing establishmentType, field will ignore if it is null
     *
     * @param id the id of the establishmentTypeDTO to save.
     * @param establishmentTypeDTO the establishmentTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated establishmentTypeDTO,
     * or with status {@code 400 (Bad Request)} if the establishmentTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the establishmentTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the establishmentTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/establishment-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EstablishmentTypeDTO> partialUpdateEstablishmentType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EstablishmentTypeDTO establishmentTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EstablishmentType partially : {}, {}", id, establishmentTypeDTO);
        if (establishmentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, establishmentTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!establishmentTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstablishmentTypeDTO> result = establishmentTypeService.partialUpdate(establishmentTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, establishmentTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /establishment-types} : get all the establishmentTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of establishmentTypes in body.
     */
    @GetMapping("/establishment-types")
    public ResponseEntity<List<EstablishmentTypeDTO>> getAllEstablishmentTypes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of EstablishmentTypes");
        Page<EstablishmentTypeDTO> page = establishmentTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /establishment-types/:id} : get the "id" establishmentType.
     *
     * @param id the id of the establishmentTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the establishmentTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/establishment-types/{id}")
    public ResponseEntity<EstablishmentTypeDTO> getEstablishmentType(@PathVariable Long id) {
        log.debug("REST request to get EstablishmentType : {}", id);
        Optional<EstablishmentTypeDTO> establishmentTypeDTO = establishmentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(establishmentTypeDTO);
    }

    /**
     * {@code DELETE  /establishment-types/:id} : delete the "id" establishmentType.
     *
     * @param id the id of the establishmentTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/establishment-types/{id}")
    public ResponseEntity<Void> deleteEstablishmentType(@PathVariable Long id) {
        log.debug("REST request to delete EstablishmentType : {}", id);
        establishmentTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
