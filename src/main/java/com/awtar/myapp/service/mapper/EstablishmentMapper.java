package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.City;
import com.awtar.myapp.domain.Establishment;
import com.awtar.myapp.domain.EstablishmentType;
import com.awtar.myapp.service.dto.CityDTO;
import com.awtar.myapp.service.dto.EstablishmentDTO;
import com.awtar.myapp.service.dto.EstablishmentTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Establishment} and its DTO {@link EstablishmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface EstablishmentMapper extends EntityMapper<EstablishmentDTO, Establishment> {
    @Mapping(target = "establishmentType", source = "establishmentType", qualifiedByName = "establishmentTypeTypeName")
    @Mapping(target = "city", source = "city", qualifiedByName = "cityName")
    EstablishmentDTO toDto(Establishment s);

    @Named("establishmentTypeTypeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "typeName", source = "typeName")
    EstablishmentTypeDTO toDtoEstablishmentTypeTypeName(EstablishmentType establishmentType);

    @Named("cityName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CityDTO toDtoCityName(City city);
}
