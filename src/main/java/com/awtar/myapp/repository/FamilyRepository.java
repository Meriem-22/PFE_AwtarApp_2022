package com.awtar.myapp.repository;

import com.awtar.myapp.domain.Family;
import com.awtar.myapp.service.dto.FamilyDTO;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Family entity.
 */
@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {
    @Query("select distinct family from Family family")
    List<Family> findAllFamilyNotArchivated();

    @Query(
        "select new com.awtar.myapp.service.dto.FamilyDTO ( COUNT(f.id) As NbInCity) from Family f, Parent p, Profile pf where p.familyHead.id = f.id and p.id= pf.parent.id and pf.placeOfResidence.id = :id and (f.archivated = null or f.archivated = false)"
    )
    FamilyDTO findFamiliesInCity(@Param("id") Long city);
}
