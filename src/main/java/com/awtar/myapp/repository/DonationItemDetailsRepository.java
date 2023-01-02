package com.awtar.myapp.repository;

import com.awtar.myapp.domain.DonationItemDetails;
import com.awtar.myapp.domain.DonationsIssued;
import com.awtar.myapp.service.dto.DonationItemDetailsDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DonationItemDetails entity.
 */
@Repository
public interface DonationItemDetailsRepository extends JpaRepository<DonationItemDetails, Long> {
    default Optional<DonationItemDetails> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DonationItemDetails> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DonationItemDetails> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct donationItemDetails from DonationItemDetails donationItemDetails left join fetch donationItemDetails.item",
        countQuery = "select count(distinct donationItemDetails) from DonationItemDetails donationItemDetails"
    )
    Page<DonationItemDetails> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct donationItemDetails from DonationItemDetails donationItemDetails left join fetch donationItemDetails.item")
    List<DonationItemDetails> findAllWithToOneRelationships();

    @Query(
        "select donationItemDetails from DonationItemDetails donationItemDetails left join fetch donationItemDetails.item where donationItemDetails.id =:id"
    )
    Optional<DonationItemDetails> findOneWithToOneRelationships(@Param("id") Long id);

    @Query(
        "select distinct new com.awtar.myapp.service.dto.DonationItemDetailsDTO (did.quantity, i.name, i.urlPhoto, i.urlPhotoContentType, iv.price, iv.availableStockQuantity, dd.id AS detailsId, dd.description)  from DonationItemDetails did, Item i, ItemValue iv, DonationDetails dd  where did.donationDetails.donationsIssued  = :donationsIssued and did.donationDetails.id = dd.id and i.id = did.item.id and i.id= iv.item.id and (did.archivated = null or did.archivated = false)"
    )
    List<DonationItemDetailsDTO> findAllDetailsItemDonations(@Param("donationsIssued") DonationsIssued donationsIssued);

    @Query(
        "select  new com.awtar.myapp.service.dto.DonationItemDetailsDTO (did.quantity, i.name, i.urlPhoto, i.urlPhotoContentType, iv.price, iv.availableStockQuantity, dd.beneficiary.id AS bId, dd.description)  from DonationItemDetails did, Item i, ItemValue iv, DonationDetails dd  where did.donationDetails.donationsIssued  = :donationsIssued and did.donationDetails.id = dd.id and dd.beneficiary.beneficiaryType = 'FAMILY' and i.id = did.item.id and i.id= iv.item.id and (did.archivated = null or did.archivated = false)"
    )
    List<DonationItemDetailsDTO> findAllFamiliesDonationItemsDetails(@Param("donationsIssued") DonationsIssued donationsIssued);

    @Query(
        "select  new com.awtar.myapp.service.dto.DonationItemDetailsDTO (did.quantity, i.name, i.urlPhoto, i.urlPhotoContentType, iv.price, iv.availableStockQuantity, dd.beneficiary.id AS bId, dd.description)  from DonationItemDetails did, Item i, ItemValue iv, DonationDetails dd  where did.donationDetails.donationsIssued  = :donationsIssued and did.donationDetails.id = dd.id and dd.beneficiary.beneficiaryType = 'CHILD' and i.id = did.item.id and i.id= iv.item.id and (did.archivated = null or did.archivated = false)"
    )
    List<DonationItemDetailsDTO> findAllChildrenDonationItemsDetails(@Param("donationsIssued") DonationsIssued donationsIssued);

    @Query(
        "select  new com.awtar.myapp.service.dto.DonationItemDetailsDTO (did.quantity, i.name, i.urlPhoto, i.urlPhotoContentType, iv.price, iv.availableStockQuantity, dd.beneficiary.id AS bId, dd.description)  from DonationItemDetails did, Item i, ItemValue iv, DonationDetails dd  where did.donationDetails.donationsIssued  = :donationsIssued and did.donationDetails.id = dd.id and dd.beneficiary.beneficiaryType = 'ESTABLISHMENT' and i.id = did.item.id and i.id= iv.item.id and (did.archivated = null or did.archivated = false)"
    )
    List<DonationItemDetailsDTO> findAllEstablishmentsDonationItemsDetails(@Param("donationsIssued") DonationsIssued donationsIssued);
}
