package com.awtar.myapp.repository;

import com.awtar.myapp.domain.HealthStatusCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HealthStatusCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HealthStatusCategoryRepository extends JpaRepository<HealthStatusCategory, Long> {}
