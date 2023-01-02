package com.awtar.myapp.repository;

import com.awtar.myapp.domain.DonationsIssued;
import com.awtar.myapp.service.dto.DonationsIssuedDTO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DonationsIssued entity.
 */
@Repository
public interface DonationsIssuedRepository extends JpaRepository<DonationsIssued, Long> {
    @Query("select donationsIssued from DonationsIssued donationsIssued where donationsIssued.isValidated  = true")
    List<DonationsIssued> findAllDetailsItemDonations();

    @Query(
        "select donationsIssued from DonationsIssued donationsIssued where donationsIssued.donationsDate > :date and (donationsIssued.archivated = null or donationsIssued.archivated = false)"
    )
    List<DonationsIssued> Upcomingscheduleddonations(@Param("date") LocalDate date);

    @Query(
        "select donationsIssued from DonationsIssued donationsIssued where donationsIssued.isValidated  = true  and donationsIssued.donationsDate > :date and (donationsIssued.archivated = null or donationsIssued.archivated = false)"
    )
    List<DonationsIssued> UpcomingDonationsValidated(@Param("date") LocalDate date);

    @Query(
        "select new com.awtar.myapp.service.dto.DonationsIssuedDTO  (to_char(d.donationsDate, 'Month YYYY') AS month , count(DISTINCT d.id) AS number) FROM DonationsIssued d WHERE (date_part('year', d.donationsDate) = 2022 and d.isValidated = true and (d.archivated = null or d.archivated = false)) GROUP BY date_part('month', donationsDate), to_char(d.donationsDate, 'Month YYYY') ORDER BY date_part('month', d.donationsDate)"
    )
    List<DonationsIssuedDTO> findIssuedDonationsByMonth();

    @Query(
        "select new com.awtar.myapp.service.dto.DonationsIssuedDTO  (to_char(d.donationsDate, 'Month YYYY') AS month , count(DISTINCT d.id) AS number) FROM DonationsIssued d WHERE (date_part('year', d.donationsDate) = 2022 and ((d.isValidated = false or d.isValidated = null) or d.canceledDonations = true) and (d.archivated = null or d.archivated = false)) GROUP BY date_part('month', donationsDate), to_char(d.donationsDate, 'Month YYYY') ORDER BY date_part('month', d.donationsDate)"
    )
    List<DonationsIssuedDTO> findCanceledDonationsByMonth();

    @Query(
        "select donationsIssued from DonationsIssued donationsIssued where (donationsIssued.archivated  = false or  donationsIssued.archivated = null) "
    )
    List<DonationsIssued> findAllNotArchivated();
}
