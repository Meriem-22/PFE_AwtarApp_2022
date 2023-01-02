package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.DonationItemDetailsDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.DonationItemDetails}.
 */
public interface DonationItemDetailsService {
    /**
     * Save a donationItemDetails.
     *
     * @param donationItemDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    DonationItemDetailsDTO save(DonationItemDetailsDTO donationItemDetailsDTO);

    /**
     * Updates a donationItemDetails.
     *
     * @param donationItemDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    DonationItemDetailsDTO update(DonationItemDetailsDTO donationItemDetailsDTO);

    /**
     * Partially updates a donationItemDetails.
     *
     * @param donationItemDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DonationItemDetailsDTO> partialUpdate(DonationItemDetailsDTO donationItemDetailsDTO);

    /**
     * Get all the donationItemDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DonationItemDetailsDTO> findAll(Pageable pageable);

    /**
     * Get all the donationItemDetails with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DonationItemDetailsDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" donationItemDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DonationItemDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" donationItemDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get the "id" donationItemDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    List<DonationItemDetailsDTO> findAllOfOneDonation(Long id);

    /**
     * Get the "id" donationItemDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    List<DonationItemDetailsDTO> getAllDonationItemDetailsOfFamilies(Long id);

    /**
     * Get the "id" donationItemDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    List<DonationItemDetailsDTO> getAllDonationItemDetailsOfChildren(Long id);

    /**
     * Get the "id" donationItemDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    List<DonationItemDetailsDTO> getAllDonationItemDetailsOfEstablishments(Long id);
}
