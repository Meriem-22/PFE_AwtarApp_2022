package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.EstablishmentType;
import com.awtar.myapp.service.dto.EstablishmentTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstablishmentType} and its DTO {@link EstablishmentTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EstablishmentTypeMapper extends EntityMapper<EstablishmentTypeDTO, EstablishmentType> {}
