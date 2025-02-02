package com.awtar.myapp.repository;

import com.awtar.myapp.domain.Beneficiary;
import com.awtar.myapp.domain.DonationDetails;
import com.awtar.myapp.domain.Family;
import com.awtar.myapp.service.dto.DonationDetailsDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DonationDetails entity.
 */
@Repository
public interface DonationDetailsRepository extends JpaRepository<DonationDetails, Long> {
    default Optional<DonationDetails> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DonationDetails> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DonationDetails> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct donationDetails from DonationDetails donationDetails left join fetch donationDetails.nature left join fetch donationDetails.beneficiary",
        countQuery = "select count(distinct donationDetails) from DonationDetails donationDetails"
    )
    Page<DonationDetails> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct donationDetails from DonationDetails donationDetails left join fetch donationDetails.nature left join fetch donationDetails.beneficiary"
    )
    List<DonationDetails> findAllWithToOneRelationships();

    @Query(
        "select donationDetails from DonationDetails donationDetails left join fetch donationDetails.nature left join fetch donationDetails.beneficiary where donationDetails.id =:id"
    )
    Optional<DonationDetails> findOneWithToOneRelationships(@Param("id") Long id);

    /*  @Query(
        "select new com.awtar.myapp.service.dto.DonationDetailsDTO (di.id, di.model, di.isValidated, di.donationsDate, COUNT(did.id) AS itemsNumber, (select SUM(iv.price * did.quantity) AS totalPrice from DonationsIssued di, DonationDetails dd, DonationItemDetails did, ItemValue iv where (di.id = dd.donationsIssued.id and did.donationDetails.id = dd.id  and did.item.id = iv.item.id)))" +
        "from DonationItemDetails did, DonationsIssued di, DonationDetails dd where (did.donationDetails.id = dd.id and dd.donationsIssued.id = di.id and dd.beneficiary = :beneficiary) GROUP  BY di.id "
    )
    List<DonationDetailsDTO> findAllDonationsList(@Param("beneficiary") Beneficiary beneficiary);*/

    @Query(
        "select new com.awtar.myapp.service.dto.DonationDetailsDTO (di.id, di.model, di.isValidated, di.donationsDate, SUM(did.quantity) AS itemQuantities)" +
        "from DonationItemDetails did, DonationsIssued di, DonationDetails dd where (did.donationDetails.id = dd.id and dd.donationsIssued.id = di.id and dd.beneficiary = :beneficiary) GROUP  BY di.id "
    )
    List<DonationDetailsDTO> findAllDonationsList(@Param("beneficiary") Beneficiary beneficiary);

    @Query(
        "select new com.awtar.myapp.service.dto.DonationDetailsDTO (d.id, d.description, d.nature.name, f.familyName, f.id) from DonationDetails d, Family f where d.donationsIssued.id = :id and (d.archivated =false or d.archivated =null) and f.id = d.beneficiary.id and (f.archivated =false or f.archivated =null)"
    )
    List<DonationDetailsDTO> findAllDonationDetailsFamilies(@Param("id") Long id);

    @Query(
        "select new com.awtar.myapp.service.dto.DonationDetailsDTO (d.id AS detailsId, d.description, d.nature.name, e.name, e.id) from DonationDetails d, Establishment e where d.donationsIssued.id = :id and (d.archivated =false or d.archivated =null) and e.id = d.beneficiary.id and (e.archivated =false or e.archivated =null)"
    )
    List<DonationDetailsDTO> findAllDonationDetailsEstablishments(@Param("id") Long id);

    /* 
    @Query(
        "select new com.awtar.myapp.service.dto.DonationDetailsDTO (d.id AS detailsId, d.description, d.nature.name, p.firstName, p.lastName, p.urlPhoto, p.urlPhotoContentType, c.id As childId) from DonationDetails d, Profile p, Child c where d.donationsIssued.id = :id and (d.archivated =false or d.archivated =null) and c.id = d.beneficiary.id and p.child.id = c.id and  (p.archivated =false or p.archivated =null)"
    )
    List<DonationDetailsDTO> findAllDonationDetailsChildren(@Param("id") Long id);

}*/

    @Query(
        "select new com.awtar.myapp.service.dto.DonationDetailsDTO (d.id AS detailsId, d.description, d.nature.name, p.firstName, p.lastName, p.urlPhoto, p.urlPhotoContentType, c.id) from DonationDetails d, Profile p, Child c where d.donationsIssued.id = :id and (d.archivated =false or d.archivated =null) and c.id = d.beneficiary.id and p.child.id = c.id and  (p.archivated =false or p.archivated =null)"
    )
    List<DonationDetailsDTO> findAllDonationDetailsChildren(@Param("id") Long id);
}
