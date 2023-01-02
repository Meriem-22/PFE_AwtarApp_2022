package com.awtar.myapp.repository;

import com.awtar.myapp.domain.DonationsReceivedItem;
import com.awtar.myapp.service.dto.DonationsReceivedDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DonationsReceivedItem entity.
 */
@Repository
public interface DonationsReceivedItemRepository extends JpaRepository<DonationsReceivedItem, Long> {
    default Optional<DonationsReceivedItem> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DonationsReceivedItem> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DonationsReceivedItem> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct donationsReceivedItem from DonationsReceivedItem donationsReceivedItem left join fetch donationsReceivedItem.item",
        countQuery = "select count(distinct donationsReceivedItem) from DonationsReceivedItem donationsReceivedItem"
    )
    Page<DonationsReceivedItem> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct donationsReceivedItem from DonationsReceivedItem donationsReceivedItem left join fetch donationsReceivedItem.item"
    )
    List<DonationsReceivedItem> findAllWithToOneRelationships();

    @Query(
        "select donationsReceivedItem from DonationsReceivedItem donationsReceivedItem left join fetch donationsReceivedItem.item where donationsReceivedItem.id =:id"
    )
    Optional<DonationsReceivedItem> findOneWithToOneRelationships(@Param("id") Long id);
}
