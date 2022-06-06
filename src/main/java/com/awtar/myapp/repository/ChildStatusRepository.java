package com.awtar.myapp.repository;

import com.awtar.myapp.domain.ChildStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ChildStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChildStatusRepository extends JpaRepository<ChildStatus, Long> {}
