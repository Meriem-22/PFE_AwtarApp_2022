package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.DonationsReceivedItemDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.DonationsReceivedItem}.
 */
public interface DonationsReceivedItemService {
    /**
     * Save a donationsReceivedItem.
     *
     * @param donationsReceivedItemDTO the entity to save.
     * @return the persisted entity.
     */
    DonationsReceivedItemDTO save(DonationsReceivedItemDTO donationsReceivedItemDTO);

    /**
     * Updates a donationsReceivedItem.
     *
     * @param donationsReceivedItemDTO the entity to update.
     * @return the persisted entity.
     */
    DonationsReceivedItemDTO update(DonationsReceivedItemDTO donationsReceivedItemDTO);

    /**
     * Partially updates a donationsReceivedItem.
     *
     * @param donationsReceivedItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DonationsReceivedItemDTO> partialUpdate(DonationsReceivedItemDTO donationsReceivedItemDTO);

    /**
     * Get all the donationsReceivedItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DonationsReceivedItemDTO> findAll(Pageable pageable);

    /**
     * Get all the donationsReceivedItems with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DonationsReceivedItemDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" donationsReceivedItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DonationsReceivedItemDTO> findOne(Long id);

    /**
     * Delete the "id" donationsReceivedItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
