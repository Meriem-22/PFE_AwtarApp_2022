package com.awtar.myapp.repository;

import com.awtar.myapp.domain.AuthorizingOfficer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AuthorizingOfficer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorizingOfficerRepository extends JpaRepository<AuthorizingOfficer, Long> {}
