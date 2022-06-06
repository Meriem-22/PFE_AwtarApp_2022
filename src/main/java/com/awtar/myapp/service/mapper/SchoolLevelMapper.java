package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.SchoolLevel;
import com.awtar.myapp.service.dto.SchoolLevelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SchoolLevel} and its DTO {@link SchoolLevelDTO}.
 */
@Mapper(componentModel = "spring")
public interface SchoolLevelMapper extends EntityMapper<SchoolLevelDTO, SchoolLevel> {}
