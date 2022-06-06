package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.DonationsReceivedDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.DonationsReceived}.
 */
public interface DonationsReceivedService {
    /**
     * Save a donationsReceived.
     *
     * @param donationsReceivedDTO the entity to save.
     * @return the persisted entity.
     */
    DonationsReceivedDTO save(DonationsReceivedDTO donationsReceivedDTO);

    /**
     * Updates a donationsReceived.
     *
     * @param donationsReceivedDTO the entity to update.
     * @return the persisted entity.
     */
    DonationsReceivedDTO update(DonationsReceivedDTO donationsReceivedDTO);

    /**
     * Partially updates a donationsReceived.
     *
     * @param donationsReceivedDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DonationsReceivedDTO> partialUpdate(DonationsReceivedDTO donationsReceivedDTO);

    /**
     * Get all the donationsReceiveds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DonationsReceivedDTO> findAll(Pageable pageable);

    /**
     * Get all the donationsReceiveds with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DonationsReceivedDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" donationsReceived.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DonationsReceivedDTO> findOne(Long id);

    /**
     * Delete the "id" donationsReceived.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
