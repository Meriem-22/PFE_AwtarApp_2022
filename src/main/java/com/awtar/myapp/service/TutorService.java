package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.TutorDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.Tutor}.
 */
public interface TutorService {
    /**
     * Save a tutor.
     *
     * @param tutorDTO the entity to save.
     * @return the persisted entity.
     */
    TutorDTO save(TutorDTO tutorDTO);

    /**
     * Updates a tutor.
     *
     * @param tutorDTO the entity to update.
     * @return the persisted entity.
     */
    TutorDTO update(TutorDTO tutorDTO);

    /**
     * Partially updates a tutor.
     *
     * @param tutorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TutorDTO> partialUpdate(TutorDTO tutorDTO);

    /**
     * Get all the tutors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TutorDTO> findAll(Pageable pageable);
    /**
     * Get all the TutorDTO where TutorProfile is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<TutorDTO> findAllWhereTutorProfileIsNull();

    /**
     * Get the "id" tutor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TutorDTO> findOne(Long id);

    /**
     * Delete the "id" tutor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Save a tutor with profile info.
     *
     * @param tutorDTO the entity to save.
     * @return the persisted entity.
     */
    TutorDTO add(TutorDTO tutorDTO);
}
