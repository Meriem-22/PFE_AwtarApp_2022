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
        "select donationItemDetails from DonationItemDetails donationItemDetails left join fetch donationItemDetails.item where donationItemDetails.donationDetails.donationsIssued  = :donationsIssued"
    )
    List<DonationItemDetailsDTO> findAllDetailsItemDonations(@Param("donationsIssued") DonationsIssued donationsIssued);
}
