package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.ItemValueDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.ItemValue}.
 */
public interface ItemValueService {
    /**
     * Save a itemValue.
     *
     * @param itemValueDTO the entity to save.
     * @return the persisted entity.
     */
    ItemValueDTO save(ItemValueDTO itemValueDTO);

    /**
     * Updates a itemValue.
     *
     * @param itemValueDTO the entity to update.
     * @return the persisted entity.
     */
    ItemValueDTO update(ItemValueDTO itemValueDTO);

    /**
     * Partially updates a itemValue.
     *
     * @param itemValueDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ItemValueDTO> partialUpdate(ItemValueDTO itemValueDTO);

    /**
     * Get all the itemValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ItemValueDTO> findAll(Pageable pageable);

    /**
     * Get all the itemValues with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ItemValueDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" itemValue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ItemValueDTO> findOne(Long id);

    /**
     * Delete the "id" itemValue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
