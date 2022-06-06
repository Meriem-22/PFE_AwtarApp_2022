package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.City;
import com.awtar.myapp.service.dto.CityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CityMapper extends EntityMapper<CityDTO, City> {}
