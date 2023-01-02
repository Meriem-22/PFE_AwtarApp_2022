package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.DonationsIssuedRepository;
import com.awtar.myapp.service.DonationsIssuedService;
import com.awtar.myapp.service.dto.DonationsIssuedDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.DonationsIssued}.
 */
@RestController
@RequestMapping("/api")
public class DonationsIssuedResource {

    private final Logger log = LoggerFactory.getLogger(DonationsIssuedResource.class);

    private static final String ENTITY_NAME = "donationsIssued";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DonationsIssuedService donationsIssuedService;

    private final DonationsIssuedRepository donationsIssuedRepository;

    public DonationsIssuedResource(DonationsIssuedService donationsIssuedService, DonationsIssuedRepository donationsIssuedRepository) {
        this.donationsIssuedService = donationsIssuedService;
        this.donationsIssuedRepository = donationsIssuedRepository;
    }

    /**
     * {@code POST  /donations-issueds} : Create a new donationsIssued.
     *
     * @param donationsIssuedDTO the donationsIssuedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donationsIssuedDTO, or with status {@code 400 (Bad Request)} if the donationsIssued has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/donations-issueds")
    public ResponseEntity<DonationsIssuedDTO> createDonationsIssued(@Valid @RequestBody DonationsIssuedDTO donationsIssuedDTO)
        throws URISyntaxException {
        log.debug("REST request to save DonationsIssued : {}", donationsIssuedDTO);
        if (donationsIssuedDTO.getId() != null) {
            throw new BadRequestAlertException("A new donationsIssued cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DonationsIssuedDTO result = donationsIssuedService.save(donationsIssuedDTO);
        return ResponseEntity
            .created(new URI("/api/donations-issueds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /donations-issueds/:id} : Updates an existing donationsIssued.
     *
     * @param id the id of the donationsIssuedDTO to save.
     * @param donationsIssuedDTO the donationsIssuedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donationsIssuedDTO,
     * or with status {@code 400 (Bad Request)} if the donationsIssuedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the donationsIssuedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/donations-issueds/{id}")
    public ResponseEntity<DonationsIssuedDTO> updateDonationsIssued(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DonationsIssuedDTO donationsIssuedDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DonationsIssued : {}, {}", id, donationsIssuedDTO);
        if (donationsIssuedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donationsIssuedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donationsIssuedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DonationsIssuedDTO result = donationsIssuedService.update(donationsIssuedDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donationsIssuedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /donations-issueds/:id} : Partial updates given fields of an existing donationsIssued, field will ignore if it is null
     *
     * @param id the id of the donationsIssuedDTO to save.
     * @param donationsIssuedDTO the donationsIssuedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donationsIssuedDTO,
     * or with status {@code 400 (Bad Request)} if the donationsIssuedDTO is not valid,
     * or with status {@code 404 (Not Found)} if the donationsIssuedDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the donationsIssuedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/donations-issueds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DonationsIssuedDTO> partialUpdateDonationsIssued(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DonationsIssuedDTO donationsIssuedDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DonationsIssued partially : {}, {}", id, donationsIssuedDTO);
        if (donationsIssuedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donationsIssuedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donationsIssuedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DonationsIssuedDTO> result = donationsIssuedService.partialUpdate(donationsIssuedDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donationsIssuedDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /donations-issueds} : get all the donationsIssueds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donationsIssueds in body.
     */
    @GetMapping("/donations-issueds")
    public ResponseEntity<List<DonationsIssuedDTO>> getAllDonationsIssueds(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of DonationsIssueds");
        Page<DonationsIssuedDTO> page = donationsIssuedService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /donations-issueds/:id} : get the "id" donationsIssued.
     *
     * @param id the id of the donationsIssuedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donationsIssuedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donations-issueds/{id}")
    public ResponseEntity<DonationsIssuedDTO> getDonationsIssued(@PathVariable Long id) {
        log.debug("REST request to get DonationsIssued : {}", id);
        Optional<DonationsIssuedDTO> donationsIssuedDTO = donationsIssuedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(donationsIssuedDTO);
    }

