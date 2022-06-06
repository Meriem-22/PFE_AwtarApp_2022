package com.awtar.myapp.repository;

import com.awtar.myapp.domain.Establishment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Establishment entity.
 */
@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {
    default Optional<Establishment> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Establishment> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Establishment> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct establishment from Establishment establishment left join fetch establishment.establishmentType left join fetch establishment.city",
        countQuery = "select count(distinct establishment) from Establishment establishment"
    )
    Page<Establishment> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct establishment from Establishment establishment left join fetch establishment.establishmentType left join fetch establishment.city"
    )
    List<Establishment> findAllWithToOneRelationships();

    @Query(
        "select establishment from Establishment establishment left join fetch establishment.establishmentType left join fetch establishment.city where establishment.id =:id"
    )
    Optional<Establishment> findOneWithToOneRelationships(@Param("id") Long id);
}
