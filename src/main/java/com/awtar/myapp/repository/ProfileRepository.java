package com.awtar.myapp.repository;

import com.awtar.myapp.domain.Family;
import com.awtar.myapp.domain.Parent;
import com.awtar.myapp.domain.Profile;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.ProfileDTO;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Profile entity.
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    default Optional<Profile> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Profile> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Profile> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct profile from Profile profile left join fetch profile.birthPlace left join fetch profile.placeOfResidence",
        countQuery = "select count(distinct profile) from Profile profile"
    )
    Page<Profile> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct profile from Profile profile left join fetch profile.birthPlace left join fetch profile.placeOfResidence")
    List<Profile> findAllWithToOneRelationships();

    @Query(
        "select profile from Profile profile left join fetch profile.birthPlace left join fetch profile.placeOfResidence where profile.id =:id"
    )
    Optional<Profile> findOneWithToOneRelationships(@Param("id") Long id);

    @Query(
        "select new com.awtar.myapp.service.dto.ProfileDTO (p.id, f.firstName,  f.lastName,  f.dateOfBirth, f.urlPhoto, p.annualRevenue, p.occupation, p.familyHead.id, f.urlPhotoContentType, f.phone, f.address, p.deceased, p.dateOfDeath) from Profile f join f.parent p where f.parent.family = :family"
    )
    List<ProfileDTO> findParentsOfOneFamily(@Param("family") Family family);

    @Query(
        "select distinct profile from Profile profile left join fetch profile.birthPlace left join fetch profile.placeOfResidence where profile.child.family = :family"
    )
    List<Profile> findChildrenOfOneFamily(@Param("family") Family family);

    @Query(
        "select distinct profile from Profile profile left join fetch profile.birthPlace left join fetch profile.placeOfResidence where profile.child IS NOT NULL"
    )
    List<Profile> findAllProfileChild();

    @Query(
        "select distinct profile from Profile profile left join fetch profile.birthPlace left join fetch profile.placeOfResidence where profile.tutor IS NOT NULL"
    )
    List<Profile> findProfileTutor();

    @Query(
        "select distinct profile from Profile profile left join fetch profile.birthPlace left join fetch profile.placeOfResidence where profile.authorizingOfficer IS NOT NULL"
    )
    List<Profile> findProfileAuthorizingOfficer();

    @Query(
        "select profile from Profile profile where ((profile.authorizingOfficer.id =:id) or (profile.tutor.id =:id)  or (profile.parent.id =:id) or (profile.child.id =:id))"
    )
    Optional<Profile> findProfileX(@Param("id") Long id);

    @Query(
        "select new com.awtar.myapp.service.dto.ProfileDTO(a.id, p.firstName, p.lastName, p.address, p.urlPhoto, p.urlPhotoContentType, a.activity) from Profile p join p.authorizingOfficer a where p.authorizingOfficer.id !=:id and p.authorizingOfficer IS NOT NULL"
    )
    List<ProfileDTO> findOthersAuthorizingOfficersProfiles(@Param("id") Long id);

    @Query(
        "select new com.awtar.myapp.service.dto.ProfileDTO(t.id, p.firstName, p.lastName, p.address, p.urlPhoto, p.urlPhotoContentType, t.activity) from Profile p join p.tutor t where p.tutor.id !=:id and p.tutor IS NOT NULL"
    )
    List<ProfileDTO> findOthersTutorsProfiles(@Param("id") Long id);

    @Query(
        "select new com.awtar.myapp.service.dto.ProfileDTO(c.id, p.firstName, p.lastName, p.dateOfBirth, p.urlPhoto, p.urlPhotoContentType, c.family.familyName, p.gender) from Profile p, Child c where p.child.id = c.id"
    )
    List<ProfileDTO> findallchildren();

    @Query(
        "select new com.awtar.myapp.service.dto.ProfileDTO(a.id, p.firstName, p.lastName, p.address, p.urlPhoto, p.urlPhotoContentType, a.activity) from Profile p join p.authorizingOfficer a where (p.archivated= false or p.archivated= null) and p.authorizingOfficer IS NOT NULL"
    )
    List<ProfileDTO> findAllAuthorizingOfficers();

    @Query(
        "select new com.awtar.myapp.service.dto.ProfileDTO(t.id, p.firstName, p.lastName, p.address, p.urlPhoto, p.urlPhotoContentType, t.activity) from Profile p join p.tutor t where (p.archivated= false or p.archivated= null) and p.tutor IS NOT NULL"
    )
    List<ProfileDTO> findAllTutors();
}
