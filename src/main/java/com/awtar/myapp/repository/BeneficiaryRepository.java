package com.awtar.myapp.repository;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.Beneficiary;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.domain.enumeration.Beneficiaries;
import com.awtar.myapp.service.dto.BeneficiaryDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Beneficiary entity.
 */
@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
    default Optional<Beneficiary> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Beneficiary> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Beneficiary> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct beneficiary from Beneficiary beneficiary left join fetch beneficiary.authorizingOfficer",
        countQuery = "select count(distinct beneficiary) from Beneficiary beneficiary"
    )
    Page<Beneficiary> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct beneficiary from Beneficiary beneficiary left join fetch beneficiary.authorizingOfficer")
    List<Beneficiary> findAllWithToOneRelationships();

    @Query("select beneficiary from Beneficiary beneficiary left join fetch beneficiary.authorizingOfficer where beneficiary.id =:id")
    Optional<Beneficiary> findOneWithToOneRelationships(@Param("id") Long id);

    @Query(
        "select new com.awtar.myapp.service.dto.BeneficiaryDTO(b.id, f.familyName) from Beneficiary b, Family f where b.id = f.id and (b.authorizingOfficer.id =:id or b.tutor.id =:id)"
    )
    List<BeneficiaryDTO> findAllFamiliesByContributors(@Param("id") Long id);

    @Query(
        "select new com.awtar.myapp.service.dto.BeneficiaryDTO(b.id, p.firstName, p.lastName, p.urlPhoto, p.urlPhotoContentType) from Beneficiary b, Profile p where b.id = p.child.id and (b.authorizingOfficer.id =:id or b.tutor.id =:id)"
    )
    List<BeneficiaryDTO> findAllChildrenByContributors(@Param("id") Long id);

    @Query(
        "select new com.awtar.myapp.service.dto.BeneficiaryDTO(b.id, e.name, e.activity) from Beneficiary b, Establishment e where b.id = e.id and (b.authorizingOfficer.id =:id or b.tutor.id =:id)"
    )
    List<BeneficiaryDTO> findAllEstablishmentsByContributors(@Param("id") Long id);

    @Modifying
    @Query("update Beneficiary set beneficiaryReference= :reference, authorizingOfficer= :authorizingOfficer  where id= :id")
    void setBeneficiaryReference(
        @Param("id") Long id,
        @Param("reference") String reference,
        @Param("authorizingOfficer") AuthorizingOfficer authorizingOfficer
    );

    @Modifying
    @Query("update Beneficiary set authorizingOfficer= :authorizingOfficer, tutor= :tutor, beneficiaryType= :beneficiaries  where id= :id")
    void updateInfo(
        @Param("id") Long id,
        @Param("authorizingOfficer") AuthorizingOfficer authorizingOfficer,
        @Param("tutor") Tutor tutor,
        @Param("beneficiaries") Beneficiaries beneficiaries
    );

    @Modifying
    @Query("update Beneficiary set beneficiaryReference= :nRef, authorizingOfficer= :authorizingOfficer,tutor= :tutor  where id= :id")
    void setBeneficiaryContributor(
        @Param("id") Long id,
        @Param("nRef") String nRef,
        @Param("authorizingOfficer") AuthorizingOfficer authorizingOfficer,
        @Param("tutor") Tutor tutor
    );

    @Modifying
    @Query("update Beneficiary set authorizingOfficer = NULL  where id= :id")
    Beneficiary removeAuthorizingOfficer(@Param("id") Long id);

    @Modifying
    @Query("update Beneficiary set tutor = NULL  where id= :id")
    Beneficiary removeTutor(@Param("id") Long id);

    @Query(
        "select distinct beneficiary from Beneficiary beneficiary left join fetch beneficiary.authorizingOfficer where (beneficiary.archivated = null or beneficiary.archivated = false) "
    )
    List<Beneficiary> TotalBeneficiaries();
}
