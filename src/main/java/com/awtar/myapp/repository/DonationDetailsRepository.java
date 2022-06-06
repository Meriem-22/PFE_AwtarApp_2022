package com.awtar.myapp.repository;

import com.awtar.myapp.domain.DonationDetails;
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
}
