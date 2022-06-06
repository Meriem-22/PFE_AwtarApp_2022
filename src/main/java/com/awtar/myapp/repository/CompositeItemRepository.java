package com.awtar.myapp.repository;

import com.awtar.myapp.domain.CompositeItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CompositeItem entity.
 */
@Repository
public interface CompositeItemRepository extends JpaRepository<CompositeItem, Long> {
    default Optional<CompositeItem> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CompositeItem> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CompositeItem> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct compositeItem from CompositeItem compositeItem left join fetch compositeItem.composant left join fetch compositeItem.composer",
        countQuery = "select count(distinct compositeItem) from CompositeItem compositeItem"
    )
    Page<CompositeItem> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct compositeItem from CompositeItem compositeItem left join fetch compositeItem.composant left join fetch compositeItem.composer"
    )
    List<CompositeItem> findAllWithToOneRelationships();

    @Query(
        "select compositeItem from CompositeItem compositeItem left join fetch compositeItem.composant left join fetch compositeItem.composer where compositeItem.id =:id"
    )
    Optional<CompositeItem> findOneWithToOneRelationships(@Param("id") Long id);
}
