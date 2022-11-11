package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.AuthorizingOfficer}.
 */
public interface AuthorizingOfficerService {
    /**
     * Save a authorizingOfficer.
     *
     * @param authorizingOfficerDTO the entity to save.
     * @return the persisted entity.
     */
    AuthorizingOfficerDTO save(AuthorizingOfficerDTO authorizingOfficerDTO);

    /**
     * Updates a authorizingOfficer.
     *
     * @param authorizingOfficerDTO the entity to update.
     * @return the persisted entity.
     */
    AuthorizingOfficerDTO update(AuthorizingOfficerDTO authorizingOfficerDTO);

    /**
     * Partially updates a authorizingOfficer.
     *
     * @param authorizingOfficerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AuthorizingOfficerDTO> partialUpdate(AuthorizingOfficerDTO authorizingOfficerDTO);

    /**
     * Get all the authorizingOfficers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AuthorizingOfficerDTO> findAll(Pageable pageable);
    /**
     * Get all the AuthorizingOfficerDTO where AuthorizingOfficerProfile is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<AuthorizingOfficerDTO> findAllWhereAuthorizingOfficerProfileIsNull();

    /**
     * Get the "id" authorizingOfficer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AuthorizingOfficerDTO> findOne(Long id);

    /**
     * Delete the "id" authorizingOfficer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * add a authorizingOfficer with profile info.
     *
     * @param authorizingOfficerDTO the entity to save.
     * @return the persisted entity.
     */
    AuthorizingOfficerDTO add(AuthorizingOfficerDTO authorizingOfficerDTO);

    /**
     * Get  all the authorizing Officers details.
     *
     *
     * @return the entity.
     */
    List<AuthorizingOfficerDTO> findAuthorizingOfficersDetails();
}
