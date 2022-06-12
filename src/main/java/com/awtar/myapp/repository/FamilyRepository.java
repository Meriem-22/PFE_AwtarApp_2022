package com.awtar.myapp.repository;

import com.awtar.myapp.domain.Family;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Family entity.
 */
@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {
    @Query("select distinct family from Family family")
    List<Family> findAllFamilyNotArchivated();
}
