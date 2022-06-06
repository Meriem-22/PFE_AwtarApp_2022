package com.awtar.myapp.repository;

import com.awtar.myapp.domain.EstablishmentType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EstablishmentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstablishmentTypeRepository extends JpaRepository<EstablishmentType, Long> {}
