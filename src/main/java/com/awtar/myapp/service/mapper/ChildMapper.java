package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Child;
import com.awtar.myapp.domain.Family;
import com.awtar.myapp.domain.Profile;
import com.awtar.myapp.service.dto.ChildDTO;
import com.awtar.myapp.service.dto.FamilyDTO;
import com.awtar.myapp.service.dto.ProfileDTO;
import java.util.List;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Child} and its DTO {@link ChildDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChildMapper extends EntityMapper<ChildDTO, Child> {
    @Mapping(target = "family", source = "family", qualifiedByName = "familyId")
    ChildDTO toDto(Child s);

    @Named("familyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FamilyDTO toDtoFamilyId(Family family);
}
