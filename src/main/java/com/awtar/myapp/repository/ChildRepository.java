package com.awtar.myapp.repository;

import com.awtar.myapp.domain.Child;
import com.awtar.myapp.service.dto.ChildDTO;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Child entity.
 */
@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {
    @Query(
        "select new com.awtar.myapp.service.dto.ChildDTO (c.id, p.id, p.firstName, p.lastName, p.gender, p.dateOfBirth, f.familyName, e.beginningYear,  e.endYear, e.annualResult, e.result, l.schoolLevel, p.urlPhoto, p.urlPhotoContentType) from Child c, Profile p, Family f, TeachingCurriculum e, SchoolLevel l where c.id = p.child.id and c.id = e.child.id and e.schoolLevel.id = l.id and e.beginningYear = :beginningYear and c.family.id = f.id"
    )
    List<ChildDTO> findAllChildrenDetails(@Param("beginningYear") String beginningYear);

    @Query(
        "select new com.awtar.myapp.service.dto.ChildDTO (c.id, p.id, p.firstName, p.lastName, p.gender, p.dateOfBirth, f.familyName, p.urlPhoto, p.urlPhotoContentType) from Child c, Profile p, Family f where c.id = p.child.id and c.family.id = f.id"
    )
    List<ChildDTO> findChildrenDetails();

    @Query(
        "select new com.awtar.myapp.service.dto.ChildDTO (c.id, p.id, p.firstName, p.lastName, p.gender, p.dateOfBirth, p.urlPhoto, p.urlPhotoContentType) from Child c, Profile p  where ((c.id = p.child.id) and (c.family =NULL))"
    )
    List<ChildDTO> findChildrenWithoutFamilyDetails();

    @Query(
        "select new com.awtar.myapp.service.dto.ChildDTO (c.id, e.beginningYear,  e.endYear, e.annualResult, e.result, l.schoolLevel) from Child c, TeachingCurriculum e, SchoolLevel l where c.id = e.child.id and e.schoolLevel.id = l.id and e.beginningYear = :beginningYear"
    )
    List<ChildDTO> findChildEducationDetails(@Param("beginningYear") String beginningYear);

    @Query(
        "select new com.awtar.myapp.service.dto.ChildDTO ( COUNT(c.id) As NbInCity) from Child c, Profile p where c.id = p.child.id and p.placeOfResidence.id = :id and (p.archivated = null or p.archivated = false)"
    )
    ChildDTO findChildrenInCity(@Param("id") Long city);
}
