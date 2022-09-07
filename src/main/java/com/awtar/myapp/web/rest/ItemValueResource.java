package com.awtar.myapp.web.rest;

import com.awtar.myapp.repository.ItemValueRepository;
import com.awtar.myapp.service.ItemValueService;
import com.awtar.myapp.service.dto.ItemValueDTO;
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
 * REST controller for managing {@link com.awtar.myapp.domain.ItemValue}.
 */
@RestController
@RequestMapping("/api")
public class ItemValueResource {

    private final Logger log = LoggerFactory.getLogger(ItemValueResource.class);

    private static final String ENTITY_NAME = "itemValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemValueService itemValueService;

    private final ItemValueRepository itemValueRepository;

    public ItemValueResource(ItemValueService itemValueService, ItemValueRepository itemValueRepository) {
        this.itemValueService = itemValueService;
        this.itemValueRepository = itemValueRepository;
    }

    /**
     * {@code POST  /item-values} : Create a new itemValue.
     *
     * @param itemValueDTO the itemValueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemValueDTO, or with status {@code 400 (Bad Request)} if the itemValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-values")
    public ResponseEntity<ItemValueDTO> createItemValue(@Valid @RequestBody ItemValueDTO itemValueDTO) throws URISyntaxException {
        log.debug("REST request to save ItemValue : {}", itemValueDTO);
        if (itemValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new itemValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemValueDTO result = itemValueService.save(itemValueDTO);
        return ResponseEntity
            .created(new URI("/api/item-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-values/:id} : Updates an existing itemValue.
     *
     * @param id the id of the itemValueDTO to save.
     * @param itemValueDTO the itemValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemValueDTO,
     * or with status {@code 400 (Bad Request)} if the itemValueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-values/{id}")
    public ResponseEntity<ItemValueDTO> updateItemValue(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ItemValueDTO itemValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ItemValue : {}, {}", id, itemValueDTO);
        if (itemValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemValueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ItemValueDTO result = itemValueService.update(itemValueDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /item-values/:id} : Partial updates given fields of an existing itemValue, field will ignore if it is null
     *
     * @param id the id of the itemValueDTO to save.
     * @param itemValueDTO the itemValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemValueDTO,
     * or with status {@code 400 (Bad Request)} if the itemValueDTO is not valid,
     * or with status {@code 404 (Not Found)} if the itemValueDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the itemValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/item-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ItemValueDTO> partialUpdateItemValue(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ItemValueDTO itemValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ItemValue partially : {}, {}", id, itemValueDTO);
        if (itemValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemValueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ItemValueDTO> result = itemValueService.partialUpdate(itemValueDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemValueDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /item-values} : get all the itemValues.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemValues in body.
     */
    @GetMapping("/item-values")
    public ResponseEntity<List<ItemValueDTO>> getAllItemValues(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of ItemValues");
        Page<ItemValueDTO> page;
        if (eagerload) {
            page = itemValueService.findAllWithEagerRelationships(pageable);
        } else {
            page = itemValueService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-values/:id} : get the "id" itemValue.
     *
     * @param id the id of the itemValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-values/{id}")
    public ResponseEntity<ItemValueDTO> getItemValue(@PathVariable Long id) {
        log.debug("REST request to get ItemValue : {}", id);
        Optional<ItemValueDTO> itemValueDTO = itemValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemValueDTO);
    }

    /**
     * {@code DELETE  /item-values/:id} : delete the "id" itemValue.
     *
     * @param id the id of the itemValueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-values/{id}")
    public ResponseEntity<Void> deleteItemValue(@PathVariable Long id) {
        log.debug("REST request to delete ItemValue : {}", id);
        itemValueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /item-values/:id} : get the "id" itemValue.
     *
     * @param id the id of the itemValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-values/item/{id}")
    public ResponseEntity<ItemValueDTO> getItem(@PathVariable Long id) {
        log.debug("REST request to get ItemValue : {}", id);
        Optional<ItemValueDTO> itemValueDTO = itemValueService.findItem(id);
        return ResponseUtil.wrapOrNotFound(itemValueDTO);
    }
}
