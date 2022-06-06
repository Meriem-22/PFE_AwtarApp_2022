package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Nature;
import com.awtar.myapp.service.dto.NatureDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nature} and its DTO {@link NatureDTO}.
 */
@Mapper(componentModel = "spring")
public interface NatureMapper extends EntityMapper<NatureDTO, Nature> {}
