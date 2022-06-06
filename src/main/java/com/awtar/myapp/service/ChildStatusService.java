package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.ChildStatusDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.ChildStatus}.
 */
public interface ChildStatusService {
    /**
     * Save a childStatus.
     *
     * @param childStatusDTO the entity to save.
     * @return the persisted entity.
     */
    ChildStatusDTO save(ChildStatusDTO childStatusDTO);

    /**
     * Updates a childStatus.
     *
     * @param childStatusDTO the entity to update.
     * @return the persisted entity.
     */
    ChildStatusDTO update(ChildStatusDTO childStatusDTO);

    /**
     * Partially updates a childStatus.
     *
     * @param childStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ChildStatusDTO> partialUpdate(ChildStatusDTO childStatusDTO);

    /**
     * Get all the childStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChildStatusDTO> findAll(Pageable pageable);

    /**
     * Get the "id" childStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChildStatusDTO> findOne(Long id);

    /**
     * Delete the "id" childStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
