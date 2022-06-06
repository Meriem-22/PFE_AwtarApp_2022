package com.awtar.myapp.repository;

import com.awtar.myapp.domain.TeachingCurriculum;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TeachingCurriculum entity.
 */
@Repository
public interface TeachingCurriculumRepository extends JpaRepository<TeachingCurriculum, Long> {
    default Optional<TeachingCurriculum> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TeachingCurriculum> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TeachingCurriculum> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct teachingCurriculum from TeachingCurriculum teachingCurriculum left join fetch teachingCurriculum.schoolLevel left join fetch teachingCurriculum.educationalInstitution",
        countQuery = "select count(distinct teachingCurriculum) from TeachingCurriculum teachingCurriculum"
    )
    Page<TeachingCurriculum> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct teachingCurriculum from TeachingCurriculum teachingCurriculum left join fetch teachingCurriculum.schoolLevel left join fetch teachingCurriculum.educationalInstitution"
    )
    List<TeachingCurriculum> findAllWithToOneRelationships();

    @Query(
        "select teachingCurriculum from TeachingCurriculum teachingCurriculum left join fetch teachingCurriculum.schoolLevel left join fetch teachingCurriculum.educationalInstitution where teachingCurriculum.id =:id"
    )
    Optional<TeachingCurriculum> findOneWithToOneRelationships(@Param("id") Long id);
}
