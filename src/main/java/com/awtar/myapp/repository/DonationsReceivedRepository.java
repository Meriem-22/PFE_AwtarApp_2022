package com.awtar.myapp.repository;

import com.awtar.myapp.domain.DonationsReceived;
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
}
