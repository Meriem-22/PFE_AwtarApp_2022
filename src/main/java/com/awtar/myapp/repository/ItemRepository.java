package com.awtar.myapp.repository;

import com.awtar.myapp.domain.Item;
import com.awtar.myapp.service.dto.ItemDTO;
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

    @Query(
        "select new com.awtar.myapp.service.dto.ItemDTO (item.id, item.name, item.urlPhoto, item.urlPhotoContentType, item.gender, item.composed, ip.price, ip.priceDate, ip.availableStockQuantity) from ItemValue ip, Item item where ((item.archivated != true) and (ip.item.id = item.id) )"
    )
    List<ItemDTO> findAllDetailsItems();

    @Query(
        "select new com.awtar.myapp.service.dto.ItemDTO (item.id, item.name, item.urlPhoto,item.urlPhotoContentType, item.gender, item.composed, ip.price, ip.priceDate, ip.availableStockQuantity,si.isSchoolItem,  sl.schoolLevel, si.quantityNeeded) from ItemValue ip, SchoolLevelItem si, SchoolLevel sl, Item item where ((item.archivated != true) and (ip.item.id = item.id) and (si.item.id = item.id) and (si.item.id = ip.item.id) and (sl.schoolLevel.id = si.schoolLevel.id) and (si.isSchoolItem = true))"
    )
    List<ItemDTO> findAllSchoolItemsDetails();

    @Query(
        "select new com.awtar.myapp.service.dto.ItemDTO (item.id, item.name, item.urlPhoto, item.urlPhotoContentType, item.gender, item.composed, ip.id, ip.price, ip.priceDate, ip.availableStockQuantity, ci.quantity) from ItemValue ip, Item item, CompositeItem ci where ((item.archivated != true) and (ip.item.id = item.id) and (ci.composant.id =:id) and (ci.composer.id = item.id))"
    )
    List<ItemDTO> findAllCompositeurDetailItems(@Param("id") Long id);

    /*
    @Query("select new com.awtar.myapp.service.dto.ItemDTO (item.id, item.name, item.urlPhoto,item.urlPhotoContentType, item.gender, item.composed,ip.price, ip.priceDate, ip.availableStockQuantity, si.isSchoolItem,  sl.schoolLevel, si.quantityNeeded, ci.quantity) from ItemValue ip,CompositeItem ci, SchoolLevelItem si, SchoolLevel sl, Item item where ((item.archivated != true) and (ip.item.id = item.id) and (si.item.id = item.id) and (si.item.id = ip.item.id) and (sl.schoolLevel.id = si.schoolLevel.id) and (si.isSchoolItem = true) and (ci.composer.id =:id) and (ci.composer.id = item.id) )")
    List<ItemDTO> findAllCompositeurSchoolItemsDetailsold(@Param("id") Long id);

    @Query("select new com.awtar.myapp.service.dto.ItemDTO (item.id, item.name, item.urlPhoto,item.urlPhotoContentType, item.gender, item.composed, ci.quantity, ip.priceDate, ip.price, ip.availableStockQuantity, (select( si.isSchoolItem,  sl.schoolLevel, si.quantityNeeded)from SchoolLevelItem si, SchoolLevel sl where (sl.schoolLevel.id = si.schoolLevel.id) and (si.isSchoolItem = true)))from ItemValue ip,CompositeItem ci, SchoolLevelItem si, SchoolLevel sl, Item item where ((item.archivated != true) and (ip.item.id = item.id) and (si.item.id = item.id) and (si.item.id = ip.item.id) and (sl.schoolLevel.id = si.schoolLevel.id) and (si.isSchoolItem = true) and (ci.composer.id =:id) and (ci.composer.id = item.id) )")
    List<ItemDTO> findAllCompositeurSchoolItemsDetails(@Param("id") Long id);*/

    @Query(
        "select new com.awtar.myapp.service.dto.ItemDTO (item.id, item.name, item.urlPhoto, item.urlPhotoContentType, item.gender, item.composed, ip.price, ip.priceDate, ip.availableStockQuantity) from ItemValue ip, Item item where ((item.archivated != true) and (ip.item.id = item.id) and (item.nature.id =:id) )"
    )
    List<ItemDTO> findDetailsItemsWithNature(@Param("id") Long id);
}
