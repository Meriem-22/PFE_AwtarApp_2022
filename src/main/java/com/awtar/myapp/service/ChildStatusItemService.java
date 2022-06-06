package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.ChildStatusItemDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.ChildStatusItem}.
 */
public interface ChildStatusItemService {
    /**
     * Save a childStatusItem.
     *
     * @param childStatusItemDTO the entity to save.
     * @return the persisted entity.
     */
    ChildStatusItemDTO save(ChildStatusItemDTO childStatusItemDTO);

    /**
     * Updates a childStatusItem.
     *
     * @param childStatusItemDTO the entity to update.
     * @return the persisted entity.
     */
    ChildStatusItemDTO update(ChildStatusItemDTO childStatusItemDTO);

    /**
     * Partially updates a childStatusItem.
     *
     * @param childStatusItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ChildStatusItemDTO> partialUpdate(ChildStatusItemDTO childStatusItemDTO);

    /**
     * Get all the childStatusItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChildStatusItemDTO> findAll(Pageable pageable);

    /**
     * Get all the childStatusItems with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChildStatusItemDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" childStatusItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChildStatusItemDTO> findOne(Long id);

    /**
     * Delete the "id" childStatusItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
