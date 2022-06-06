package com.awtar.myapp.repository;

import com.awtar.myapp.domain.StatusOfHealth;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StatusOfHealth entity.
 */
@Repository
public interface StatusOfHealthRepository extends JpaRepository<StatusOfHealth, Long> {
    default Optional<StatusOfHealth> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<StatusOfHealth> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<StatusOfHealth> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct statusOfHealth from StatusOfHealth statusOfHealth left join fetch statusOfHealth.healthStatusCategory",
        countQuery = "select count(distinct statusOfHealth) from StatusOfHealth statusOfHealth"
    )
    Page<StatusOfHealth> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct statusOfHealth from StatusOfHealth statusOfHealth left join fetch statusOfHealth.healthStatusCategory")
    List<StatusOfHealth> findAllWithToOneRelationships();

    @Query(
        "select statusOfHealth from StatusOfHealth statusOfHealth left join fetch statusOfHealth.healthStatusCategory where statusOfHealth.id =:id"
    )
    Optional<StatusOfHealth> findOneWithToOneRelationships(@Param("id") Long id);
}
