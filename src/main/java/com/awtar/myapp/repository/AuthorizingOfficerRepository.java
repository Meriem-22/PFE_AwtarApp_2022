package com.awtar.myapp.repository;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AuthorizingOfficer entity.
 */

@Repository
public interface AuthorizingOfficerRepository extends JpaRepository<AuthorizingOfficer, Long> {
    @Query(
        "select new com.awtar.myapp.service.dto.AuthorizingOfficerDTO(a.id, p.firstName, p.lastName, p.address, p.urlPhoto, p.urlPhotoContentType, a.activity) from Profile p join p.authorizingOfficer a where p.archivated != true and p.authorizingOfficer IS NOT NULL"
    )
    List<AuthorizingOfficerDTO> findAuthorizingOfficersDetails();
}
