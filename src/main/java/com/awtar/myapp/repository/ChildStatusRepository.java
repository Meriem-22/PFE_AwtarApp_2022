package com.awtar.myapp.repository;

import com.awtar.myapp.domain.ChildStatus;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ChildStatus entity.
 */
@Repository
public interface ChildStatusRepository extends JpaRepository<ChildStatus, Long> {}
