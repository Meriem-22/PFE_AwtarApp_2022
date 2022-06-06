package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.ParentDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.Parent}.
 */
public interface ParentService {
    /**
     * Save a parent.
     *
     * @param parentDTO the entity to save.
     * @return the persisted entity.
     */
    ParentDTO save(ParentDTO parentDTO);

    /**
     * Updates a parent.
     *
     * @param parentDTO the entity to update.
     * @return the persisted entity.
     */
    ParentDTO update(ParentDTO parentDTO);

    /**
     * Partially updates a parent.
     *
     * @param parentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ParentDTO> partialUpdate(ParentDTO parentDTO);

    /**
     * Get all the parents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ParentDTO> findAll(Pageable pageable);
    /**
     * Get all the ParentDTO where ParentProfile is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ParentDTO> findAllWhereParentProfileIsNull();

    /**
     * Get the "id" parent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParentDTO> findOne(Long id);

    /**
     * Delete the "id" parent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
