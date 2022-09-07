package com.awtar.myapp.repository;

import com.awtar.myapp.domain.SchoolLevel;
import com.awtar.myapp.service.dto.SchoolLevelDTO;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SchoolLevel entity.
 */
@Repository
public interface SchoolLevelRepository extends JpaRepository<SchoolLevel, Long> {
    @Query("select distinct schoolLevel from SchoolLevel schoolLevel where schoolLevel.archivated != true")
    List<SchoolLevel> findAllSchoolLevels();
}
