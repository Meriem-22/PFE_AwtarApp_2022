package com.awtar.myapp.repository;

import com.awtar.myapp.domain.SchoolLevelItem;
import com.awtar.myapp.service.dto.SchoolLevelItemDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SchoolLevelItem entity.
 */
@Repository
public interface SchoolLevelItemRepository extends JpaRepository<SchoolLevelItem, Long> {
    default Optional<SchoolLevelItem> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SchoolLevelItem> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SchoolLevelItem> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct schoolLevelItem from SchoolLevelItem schoolLevelItem left join fetch schoolLevelItem.item left join fetch schoolLevelItem.schoolLevel",
        countQuery = "select count(distinct schoolLevelItem) from SchoolLevelItem schoolLevelItem"
    )
    Page<SchoolLevelItem> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct schoolLevelItem from SchoolLevelItem schoolLevelItem left join fetch schoolLevelItem.item left join fetch schoolLevelItem.schoolLevel"
    )
    List<SchoolLevelItem> findAllWithToOneRelationships();

    @Query(
        "select schoolLevelItem from SchoolLevelItem schoolLevelItem left join fetch schoolLevelItem.item left join fetch schoolLevelItem.schoolLevel where schoolLevelItem.id =:id"
    )
    Optional<SchoolLevelItem> findOneWithToOneRelationships(@Param("id") Long id);

    @Query(
        "select new com.awtar.myapp.service.dto.SchoolLevelItemDTO (sli.schoolLevel.schoolLevel, sli.quantityNeeded) from SchoolLevelItem sli where sli.item.id = :id"
    )
    List<SchoolLevelItemDTO> findSchoolLevelItemDetails(@Param("id") Long id);
}
