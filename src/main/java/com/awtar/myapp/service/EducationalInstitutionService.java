package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.EducationalInstitutionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.EducationalInstitution}.
 */
public interface EducationalInstitutionService {
    /**
     * Save a educationalInstitution.
     *
     * @param educationalInstitutionDTO the entity to save.
     * @return the persisted entity.
     */
    EducationalInstitutionDTO save(EducationalInstitutionDTO educationalInstitutionDTO);

    /**
     * Updates a educationalInstitution.
     *
     * @param educationalInstitutionDTO the entity to update.
     * @return the persisted entity.
     */
    EducationalInstitutionDTO update(EducationalInstitutionDTO educationalInstitutionDTO);

    /**
     * Partially updates a educationalInstitution.
     *
     * @param educationalInstitutionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EducationalInstitutionDTO> partialUpdate(EducationalInstitutionDTO educationalInstitutionDTO);

    /**
     * Get all the educationalInstitutions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EducationalInstitutionDTO> findAll(Pageable pageable);

    /**
     * Get all the educationalInstitutions with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EducationalInstitutionDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" educationalInstitution.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EducationalInstitutionDTO> findOne(Long id);

    /**
     * Delete the "id" educationalInstitution.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
