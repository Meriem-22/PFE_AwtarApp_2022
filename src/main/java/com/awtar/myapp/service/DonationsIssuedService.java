package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.DonationsIssuedDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.DonationsIssued}.
 */
public interface DonationsIssuedService {
    /**
     * Save a donationsIssued.
     *
     * @param donationsIssuedDTO the entity to save.
     * @return the persisted entity.
     */
    DonationsIssuedDTO save(DonationsIssuedDTO donationsIssuedDTO);

    /**
     * Updates a donationsIssued.
     *
     * @param donationsIssuedDTO the entity to update.
     * @return the persisted entity.
     */
    DonationsIssuedDTO update(DonationsIssuedDTO donationsIssuedDTO);

    /**
     * Partially updates a donationsIssued.
     *
     * @param donationsIssuedDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DonationsIssuedDTO> partialUpdate(DonationsIssuedDTO donationsIssuedDTO);

    /**
     * Get all the donationsIssueds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DonationsIssuedDTO> findAll(Pageable pageable);

    /**
     * Get the "id" donationsIssued.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DonationsIssuedDTO> findOne(Long id);

    /**
     * Delete the "id" donationsIssued.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Save a donationsIssued.
     *
     * @param donationsIssuedDTO the entity to save.
     * @return the persisted entity.
     */
    DonationsIssuedDTO saveCompletedDonationsIssued(DonationsIssuedDTO donationsIssuedDTO);

    /**
     *
     *
     *
     * @return the persisted entity.
     */
    List<DonationsIssuedDTO> getLastValidatedDonations();
}
