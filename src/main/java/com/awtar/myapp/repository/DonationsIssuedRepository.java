package com.awtar.myapp.repository;

import com.awtar.myapp.domain.DonationsIssued;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DonationsIssued entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonationsIssuedRepository extends JpaRepository<DonationsIssued, Long> {}