    /**
     * {@code DELETE  /donations-issueds/:id} : delete the "id" donationsIssued.
     *
     * @param id the id of the donationsIssuedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/donations-issueds/{id}")
    public ResponseEntity<Void> deleteDonationsIssued(@PathVariable Long id) {
        log.debug("REST request to delete DonationsIssued : {}", id);
        donationsIssuedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code POST  /donations-issueds} : Create a new donationsIssued.
     *
     * @param donationsIssuedDTO the donationsIssuedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donationsIssuedDTO, or with status {@code 400 (Bad Request)} if the donationsIssued has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/donations-issueds/add")
    public ResponseEntity<DonationsIssuedDTO> createCompletedDonationsIssued(@Valid @RequestBody DonationsIssuedDTO donationsIssuedDTO)
        throws URISyntaxException {
        log.debug("REST request to save DonationsIssued : {}", donationsIssuedDTO);
        if (donationsIssuedDTO.getId() != null) {
            throw new BadRequestAlertException("A new donationsIssued cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DonationsIssuedDTO result = donationsIssuedService.saveCompletedDonationsIssued(donationsIssuedDTO);
        return ResponseEntity
            .created(new URI("/api/donations-issueds/add" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /donations-issueds/validated} : get the  donationsIssued.
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donationsIssuedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donations-issueds/validated")
    public ResponseEntity<List<DonationsIssuedDTO>> getValidatedDonationsIssued() {
        log.debug("REST request to get DonationsIssued : {}");
        List<DonationsIssuedDTO> donationsIssuedDTO = donationsIssuedService.getLastValidatedDonations();
        return ResponseEntity.ok().body(donationsIssuedDTO);
    }

    /**
     * {@code GET  /donations-issueds/validated} : get the  donationsIssued.
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donationsIssuedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donations-issueds/upcoming-scheduled-donations")
    public ResponseEntity<List<DonationsIssuedDTO>> UpcomingScheduledDonationsIssued() {
        log.debug("REST request to get DonationsIssued : {}");
        List<DonationsIssuedDTO> donationsIssuedDTO = donationsIssuedService.Upcomingscheduleddonations();
        return ResponseEntity.ok().body(donationsIssuedDTO);
    }

    /**
     * {@code GET  /donations-issueds/upcoming-validated-donations} : get the  donationsIssued.
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donationsIssuedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donations-issueds/upcoming-validated-donations")
    public ResponseEntity<List<DonationsIssuedDTO>> UpcomingValidatedDonationsIssued() {
        log.debug("REST request to get DonationsIssued : {}");
        List<DonationsIssuedDTO> donationsIssuedDTO = donationsIssuedService.UpcomingDonationsValidated();
        return ResponseEntity.ok().body(donationsIssuedDTO);
    }

    /**
     * {@code GET  /donations-issueds/validated} : get the  donationsIssued.
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donationsIssuedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donations-issueds/by-month")
    public ResponseEntity<List<DonationsIssuedDTO>> DonationsIssuedOfCurrentYearByMoth() {
        log.debug("REST request to get DonationsIssued by month : {}");
        List<DonationsIssuedDTO> donationsIssuedDTO = donationsIssuedService.IssuedDonationsOfCurrentYearByMonth();
        return ResponseEntity.ok().body(donationsIssuedDTO);
    }

    /**
     * {@code GET  /donations-issueds/validated} : get the  donationsIssued.
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donationsIssuedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donations-issueds/canceled-by-month")
    public ResponseEntity<List<DonationsIssuedDTO>> CanceledDonationsOfCurrentYearByMoth() {
        log.debug("REST request to get DonationsIssued by month : {}");
        List<DonationsIssuedDTO> donationsIssuedDTO = donationsIssuedService.CanceledDonationsOfCurrentYearByMonth();
        return ResponseEntity.ok().body(donationsIssuedDTO);
    }

    /**
     * {@code GET  /donations-issueds/validated} : get the  donationsIssued.
     *
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donationsIssuedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donations-issueds/get-all")
    public ResponseEntity<List<DonationsIssuedDTO>> GetAll() {
        log.debug("REST request to get DonationsIssued  : {}");
        List<DonationsIssuedDTO> donationsIssuedDTO = donationsIssuedService.GetAll();
        return ResponseEntity.ok().body(donationsIssuedDTO);
    }
}
