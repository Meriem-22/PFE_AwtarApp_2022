package com.awtar.myapp.repository;

import com.awtar.myapp.domain.DonationsIssued;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DonationsIssued entity.
 */
@Repository
public interface DonationsIssuedRepository extends JpaRepository<DonationsIssued, Long> {
    @Query("select donationsIssued from DonationsIssued donationsIssued where donationsIssued.isValidated  = true")
    List<DonationsIssued> findAllDetailsItemDonations();
}
