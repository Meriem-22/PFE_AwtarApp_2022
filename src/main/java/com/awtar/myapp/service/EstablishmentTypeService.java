package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.EstablishmentTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.EstablishmentType}.
 */
public interface EstablishmentTypeService {
    /**
     * Save a establishmentType.
     *
     * @param establishmentTypeDTO the entity to save.
     * @return the persisted entity.
     */
    EstablishmentTypeDTO save(EstablishmentTypeDTO establishmentTypeDTO);

    /**
     * Updates a establishmentType.
     *
     * @param establishmentTypeDTO the entity to update.
     * @return the persisted entity.
     */
    EstablishmentTypeDTO update(EstablishmentTypeDTO establishmentTypeDTO);

    /**
     * Partially updates a establishmentType.
     *
     * @param establishmentTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EstablishmentTypeDTO> partialUpdate(EstablishmentTypeDTO establishmentTypeDTO);

    /**
     * Get all the establishmentTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstablishmentTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" establishmentType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstablishmentTypeDTO> findOne(Long id);

    /**
     * Delete the "id" establishmentType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
