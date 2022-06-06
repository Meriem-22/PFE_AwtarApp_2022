package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Child;
import com.awtar.myapp.domain.EducationalInstitution;
import com.awtar.myapp.domain.SchoolLevel;
import com.awtar.myapp.domain.TeachingCurriculum;
import com.awtar.myapp.service.dto.ChildDTO;
import com.awtar.myapp.service.dto.EducationalInstitutionDTO;
import com.awtar.myapp.service.dto.SchoolLevelDTO;
import com.awtar.myapp.service.dto.TeachingCurriculumDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeachingCurriculum} and its DTO {@link TeachingCurriculumDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeachingCurriculumMapper extends EntityMapper<TeachingCurriculumDTO, TeachingCurriculum> {
    @Mapping(target = "schoolLevel", source = "schoolLevel", qualifiedByName = "schoolLevelSchoolLevel")
    @Mapping(target = "child", source = "child", qualifiedByName = "childId")
    @Mapping(target = "educationalInstitution", source = "educationalInstitution", qualifiedByName = "educationalInstitutionName")
    TeachingCurriculumDTO toDto(TeachingCurriculum s);

    @Named("schoolLevelSchoolLevel")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "schoolLevel", source = "schoolLevel")
    SchoolLevelDTO toDtoSchoolLevelSchoolLevel(SchoolLevel schoolLevel);

    @Named("childId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ChildDTO toDtoChildId(Child child);

    @Named("educationalInstitutionName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    EducationalInstitutionDTO toDtoEducationalInstitutionName(EducationalInstitution educationalInstitution);
}
