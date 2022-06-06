package com.awtar.myapp.repository;

import com.awtar.myapp.domain.EducationalInstitution;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EducationalInstitution entity.
 */
@Repository
public interface EducationalInstitutionRepository extends JpaRepository<EducationalInstitution, Long> {
    default Optional<EducationalInstitution> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EducationalInstitution> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<EducationalInstitution> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct educationalInstitution from EducationalInstitution educationalInstitution left join fetch educationalInstitution.city",
        countQuery = "select count(distinct educationalInstitution) from EducationalInstitution educationalInstitution"
    )
    Page<EducationalInstitution> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct educationalInstitution from EducationalInstitution educationalInstitution left join fetch educationalInstitution.city"
    )
    List<EducationalInstitution> findAllWithToOneRelationships();

    @Query(
        "select educationalInstitution from EducationalInstitution educationalInstitution left join fetch educationalInstitution.city where educationalInstitution.id =:id"
    )
    Optional<EducationalInstitution> findOneWithToOneRelationships(@Param("id") Long id);
}
