package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.ProfileDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.Profile}.
 */
public interface ProfileService {
    /**
     * Save a profile.
     *
     * @param profileDTO the entity to save.
     * @return the persisted entity.
     */
    ProfileDTO save(ProfileDTO profileDTO);

    /**
     * Updates a profile.
     *
     * @param profileDTO the entity to update.
     * @return the persisted entity.
     */
    ProfileDTO update(ProfileDTO profileDTO);

    /**
     * Partially updates a profile.
     *
     * @param profileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProfileDTO> partialUpdate(ProfileDTO profileDTO);

    /**
     * Get all the profiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProfileDTO> findAll(Pageable pageable);

    /**
     * Get all the profiles with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProfileDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" profile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProfileDTO> findOne(Long id);

    /**
     * Delete the "id" profile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get the "id" profile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    List<ProfileDTO> findFamilyParents(Long id);

    /**
     * Get the "id" profile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    List<ProfileDTO> findFamilyChildren(Long id);

    /**
     *
     *
     *
     * @return the entity.
     */
    List<ProfileDTO> findProfileChildren();

    /**
     *
     *
     *
     * @return the entity.
     */
    List<ProfileDTO> findTutorProfile();
    /**
     *
     *
     *
     * @return the entity.
     */
    List<ProfileDTO> findAuthorizingOfficerProfile();

    /**
     * Get the "id" profile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProfileDTO> findProfileX(Long id);
    /**
     * Get the "id" profile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    List<ProfileDTO> findOtherAuthorizingOfficers(Long id);
    /**
     * Get the "id" profile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    List<ProfileDTO> findOthersTutors(Long id);

    /**
     * Get the "id" profile.
     *
     *
     * @return the entity.
     */
    List<ProfileDTO> findallchildren();

    /**
     * Get the "id" profile.
     *
     *
     * @return the entity.
     */
    List<ProfileDTO> findAllAuthorizingOfficers();

    /**
     * Get the "id" profile.
     *
     *
     * @return the entity.
     */
    List<ProfileDTO> findAllTutors();
}
