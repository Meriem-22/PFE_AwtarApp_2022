package com.awtar.myapp.repository;

import com.awtar.myapp.domain.ChildStatusItem;
import com.awtar.myapp.service.dto.ChildStatusItemDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ChildStatusItem entity.
 */
@Repository
public interface ChildStatusItemRepository extends JpaRepository<ChildStatusItem, Long> {
    default Optional<ChildStatusItem> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ChildStatusItem> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ChildStatusItem> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct childStatusItem from ChildStatusItem childStatusItem left join fetch childStatusItem.item left join fetch childStatusItem.childStatus",
        countQuery = "select count(distinct childStatusItem) from ChildStatusItem childStatusItem"
    )
    Page<ChildStatusItem> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct childStatusItem from ChildStatusItem childStatusItem left join fetch childStatusItem.item left join fetch childStatusItem.childStatus"
    )
    List<ChildStatusItem> findAllWithToOneRelationships();

    @Query(
        "select childStatusItem from ChildStatusItem childStatusItem left join fetch childStatusItem.item left join fetch childStatusItem.childStatus where childStatusItem.id =:id"
    )
    Optional<ChildStatusItem> findOneWithToOneRelationships(@Param("id") Long id);

    @Query(
        "select new com.awtar.myapp.service.dto.ChildStatusItemDTO (cs.id, csi.childStatus.staus) from ChildStatus cs, ChildStatusItem csi where csi.item.id =:id and csi.childStatus.id = cs.id"
    )
    List<ChildStatusItemDTO> findAllChildStatusItem(@Param("id") Long id);
}
