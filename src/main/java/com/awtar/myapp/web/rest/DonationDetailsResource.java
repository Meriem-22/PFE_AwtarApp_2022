package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.DonationDetailsRepository;
import com.awtar.myapp.service.DonationDetailsService;
import com.awtar.myapp.service.dto.DonationDetailsDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.DonationDetails}.
 */
@RestController
@RequestMapping("/api")
public class DonationDetailsResource {

    private final Logger log = LoggerFactory.getLogger(DonationDetailsResource.class);

    private static final String ENTITY_NAME = "donationDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DonationDetailsService donationDetailsService;

    private final DonationDetailsRepository donationDetailsRepository;

    public DonationDetailsResource(DonationDetailsService donationDetailsService, DonationDetailsRepository donationDetailsRepository) {
        this.donationDetailsService = donationDetailsService;
        this.donationDetailsRepository = donationDetailsRepository;
    }

    /**
     * {@code POST  /donation-details} : Create a new donationDetails.
     *
     * @param donationDetailsDTO the donationDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donationDetailsDTO, or with status {@code 400 (Bad Request)} if the donationDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/donation-details")
    public ResponseEntity<DonationDetailsDTO> createDonationDetails(@Valid @RequestBody DonationDetailsDTO donationDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save DonationDetails : {}", donationDetailsDTO);
        if (donationDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new donationDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DonationDetailsDTO result = donationDetailsService.save(donationDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/donation-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /donation-details/:id} : Updates an existing donationDetails.
     *
     * @param id the id of the donationDetailsDTO to save.
     * @param donationDetailsDTO the donationDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donationDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the donationDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the donationDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/donation-details/{id}")
    public ResponseEntity<DonationDetailsDTO> updateDonationDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DonationDetailsDTO donationDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DonationDetails : {}, {}", id, donationDetailsDTO);
        if (donationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donationDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donationDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DonationDetailsDTO result = donationDetailsService.update(donationDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donationDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /donation-details/:id} : Partial updates given fields of an existing donationDetails, field will ignore if it is null
     *
     * @param id the id of the donationDetailsDTO to save.
     * @param donationDetailsDTO the donationDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donationDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the donationDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the donationDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the donationDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/donation-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DonationDetailsDTO> partialUpdateDonationDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DonationDetailsDTO donationDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DonationDetails partially : {}, {}", id, donationDetailsDTO);
        if (donationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donationDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donationDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DonationDetailsDTO> result = donationDetailsService.partialUpdate(donationDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donationDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /donation-details} : get all the donationDetails.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donationDetails in body.
     */
    @GetMapping("/donation-details")
    public ResponseEntity<List<DonationDetailsDTO>> getAllDonationDetails(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of DonationDetails");
        Page<DonationDetailsDTO> page;
        if (eagerload) {
            page = donationDetailsService.findAllWithEagerRelationships(pageable);
        } else {
            page = donationDetailsService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /donation-details/:id} : get the "id" donationDetails.
     *
     * @param id the id of the donationDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donationDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donation-details/{id}")
    public ResponseEntity<DonationDetailsDTO> getDonationDetails(@PathVariable Long id) {
        log.debug("REST request to get DonationDetails : {}", id);
        Optional<DonationDetailsDTO> donationDetailsDTO = donationDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(donationDetailsDTO);
    }

    /**
     * {@code DELETE  /donation-details/:id} : delete the "id" donationDetails.
     *
     * @param id the id of the donationDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/donation-details/{id}")
    public ResponseEntity<Void> deleteDonationDetails(@PathVariable Long id) {
        log.debug("REST request to delete DonationDetails : {}", id);
        donationDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /donation-details/:id} : get the "id" donation-details.
     *
     * @param id the id of the donation-detailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donation-detailsDTO, or with status {@code 404 (Not Found)}.
     */

    @GetMapping("/donation-details/beneficiary/donation-list/{id}")
    public ResponseEntity<List<DonationDetailsDTO>> getParentsProfile(@PathVariable Long id) {
        log.debug("REST request to get Beneficiary : {}", id);
        List<DonationDetailsDTO> donationDetailsDTO = donationDetailsService.findAllBeneficiaryDonationList(id);
        return ResponseEntity.ok().body(donationDetailsDTO);
    }

    /**
     * {@code GET  /donation-details} : get all the donationDetails.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donationDetails in body.
     */
    @GetMapping("/donation-details/beneficiary/{id}")
    public ResponseEntity<List<DonationDetailsDTO>> getParentsProfile(
        @PathVariable Long id,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of DonationDetails");
        Page<DonationDetailsDTO> page;
        if (eagerload) {
            page = donationDetailsService.findAllBeneficiaryDonation(id, pageable);
        } else {
            page = donationDetailsService.findAllBeneficiaryDonation(id, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
