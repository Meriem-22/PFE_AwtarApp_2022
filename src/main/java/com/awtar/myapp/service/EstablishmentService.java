package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.EstablishmentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.Establishment}.
 */
public interface EstablishmentService {
    /**
     * Save a establishment.
     *
     * @param establishmentDTO the entity to save.
     * @return the persisted entity.
     */
    EstablishmentDTO save(EstablishmentDTO establishmentDTO);

    /**
     * Updates a establishment.
     *
     * @param establishmentDTO the entity to update.
     * @return the persisted entity.
     */
    EstablishmentDTO update(EstablishmentDTO establishmentDTO);

    /**
     * Partially updates a establishment.
     *
     * @param establishmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EstablishmentDTO> partialUpdate(EstablishmentDTO establishmentDTO);

    /**
     * Get all the establishments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstablishmentDTO> findAll(Pageable pageable);

    /**
     * Get all the establishments with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstablishmentDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" establishment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstablishmentDTO> findOne(Long id);

    /**
     * Delete the "id" establishment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
