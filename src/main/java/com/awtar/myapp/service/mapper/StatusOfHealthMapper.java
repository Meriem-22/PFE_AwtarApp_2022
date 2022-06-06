package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.HealthStatusCategory;
import com.awtar.myapp.domain.Profile;
import com.awtar.myapp.domain.StatusOfHealth;
import com.awtar.myapp.service.dto.HealthStatusCategoryDTO;
import com.awtar.myapp.service.dto.ProfileDTO;
import com.awtar.myapp.service.dto.StatusOfHealthDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StatusOfHealth} and its DTO {@link StatusOfHealthDTO}.
 */
@Mapper(componentModel = "spring")
public interface StatusOfHealthMapper extends EntityMapper<StatusOfHealthDTO, StatusOfHealth> {
    @Mapping(target = "person", source = "person", qualifiedByName = "profileId")
    @Mapping(target = "healthStatusCategory", source = "healthStatusCategory", qualifiedByName = "healthStatusCategoryName")
    StatusOfHealthDTO toDto(StatusOfHealth s);

    @Named("profileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProfileDTO toDtoProfileId(Profile profile);

    @Named("healthStatusCategoryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    HealthStatusCategoryDTO toDtoHealthStatusCategoryName(HealthStatusCategory healthStatusCategory);
}
