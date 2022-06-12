package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.FamilyDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.Family}.
 */
public interface FamilyService {
    /**
     * Save a family.
     *
     * @param familyDTO the entity to save.
     * @return the persisted entity.
     */
    FamilyDTO save(FamilyDTO familyDTO);

    /**
     * Updates a family.
     *
     * @param familyDTO the entity to update.
     * @return the persisted entity.
     */
    FamilyDTO update(FamilyDTO familyDTO);

    /**
     * Partially updates a family.
     *
     * @param familyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FamilyDTO> partialUpdate(FamilyDTO familyDTO);

    /**
     * Get all the families.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FamilyDTO> findAll(Pageable pageable);
    /**
     * Get all the FamilyDTO where Head is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<FamilyDTO> findAllWhereHeadIsNull();

    /**
     * Get the "id" family.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FamilyDTO> findOne(Long id);

    /**
     * Delete the "id" family.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Save a family.
     *
     *
     * @param familyDTO the entity to save.
     * @return the persisted entity.
     */
    FamilyDTO saveCompletedFamily(FamilyDTO familyDTO);

    /**
     *
     *
     * @return the {@link List} of entities.
     */
    List<FamilyDTO> findFamilys();
}
