package com.awtar.myapp.repository;

import com.awtar.myapp.domain.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Item entity.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    default Optional<Item> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Item> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Item> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct item from Item item left join fetch item.nature",
        countQuery = "select count(distinct item) from Item item"
    )
    Page<Item> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct item from Item item left join fetch item.nature")
    List<Item> findAllWithToOneRelationships();

    @Query("select item from Item item left join fetch item.nature where item.id =:id")
    Optional<Item> findOneWithToOneRelationships(@Param("id") Long id);

    @Query("select item from Item item left join fetch item.nature where item.nature.id =:id")
    List<Item> findItemsWithNature(@Param("id") Long id);

    @Query("select item from Item item left join fetch item.nature where item.archivated != true")
    List<Item> findAllItems();
}
