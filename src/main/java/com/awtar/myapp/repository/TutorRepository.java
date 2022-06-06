package com.awtar.myapp.repository;

import com.awtar.myapp.domain.Tutor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tutor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {}
