package com.awtar.myapp.service;

import com.awtar.myapp.service.dto.BeneficiaryDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.awtar.myapp.domain.Beneficiary}.
 */
public interface BeneficiaryService {
    /**
     * Save a beneficiary.
     *
     * @param beneficiaryDTO the entity to save.
     * @return the persisted entity.
     */
    BeneficiaryDTO save(BeneficiaryDTO beneficiaryDTO);

    /**
     * Updates a beneficiary.
     *
     * @param beneficiaryDTO the entity to update.
     * @return the persisted entity.
     */
    BeneficiaryDTO update(BeneficiaryDTO beneficiaryDTO);

    /**
     * Partially updates a beneficiary.
     *
     * @param beneficiaryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BeneficiaryDTO> partialUpdate(BeneficiaryDTO beneficiaryDTO);

    /**
     * Get all the beneficiaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BeneficiaryDTO> findAll(Pageable pageable);

    /**
     * Get all the beneficiaries with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BeneficiaryDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" beneficiary.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BeneficiaryDTO> findOne(Long id);

    /**
     * Delete the "id" beneficiary.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Calcul of the  the beneficiary Reference.
     *
     * @param beneficiaryDTO the entity to update.
     * @return the persisted entity.
     */
    BeneficiaryDTO calculReference(BeneficiaryDTO beneficiaryDTO);

    /**
     * Updates a beneficiary.
     *
     * @param beneficiaryDTO the entity to update.
     * @return the persisted entity.
     */
    BeneficiaryDTO updateAuthorizingOfficer(BeneficiaryDTO beneficiaryDTO);

    /**
     * Updates a beneficiary.
     *
     * @param beneficiaryDTO the entity to update.
     * @return the persisted entity.
     */
    BeneficiaryDTO updateTutor(BeneficiaryDTO beneficiaryDTO);

    /**
     * Updates a beneficiary.
     *
     * @param id the id of the contributor..
     * @return the persisted entity.
     */
    List<BeneficiaryDTO> findAllFamiliesContributor(Long id);

    /**
     * Updates a beneficiary.
     *
     * @param id the id of the contributor..
     * @return the persisted entity.
     */
    List<BeneficiaryDTO> findAllChildrenContributor(Long id);

    /**
     * Updates a beneficiary.
     *
     * @param id the id of the contributor..
     * @return the persisted entity.
     */
    List<BeneficiaryDTO> findAllEstablishmentsContributor(Long id);

    /**
     * Updates a beneficiary.
     *
     * @param beneficiaryDTO the entity to update.
     * @return the persisted entity.
     */
    BeneficiaryDTO removeAuthorizingOfficer(BeneficiaryDTO beneficiaryDTO);

    /**
     * Updates a beneficiary.
     *
     * @param beneficiaryDTO the entity to update.
     * @return the persisted entity.
     */
    BeneficiaryDTO removeTutor(BeneficiaryDTO beneficiaryDTO);

    /**
     *
     *
     *
     * @return the persisted entity.
     */
    List<BeneficiaryDTO> TotalBeneficiaries();
}
