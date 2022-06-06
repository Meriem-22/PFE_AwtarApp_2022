package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.Child;
import com.awtar.myapp.domain.City;
import com.awtar.myapp.domain.Parent;
import com.awtar.myapp.domain.Profile;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import com.awtar.myapp.service.dto.ChildDTO;
import com.awtar.myapp.service.dto.CityDTO;
import com.awtar.myapp.service.dto.ParentDTO;
import com.awtar.myapp.service.dto.ProfileDTO;
import com.awtar.myapp.service.dto.TutorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profile} and its DTO {@link ProfileDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {
    @Mapping(target = "parent", source = "parent", qualifiedByName = "parentId")
    @Mapping(target = "child", source = "child", qualifiedByName = "childId")
    @Mapping(target = "authorizingOfficer", source = "authorizingOfficer", qualifiedByName = "authorizingOfficerId")
    @Mapping(target = "tutor", source = "tutor", qualifiedByName = "tutorId")
    @Mapping(target = "birthPlace", source = "birthPlace", qualifiedByName = "cityName")
    @Mapping(target = "placeOfResidence", source = "placeOfResidence", qualifiedByName = "cityName")
    ProfileDTO toDto(Profile s);

    @Named("parentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ParentDTO toDtoParentId(Parent parent);

    @Named("childId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChildDTO toDtoChildId(Child child);

    @Named("authorizingOfficerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AuthorizingOfficerDTO toDtoAuthorizingOfficerId(AuthorizingOfficer authorizingOfficer);

    @Named("tutorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TutorDTO toDtoTutorId(Tutor tutor);

    @Named("cityName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CityDTO toDtoCityName(City city);
}
