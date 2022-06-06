package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.HealthStatusCategory;
import com.awtar.myapp.service.dto.HealthStatusCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HealthStatusCategory} and its DTO {@link HealthStatusCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface HealthStatusCategoryMapper extends EntityMapper<HealthStatusCategoryDTO, HealthStatusCategory> {}
