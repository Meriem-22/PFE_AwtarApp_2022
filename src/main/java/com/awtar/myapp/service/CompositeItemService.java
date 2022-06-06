package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.CompositeItemDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.CompositeItem}.
 */
public interface CompositeItemService {
    /**
     * Save a compositeItem.
     *
     * @param compositeItemDTO the entity to save.
     * @return the persisted entity.
     */
    CompositeItemDTO save(CompositeItemDTO compositeItemDTO);

    /**
     * Updates a compositeItem.
     *
     * @param compositeItemDTO the entity to update.
     * @return the persisted entity.
     */
    CompositeItemDTO update(CompositeItemDTO compositeItemDTO);

    /**
     * Partially updates a compositeItem.
     *
     * @param compositeItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompositeItemDTO> partialUpdate(CompositeItemDTO compositeItemDTO);

    /**
     * Get all the compositeItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompositeItemDTO> findAll(Pageable pageable);

    /**
     * Get all the compositeItems with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompositeItemDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" compositeItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompositeItemDTO> findOne(Long id);

    /**
     * Delete the "id" compositeItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
