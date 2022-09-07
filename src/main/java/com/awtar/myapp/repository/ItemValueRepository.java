package com.awtar.myapp.repository;

import com.awtar.myapp.domain.ItemValue;
import com.awtar.myapp.service.dto.ItemValueDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ItemValue entity.
 */
@Repository
public interface ItemValueRepository extends JpaRepository<ItemValue, Long> {
    default Optional<ItemValue> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ItemValue> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ItemValue> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct itemValue from ItemValue itemValue left join fetch itemValue.item",
        countQuery = "select count(distinct itemValue) from ItemValue itemValue"
    )
    Page<ItemValue> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct itemValue from ItemValue itemValue left join fetch itemValue.item")
    List<ItemValue> findAllWithToOneRelationships();

    @Query("select itemValue from ItemValue itemValue left join fetch itemValue.item where itemValue.id =:id")
    Optional<ItemValue> findOneWithToOneRelationships(@Param("id") Long id);

    @Query("select itemValue from ItemValue itemValue left join fetch itemValue.item where itemValue.item.id =:id")
    Optional<ItemValue> findwithItem(@Param("id") Long id);
}
