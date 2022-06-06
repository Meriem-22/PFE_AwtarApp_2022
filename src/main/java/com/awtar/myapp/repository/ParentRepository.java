package com.awtar.myapp.repository;

import com.awtar.myapp.domain.Parent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Parent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {}
