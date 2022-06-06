package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.TeachingCurriculumDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.TeachingCurriculum}.
 */
public interface TeachingCurriculumService {
    /**
     * Save a teachingCurriculum.
     *
     * @param teachingCurriculumDTO the entity to save.
     * @return the persisted entity.
     */
    TeachingCurriculumDTO save(TeachingCurriculumDTO teachingCurriculumDTO);

    /**
     * Updates a teachingCurriculum.
     *
     * @param teachingCurriculumDTO the entity to update.
     * @return the persisted entity.
     */
    TeachingCurriculumDTO update(TeachingCurriculumDTO teachingCurriculumDTO);

    /**
     * Partially updates a teachingCurriculum.
     *
     * @param teachingCurriculumDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TeachingCurriculumDTO> partialUpdate(TeachingCurriculumDTO teachingCurriculumDTO);

    /**
     * Get all the teachingCurricula.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TeachingCurriculumDTO> findAll(Pageable pageable);

    /**
     * Get all the teachingCurricula with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TeachingCurriculumDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" teachingCurriculum.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TeachingCurriculumDTO> findOne(Long id);

    /**
     * Delete the "id" teachingCurriculum.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
