package com.awtar.myapp.repository;

import com.awtar.myapp.domain.SchoolLevel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SchoolLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchoolLevelRepository extends JpaRepository<SchoolLevel, Long> {}
