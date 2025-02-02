package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.BeneficiaryRepository;
import com.awtar.myapp.service.BeneficiaryService;
import com.awtar.myapp.service.dto.BeneficiaryDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.Beneficiary}.
 */
@RestController
@RequestMapping("/api")
public class BeneficiaryResource {

    private final Logger log = LoggerFactory.getLogger(BeneficiaryResource.class);

    private static final String ENTITY_NAME = "beneficiary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeneficiaryService beneficiaryService;

    private final BeneficiaryRepository beneficiaryRepository;

    public BeneficiaryResource(BeneficiaryService beneficiaryService, BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryService = beneficiaryService;
        this.beneficiaryRepository = beneficiaryRepository;
    }

    /**
     * {@code POST  /beneficiaries} : Create a new beneficiary.
     *
     * @param beneficiaryDTO the beneficiaryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beneficiaryDTO, or with status {@code 400 (Bad Request)} if the beneficiary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/beneficiaries")
    public ResponseEntity<BeneficiaryDTO> createBeneficiary(@Valid @RequestBody BeneficiaryDTO beneficiaryDTO) throws URISyntaxException {
        log.debug("REST request to save Beneficiary : {}", beneficiaryDTO);
        if (beneficiaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new beneficiary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BeneficiaryDTO result = beneficiaryService.save(beneficiaryDTO);
        return ResponseEntity
            .created(new URI("/api/beneficiaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /beneficiaries/:id} : Updates an existing beneficiary.
     *
     * @param id the id of the beneficiaryDTO to save.
     * @param beneficiaryDTO the beneficiaryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beneficiaryDTO,
     * or with status {@code 400 (Bad Request)} if the beneficiaryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beneficiaryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/beneficiaries/{id}")
    public ResponseEntity<BeneficiaryDTO> updateBeneficiary(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BeneficiaryDTO beneficiaryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Beneficiary : {}, {}", id, beneficiaryDTO);
        if (beneficiaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beneficiaryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beneficiaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BeneficiaryDTO result = beneficiaryService.update(beneficiaryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beneficiaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /beneficiaries/:id} : Partial updates given fields of an existing beneficiary, field will ignore if it is null
     *
     * @param id the id of the beneficiaryDTO to save.
     * @param beneficiaryDTO the beneficiaryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beneficiaryDTO,
     * or with status {@code 400 (Bad Request)} if the beneficiaryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the beneficiaryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the beneficiaryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/beneficiaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BeneficiaryDTO> partialUpdateBeneficiary(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BeneficiaryDTO beneficiaryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Beneficiary partially : {}, {}", id, beneficiaryDTO);
        if (beneficiaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beneficiaryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beneficiaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BeneficiaryDTO> result = beneficiaryService.partialUpdate(beneficiaryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beneficiaryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /beneficiaries} : get all the beneficiaries.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beneficiaries in body.
     */
    @GetMapping("/beneficiaries")
    public ResponseEntity<List<BeneficiaryDTO>> getAllBeneficiaries(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Beneficiaries");
        Page<BeneficiaryDTO> page;
        if (eagerload) {
            page = beneficiaryService.findAllWithEagerRelationships(pageable);
        } else {
            page = beneficiaryService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /beneficiaries/:id} : get the "id" beneficiary.
     *
     * @param id the id of the beneficiaryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beneficiaryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/beneficiaries/{id}")
    public ResponseEntity<BeneficiaryDTO> getBeneficiary(@PathVariable Long id) {
        log.debug("REST request to get Beneficiary : {}", id);
        Optional<BeneficiaryDTO> beneficiaryDTO = beneficiaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(beneficiaryDTO);
    }

    /**
     * {@code DELETE  /beneficiaries/:id} : delete the "id" beneficiary.
     *
     * @param id the id of the beneficiaryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/beneficiaries/{id}")
    public ResponseEntity<Void> deleteBeneficiary(@PathVariable Long id) {
        log.debug("REST request to delete Beneficiary : {}", id);
        beneficiaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PutMapping("/beneficiaries/calcul-reference/{id}")
    public ResponseEntity<BeneficiaryDTO> CalculbeneficiaryReference(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BeneficiaryDTO beneficiaryDTO
    ) throws URISyntaxException {
        log.debug("REST request to calcul Beneficiary Reference: : {}, {}", id, beneficiaryDTO);
        if (beneficiaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beneficiaryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beneficiaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BeneficiaryDTO result = beneficiaryService.calculReference(beneficiaryDTO);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beneficiaryDTO.getId().toString()))
            .body(result);
    }

    @PutMapping("/beneficiaries/contributor/authorizing-officer/{id}")
    public ResponseEntity<BeneficiaryDTO> EditAuthorizingOfficer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BeneficiaryDTO beneficiaryDTO
    ) throws URISyntaxException {
        log.debug("REST request to Edit Contributors: : {}, {}", id, beneficiaryDTO);
        if (beneficiaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        BeneficiaryDTO result = beneficiaryService.updateAuthorizingOfficer(beneficiaryDTO);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beneficiaryDTO.getId().toString()))
            .body(result);
    }

    @PutMapping("/beneficiaries/contributor/tutor/{id}")
    public ResponseEntity<BeneficiaryDTO> EditTutor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BeneficiaryDTO beneficiaryDTO
    ) throws URISyntaxException {
        log.debug("REST request to Edit Contributors: : {}, {}", id, beneficiaryDTO);
        if (beneficiaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        BeneficiaryDTO result = beneficiaryService.updateTutor(beneficiaryDTO);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beneficiaryDTO.getId().toString()))
            .body(result);
    }

    /**
     *
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beneficiaryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/beneficiaries/families/{id}")
    public ResponseEntity<List<BeneficiaryDTO>> getAllContributorFamilies(@PathVariable Long id) {
        log.debug("REST request to get  all Families : {}");
        List<BeneficiaryDTO> beneficiaryDTOC = beneficiaryService.findAllFamiliesContributor(id);
        return ResponseEntity.ok().body(beneficiaryDTOC);
    }

    /**
     *
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beneficiaryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/beneficiaries/children/{id}")
    public ResponseEntity<List<BeneficiaryDTO>> getAllContributorChildren(@PathVariable Long id) {
        log.debug("REST request to get  all Children : {}");
        List<BeneficiaryDTO> beneficiaryDTOC = beneficiaryService.findAllChildrenContributor(id);
        return ResponseEntity.ok().body(beneficiaryDTOC);
    }

    /**
     *
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beneficiaryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/beneficiaries/establishments/{id}")
    public ResponseEntity<List<BeneficiaryDTO>> getAllContributorEstablishments(@PathVariable Long id) {
        log.debug("REST request to get  all Establishments : {}");
        List<BeneficiaryDTO> beneficiaryDTOC = beneficiaryService.findAllEstablishmentsContributor(id);
        return ResponseEntity.ok().body(beneficiaryDTOC);
    }

    @PutMapping("/beneficiaries/remove-authorizing-officer/{id}")
    public ResponseEntity<BeneficiaryDTO> removeAuthorizingOfficer(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BeneficiaryDTO beneficiaryDTO
    ) throws URISyntaxException {
        log.debug("REST request to remove Contributor authorizing officer : : {}, {}", id, beneficiaryDTO);
        if (beneficiaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        BeneficiaryDTO result = beneficiaryService.removeAuthorizingOfficer(beneficiaryDTO);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beneficiaryDTO.getId().toString()))
            .body(result);
    }

    @PutMapping("/beneficiaries/remove-tutor/{id}")
    public ResponseEntity<BeneficiaryDTO> removeTutor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BeneficiaryDTO beneficiaryDTO
    ) throws URISyntaxException {
        log.debug("REST request to remove Contributor tutor : : {}, {}", id, beneficiaryDTO);
        if (beneficiaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        BeneficiaryDTO result = beneficiaryService.removeTutor(beneficiaryDTO);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beneficiaryDTO.getId().toString()))
            .body(result);
    }

    /**
     *
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beneficiaryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/beneficiaries/all")
    public ResponseEntity<List<BeneficiaryDTO>> getAllBeneficiaires() {
        log.debug("REST request to get  all Beneficiaires : {}");
        List<BeneficiaryDTO> beneficiaryDTOC = beneficiaryService.TotalBeneficiaries();
        return ResponseEntity.ok().body(beneficiaryDTOC);
    }
}
