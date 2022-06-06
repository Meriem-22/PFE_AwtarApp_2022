package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.DonationsReceivedItemRepository;
import com.awtar.myapp.service.DonationsReceivedItemService;
import com.awtar.myapp.service.dto.DonationsReceivedItemDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.DonationsReceivedItem}.
 */
@RestController
@RequestMapping("/api")
public class DonationsReceivedItemResource {

    private final Logger log = LoggerFactory.getLogger(DonationsReceivedItemResource.class);

    private static final String ENTITY_NAME = "donationsReceivedItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DonationsReceivedItemService donationsReceivedItemService;

    private final DonationsReceivedItemRepository donationsReceivedItemRepository;

    public DonationsReceivedItemResource(
        DonationsReceivedItemService donationsReceivedItemService,
        DonationsReceivedItemRepository donationsReceivedItemRepository
    ) {
        this.donationsReceivedItemService = donationsReceivedItemService;
        this.donationsReceivedItemRepository = donationsReceivedItemRepository;
    }

    /**
     * {@code POST  /donations-received-items} : Create a new donationsReceivedItem.
     *
     * @param donationsReceivedItemDTO the donationsReceivedItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donationsReceivedItemDTO, or with status {@code 400 (Bad Request)} if the donationsReceivedItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/donations-received-items")
    public ResponseEntity<DonationsReceivedItemDTO> createDonationsReceivedItem(
        @Valid @RequestBody DonationsReceivedItemDTO donationsReceivedItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to save DonationsReceivedItem : {}", donationsReceivedItemDTO);
        if (donationsReceivedItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new donationsReceivedItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DonationsReceivedItemDTO result = donationsReceivedItemService.save(donationsReceivedItemDTO);
        return ResponseEntity
            .created(new URI("/api/donations-received-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /donations-received-items/:id} : Updates an existing donationsReceivedItem.
     *
     * @param id the id of the donationsReceivedItemDTO to save.
     * @param donationsReceivedItemDTO the donationsReceivedItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donationsReceivedItemDTO,
     * or with status {@code 400 (Bad Request)} if the donationsReceivedItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the donationsReceivedItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/donations-received-items/{id}")
    public ResponseEntity<DonationsReceivedItemDTO> updateDonationsReceivedItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DonationsReceivedItemDTO donationsReceivedItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DonationsReceivedItem : {}, {}", id, donationsReceivedItemDTO);
        if (donationsReceivedItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donationsReceivedItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donationsReceivedItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DonationsReceivedItemDTO result = donationsReceivedItemService.update(donationsReceivedItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donationsReceivedItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /donations-received-items/:id} : Partial updates given fields of an existing donationsReceivedItem, field will ignore if it is null
     *
     * @param id the id of the donationsReceivedItemDTO to save.
     * @param donationsReceivedItemDTO the donationsReceivedItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donationsReceivedItemDTO,
     * or with status {@code 400 (Bad Request)} if the donationsReceivedItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the donationsReceivedItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the donationsReceivedItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/donations-received-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DonationsReceivedItemDTO> partialUpdateDonationsReceivedItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DonationsReceivedItemDTO donationsReceivedItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DonationsReceivedItem partially : {}, {}", id, donationsReceivedItemDTO);
        if (donationsReceivedItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donationsReceivedItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donationsReceivedItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DonationsReceivedItemDTO> result = donationsReceivedItemService.partialUpdate(donationsReceivedItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donationsReceivedItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /donations-received-items} : get all the donationsReceivedItems.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donationsReceivedItems in body.
     */
    @GetMapping("/donations-received-items")
    public ResponseEntity<List<DonationsReceivedItemDTO>> getAllDonationsReceivedItems(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of DonationsReceivedItems");
        Page<DonationsReceivedItemDTO> page;
        if (eagerload) {
            page = donationsReceivedItemService.findAllWithEagerRelationships(pageable);
        } else {
            page = donationsReceivedItemService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /donations-received-items/:id} : get the "id" donationsReceivedItem.
     *
     * @param id the id of the donationsReceivedItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donationsReceivedItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donations-received-items/{id}")
    public ResponseEntity<DonationsReceivedItemDTO> getDonationsReceivedItem(@PathVariable Long id) {
        log.debug("REST request to get DonationsReceivedItem : {}", id);
        Optional<DonationsReceivedItemDTO> donationsReceivedItemDTO = donationsReceivedItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(donationsReceivedItemDTO);
    }

    /**
     * {@code DELETE  /donations-received-items/:id} : delete the "id" donationsReceivedItem.
     *
     * @param id the id of the donationsReceivedItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/donations-received-items/{id}")
    public ResponseEntity<Void> deleteDonationsReceivedItem(@PathVariable Long id) {
        log.debug("REST request to delete DonationsReceivedItem : {}", id);
        donationsReceivedItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
