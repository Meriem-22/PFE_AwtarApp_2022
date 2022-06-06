package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.City;
import com.awtar.myapp.domain.EducationalInstitution;
import com.awtar.myapp.service.dto.CityDTO;
import com.awtar.myapp.service.dto.EducationalInstitutionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EducationalInstitution} and its DTO {@link EducationalInstitutionDTO}.
 */
@Mapper(componentModel = "spring")
public interface EducationalInstitutionMapper extends EntityMapper<EducationalInstitutionDTO, EducationalInstitution> {
    @Mapping(target = "city", source = "city", qualifiedByName = "cityName")
    EducationalInstitutionDTO toDto(EducationalInstitution s);

    @Named("cityName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CityDTO toDtoCityName(City city);
}
