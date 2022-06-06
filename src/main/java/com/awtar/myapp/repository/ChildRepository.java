package com.awtar.myapp.repository;

import com.awtar.myapp.domain.Child;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Child entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {}
