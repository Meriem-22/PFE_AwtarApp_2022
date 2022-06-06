package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.SchoolLevelDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.SchoolLevel}.
 */
public interface SchoolLevelService {
    /**
     * Save a schoolLevel.
     *
     * @param schoolLevelDTO the entity to save.
     * @return the persisted entity.
     */
    SchoolLevelDTO save(SchoolLevelDTO schoolLevelDTO);

    /**
     * Updates a schoolLevel.
     *
     * @param schoolLevelDTO the entity to update.
     * @return the persisted entity.
     */
    SchoolLevelDTO update(SchoolLevelDTO schoolLevelDTO);

    /**
     * Partially updates a schoolLevel.
     *
     * @param schoolLevelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SchoolLevelDTO> partialUpdate(SchoolLevelDTO schoolLevelDTO);

    /**
     * Get all the schoolLevels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SchoolLevelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" schoolLevel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SchoolLevelDTO> findOne(Long id);

    /**
     * Delete the "id" schoolLevel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
