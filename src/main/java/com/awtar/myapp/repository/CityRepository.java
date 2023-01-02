package com.awtar.myapp.repository;

import com.awtar.myapp.domain.City;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the City entity.
 */

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query("select distinct city from City city where city.archivated !=true")
    List<City> findAllCitys();
}
