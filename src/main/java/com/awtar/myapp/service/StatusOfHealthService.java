package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.StatusOfHealthDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.StatusOfHealth}.
 */
public interface StatusOfHealthService {
    /**
     * Save a statusOfHealth.
     *
     * @param statusOfHealthDTO the entity to save.
     * @return the persisted entity.
     */
    StatusOfHealthDTO save(StatusOfHealthDTO statusOfHealthDTO);

    /**
     * Updates a statusOfHealth.
     *
     * @param statusOfHealthDTO the entity to update.
     * @return the persisted entity.
     */
    StatusOfHealthDTO update(StatusOfHealthDTO statusOfHealthDTO);

    /**
     * Partially updates a statusOfHealth.
     *
     * @param statusOfHealthDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StatusOfHealthDTO> partialUpdate(StatusOfHealthDTO statusOfHealthDTO);

    /**
     * Get all the statusOfHealths.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StatusOfHealthDTO> findAll(Pageable pageable);

    /**
     * Get all the statusOfHealths with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StatusOfHealthDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" statusOfHealth.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StatusOfHealthDTO> findOne(Long id);

    /**
     * Delete the "id" statusOfHealth.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
