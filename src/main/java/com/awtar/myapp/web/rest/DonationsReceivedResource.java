package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.DonationsReceivedRepository;
import com.awtar.myapp.service.DonationsReceivedService;
import com.awtar.myapp.service.dto.DonationsReceivedDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.DonationsReceived}.
 */
@RestController
@RequestMapping("/api")
public class DonationsReceivedResource {

    private final Logger log = LoggerFactory.getLogger(DonationsReceivedResource.class);

    private static final String ENTITY_NAME = "donationsReceived";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DonationsReceivedService donationsReceivedService;

    private final DonationsReceivedRepository donationsReceivedRepository;

    public DonationsReceivedResource(
        DonationsReceivedService donationsReceivedService,
        DonationsReceivedRepository donationsReceivedRepository
    ) {
        this.donationsReceivedService = donationsReceivedService;
        this.donationsReceivedRepository = donationsReceivedRepository;
    }

    /**
     * {@code POST  /donations-receiveds} : Create a new donationsReceived.
     *
     * @param donationsReceivedDTO the donationsReceivedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donationsReceivedDTO, or with status {@code 400 (Bad Request)} if the donationsReceived has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/donations-receiveds")
    public ResponseEntity<DonationsReceivedDTO> createDonationsReceived(@Valid @RequestBody DonationsReceivedDTO donationsReceivedDTO)
        throws URISyntaxException {
        log.debug("REST request to save DonationsReceived : {}", donationsReceivedDTO);
        if (donationsReceivedDTO.getId() != null) {
            throw new BadRequestAlertException("A new donationsReceived cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DonationsReceivedDTO result = donationsReceivedService.save(donationsReceivedDTO);
        return ResponseEntity
            .created(new URI("/api/donations-receiveds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /donations-receiveds/:id} : Updates an existing donationsReceived.
     *
     * @param id the id of the donationsReceivedDTO to save.
     * @param donationsReceivedDTO the donationsReceivedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donationsReceivedDTO,
     * or with status {@code 400 (Bad Request)} if the donationsReceivedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the donationsReceivedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/donations-receiveds/{id}")
    public ResponseEntity<DonationsReceivedDTO> updateDonationsReceived(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DonationsReceivedDTO donationsReceivedDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DonationsReceived : {}, {}", id, donationsReceivedDTO);
        if (donationsReceivedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donationsReceivedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donationsReceivedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DonationsReceivedDTO result = donationsReceivedService.update(donationsReceivedDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donationsReceivedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /donations-receiveds/:id} : Partial updates given fields of an existing donationsReceived, field will ignore if it is null
     *
     * @param id the id of the donationsReceivedDTO to save.
     * @param donationsReceivedDTO the donationsReceivedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donationsReceivedDTO,
     * or with status {@code 400 (Bad Request)} if the donationsReceivedDTO is not valid,
     * or with status {@code 404 (Not Found)} if the donationsReceivedDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the donationsReceivedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/donations-receiveds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DonationsReceivedDTO> partialUpdateDonationsReceived(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DonationsReceivedDTO donationsReceivedDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DonationsReceived partially : {}, {}", id, donationsReceivedDTO);
        if (donationsReceivedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donationsReceivedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donationsReceivedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DonationsReceivedDTO> result = donationsReceivedService.partialUpdate(donationsReceivedDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donationsReceivedDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /donations-receiveds} : get all the donationsReceiveds.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donationsReceiveds in body.
     */
    @GetMapping("/donations-receiveds")
    public ResponseEntity<List<DonationsReceivedDTO>> getAllDonationsReceiveds(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of DonationsReceiveds");
        Page<DonationsReceivedDTO> page;
        if (eagerload) {
            page = donationsReceivedService.findAllWithEagerRelationships(pageable);
        } else {
            page = donationsReceivedService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /donations-receiveds/:id} : get the "id" donationsReceived.
     *
     * @param id the id of the donationsReceivedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donationsReceivedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donations-receiveds/{id}")
    public ResponseEntity<DonationsReceivedDTO> getDonationsReceived(@PathVariable Long id) {
        log.debug("REST request to get DonationsReceived : {}", id);
        Optional<DonationsReceivedDTO> donationsReceivedDTO = donationsReceivedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(donationsReceivedDTO);
    }

    /**
     * {@code DELETE  /donations-receiveds/:id} : delete the "id" donationsReceived.
     *
     * @param id the id of the donationsReceivedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/donations-receiveds/{id}")
    public ResponseEntity<Void> deleteDonationsReceived(@PathVariable Long id) {
        log.debug("REST request to delete DonationsReceived : {}", id);
        donationsReceivedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
