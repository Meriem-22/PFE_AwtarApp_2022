package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Family;
import com.awtar.myapp.domain.Parent;
import com.awtar.myapp.service.dto.FamilyDTO;
import com.awtar.myapp.service.dto.ParentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Parent} and its DTO {@link ParentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParentMapper extends EntityMapper<ParentDTO, Parent> {
    @Mapping(target = "familyHead", source = "familyHead", qualifiedByName = "familyId")
    @Mapping(target = "family", source = "family", qualifiedByName = "familyId")
    ParentDTO toDto(Parent s);

    @Named("familyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FamilyDTO toDtoFamilyId(Family family);
}
