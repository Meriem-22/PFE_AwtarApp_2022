package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Family;
import com.awtar.myapp.service.dto.FamilyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Family} and its DTO {@link FamilyDTO}.
 */
@Mapper(componentModel = "spring")
public interface FamilyMapper extends EntityMapper<FamilyDTO, Family> {}
