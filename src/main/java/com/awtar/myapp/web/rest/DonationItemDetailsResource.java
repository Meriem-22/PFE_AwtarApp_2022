package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.DonationItemDetailsRepository;
import com.awtar.myapp.service.DonationItemDetailsService;
import com.awtar.myapp.service.dto.DonationItemDetailsDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.DonationItemDetails}.
 */
@RestController
@RequestMapping("/api")
public class DonationItemDetailsResource {

    private final Logger log = LoggerFactory.getLogger(DonationItemDetailsResource.class);

    private static final String ENTITY_NAME = "donationItemDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DonationItemDetailsService donationItemDetailsService;

    private final DonationItemDetailsRepository donationItemDetailsRepository;

    public DonationItemDetailsResource(
        DonationItemDetailsService donationItemDetailsService,
        DonationItemDetailsRepository donationItemDetailsRepository
    ) {
        this.donationItemDetailsService = donationItemDetailsService;
        this.donationItemDetailsRepository = donationItemDetailsRepository;
    }

    /**
     * {@code POST  /donation-item-details} : Create a new donationItemDetails.
     *
     * @param donationItemDetailsDTO the donationItemDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donationItemDetailsDTO, or with status {@code 400 (Bad Request)} if the donationItemDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/donation-item-details")
    public ResponseEntity<DonationItemDetailsDTO> createDonationItemDetails(
        @Valid @RequestBody DonationItemDetailsDTO donationItemDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save DonationItemDetails : {}", donationItemDetailsDTO);
        if (donationItemDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new donationItemDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DonationItemDetailsDTO result = donationItemDetailsService.save(donationItemDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/donation-item-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /donation-item-details/:id} : Updates an existing donationItemDetails.
     *
     * @param id the id of the donationItemDetailsDTO to save.
     * @param donationItemDetailsDTO the donationItemDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donationItemDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the donationItemDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the donationItemDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/donation-item-details/{id}")
    public ResponseEntity<DonationItemDetailsDTO> updateDonationItemDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DonationItemDetailsDTO donationItemDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DonationItemDetails : {}, {}", id, donationItemDetailsDTO);
        if (donationItemDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donationItemDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donationItemDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DonationItemDetailsDTO result = donationItemDetailsService.update(donationItemDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donationItemDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /donation-item-details/:id} : Partial updates given fields of an existing donationItemDetails, field will ignore if it is null
     *
     * @param id the id of the donationItemDetailsDTO to save.
     * @param donationItemDetailsDTO the donationItemDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donationItemDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the donationItemDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the donationItemDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the donationItemDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/donation-item-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DonationItemDetailsDTO> partialUpdateDonationItemDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DonationItemDetailsDTO donationItemDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DonationItemDetails partially : {}, {}", id, donationItemDetailsDTO);
        if (donationItemDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donationItemDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donationItemDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DonationItemDetailsDTO> result = donationItemDetailsService.partialUpdate(donationItemDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donationItemDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /donation-item-details} : get all the donationItemDetails.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donationItemDetails in body.
     */
    @GetMapping("/donation-item-details")
    public ResponseEntity<List<DonationItemDetailsDTO>> getAllDonationItemDetails(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of DonationItemDetails");
        Page<DonationItemDetailsDTO> page;
        if (eagerload) {
            page = donationItemDetailsService.findAllWithEagerRelationships(pageable);
        } else {
            page = donationItemDetailsService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /donation-item-details/:id} : get the "id" donationItemDetails.
     *
     * @param id the id of the donationItemDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donationItemDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donation-item-details/{id}")
    public ResponseEntity<DonationItemDetailsDTO> getDonationItemDetails(@PathVariable Long id) {
        log.debug("REST request to get DonationItemDetails : {}", id);
        Optional<DonationItemDetailsDTO> donationItemDetailsDTO = donationItemDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(donationItemDetailsDTO);
    }

    /**
     * {@code DELETE  /donation-item-details/:id} : delete the "id" donationItemDetails.
     *
     * @param id the id of the donationItemDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/donation-item-details/{id}")
    public ResponseEntity<Void> deleteDonationItemDetails(@PathVariable Long id) {
        log.debug("REST request to delete DonationItemDetails : {}", id);
        donationItemDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
