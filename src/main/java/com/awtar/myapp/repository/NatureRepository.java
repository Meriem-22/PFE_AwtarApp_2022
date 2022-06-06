package com.awtar.myapp.repository;

import com.awtar.myapp.domain.Nature;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Nature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatureRepository extends JpaRepository<Nature, Long> {}
