package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.SchoolLevelItemDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.SchoolLevelItem}.
 */
public interface SchoolLevelItemService {
    /**
     * Save a schoolLevelItem.
     *
     * @param schoolLevelItemDTO the entity to save.
     * @return the persisted entity.
     */
    SchoolLevelItemDTO save(SchoolLevelItemDTO schoolLevelItemDTO);

    /**
     * Updates a schoolLevelItem.
     *
     * @param schoolLevelItemDTO the entity to update.
     * @return the persisted entity.
     */
    SchoolLevelItemDTO update(SchoolLevelItemDTO schoolLevelItemDTO);

    /**
     * Partially updates a schoolLevelItem.
     *
     * @param schoolLevelItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SchoolLevelItemDTO> partialUpdate(SchoolLevelItemDTO schoolLevelItemDTO);

    /**
     * Get all the schoolLevelItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SchoolLevelItemDTO> findAll(Pageable pageable);

    /**
     * Get all the schoolLevelItems with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SchoolLevelItemDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" schoolLevelItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SchoolLevelItemDTO> findOne(Long id);

    /**
     * Delete the "id" schoolLevelItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get the "id" schoolLevelItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    List<SchoolLevelItemDTO> findSchoolLevelItemDetails(Long id);
}
