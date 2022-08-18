package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.DonationDetailsDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.DonationDetails}.
 */
public interface DonationDetailsService {
    /**
     * Save a donationDetails.
     *
     * @param donationDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    DonationDetailsDTO save(DonationDetailsDTO donationDetailsDTO);

    /**
     * Updates a donationDetails.
     *
     * @param donationDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    DonationDetailsDTO update(DonationDetailsDTO donationDetailsDTO);

    /**
     * Partially updates a donationDetails.
     *
     * @param donationDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DonationDetailsDTO> partialUpdate(DonationDetailsDTO donationDetailsDTO);

    /**
     * Get all the donationDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DonationDetailsDTO> findAll(Pageable pageable);

    /**
     * Get all the donationDetails with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DonationDetailsDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" donationDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DonationDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" donationDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get the "id" beneficiary.
     *
     * @param id the id of the entity.
     * @return  the list of entities.
     */
    List<DonationDetailsDTO> findAllBeneficiaryDonationList(Long id);

    /**
     * Get the "id" beneficiary.
     *
     * @param id the id of the entity.
     * @param pageable the pagination information.
     * @return  the list of entities.
     */
    Page<DonationDetailsDTO> findAllBeneficiaryDonation(Long id, Pageable pageable);
}
