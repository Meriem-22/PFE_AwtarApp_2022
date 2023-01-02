package com.awtar.myapp.repository;

import com.awtar.myapp.domain.DonationsReceived;
import com.awtar.myapp.service.dto.DonationsReceivedDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DonationsReceived entity.
 */
@Repository
public interface DonationsReceivedRepository extends JpaRepository<DonationsReceived, Long> {
    default Optional<DonationsReceived> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DonationsReceived> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DonationsReceived> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct donationsReceived from DonationsReceived donationsReceived left join fetch donationsReceived.authorizingOfficer",
        countQuery = "select count(distinct donationsReceived) from DonationsReceived donationsReceived"
    )
    Page<DonationsReceived> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct donationsReceived from DonationsReceived donationsReceived left join fetch donationsReceived.authorizingOfficer"
    )
    List<DonationsReceived> findAllWithToOneRelationships();

    @Query(
        "select donationsReceived from DonationsReceived donationsReceived left join fetch donationsReceived.authorizingOfficer where donationsReceived.id =:id"
    )
    Optional<DonationsReceived> findOneWithToOneRelationships(@Param("id") Long id);

    @Query(
        "select distinct new com.awtar.myapp.service.dto.DonationsReceivedDTO(d.id, d.urlPhoto, d.urlPhotoContentType, p.firstName, p.lastName, di.date) from DonationsReceived d, DonationsReceivedItem di , Profile p where d.authorizingOfficer.id = p.authorizingOfficer.id and d.id = di.donationsReceived.id and d.archivated = false order by d.id desc"
    )
    List<DonationsReceivedDTO> findRecentDonationsReceived();

    @Query(
        "select distinct new com.awtar.myapp.service.dto.DonationsReceivedDTO(d.id) from DonationsReceived d , DonationsReceivedItem di where d.id = di.donationsReceived.id and d.archivated != true and (di.date > :beginingDate or di.date = :beginingDate) and (di.date < :EndDate or di.date = :EndDate)"
    )
    List<DonationsReceivedDTO> findCurrentYearDonationsReceived(
        @Param("beginingDate") LocalDate beginingDate,
        @Param("EndDate") LocalDate EndDate
    );

    @Query(
        "select new com.awtar.myapp.service.dto.DonationsReceivedDTO (to_char(di.date, 'Month YYYY') AS month , count(DISTINCT d.id) AS number) FROM DonationsReceived d, DonationsReceivedItem di  WHERE (date_part('year',di.date) = 2022 and (d.archivated = null or d.archivated = false) and (di.archivated = null or di.archivated = false) and d.id = di.donationsReceived.id)  GROUP BY date_part('month', date), to_char(di.date, 'Month YYYY') ORDER BY date_part('month', di.date)"
    )
    List<DonationsReceivedDTO> findCurrentYearDonationsReceivedByMonth();
}
