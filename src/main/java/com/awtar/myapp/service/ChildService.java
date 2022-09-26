package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.ChildDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.Child}.
 */
public interface ChildService {
    /**
     * Save a child.
     *
     * @param childDTO the entity to save.
     * @return the persisted entity.
     */
    ChildDTO save(ChildDTO childDTO);

    /**
     * Updates a child.
     *
     * @param childDTO the entity to update.
     * @return the persisted entity.
     */
    ChildDTO update(ChildDTO childDTO);

    /**
     * Partially updates a child.
     *
     * @param childDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ChildDTO> partialUpdate(ChildDTO childDTO);

    /**
     * Get all the children.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ChildDTO> findAll(Pageable pageable);
    /**
     * Get all the ChildDTO where ChildProfile is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ChildDTO> findAllWhereChildProfileIsNull();

    /**
     * Get the "id" child.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChildDTO> findOne(Long id);

    /**
     * Delete the "id" child.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Save a child.
     *
     * @param childDTO the entity to save.
     * @return the persisted entity.
     */
    ChildDTO saveChildAllDetails(ChildDTO childDTO);

    /**
     * Get the "id" profile.
     *
     * @param year the entity to save.
     * @return the entity.
     */
    List<ChildDTO> getAllChildrenDetails(String beginningYear);

    /**
     * Get the "id" profile.
     *
     *
     * @return the entity.
     */
    List<ChildDTO> getChildrenDetails();

    /**
     * Get the "id" profile.
     *
     * @param year the entity to save.
     * @return the entity.
     */
    List<ChildDTO> getAllChildrenSchoolDetails(String beginningYear);

    /**
     * Get the "id" profile.
     *
     *
     * @return the entity.
     */
    List<ChildDTO> getChildrenWithoutFamilyDetails();
}
