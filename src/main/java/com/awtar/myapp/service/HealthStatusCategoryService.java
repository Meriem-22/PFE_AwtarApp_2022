package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.HealthStatusCategoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.HealthStatusCategory}.
 */
public interface HealthStatusCategoryService {
    /**
     * Save a healthStatusCategory.
     *
     * @param healthStatusCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    HealthStatusCategoryDTO save(HealthStatusCategoryDTO healthStatusCategoryDTO);

    /**
     * Updates a healthStatusCategory.
     *
     * @param healthStatusCategoryDTO the entity to update.
     * @return the persisted entity.
     */
    HealthStatusCategoryDTO update(HealthStatusCategoryDTO healthStatusCategoryDTO);

    /**
     * Partially updates a healthStatusCategory.
     *
     * @param healthStatusCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HealthStatusCategoryDTO> partialUpdate(HealthStatusCategoryDTO healthStatusCategoryDTO);

    /**
     * Get all the healthStatusCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HealthStatusCategoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" healthStatusCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HealthStatusCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" healthStatusCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
