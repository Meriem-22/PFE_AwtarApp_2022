package com.awtar.myapp.repository;

import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.TutorDTO;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tutor entity.
 */

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    @Query(
        "select new com.awtar.myapp.service.dto.TutorDTO(t.id, p.firstName, p.lastName, p.address, p.urlPhoto, p.urlPhotoContentType, t.activity) from Profile p join p.tutor t where p.archivated != true and p.tutor IS NOT NULL"
    )
    List<TutorDTO> findTutorsDetails();
}
