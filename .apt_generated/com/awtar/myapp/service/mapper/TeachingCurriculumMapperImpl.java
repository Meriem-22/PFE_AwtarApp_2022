package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.Child;
import com.awtar.myapp.domain.City;
import com.awtar.myapp.domain.EducationalInstitution;
import com.awtar.myapp.domain.Family;
import com.awtar.myapp.domain.SchoolLevel;
import com.awtar.myapp.domain.TeachingCurriculum;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.ChildDTO;
import com.awtar.myapp.service.dto.CityDTO;
import com.awtar.myapp.service.dto.EducationalInstitutionDTO;
import com.awtar.myapp.service.dto.FamilyDTO;
import com.awtar.myapp.service.dto.ProfileDTO;
import com.awtar.myapp.service.dto.SchoolLevelDTO;
import com.awtar.myapp.service.dto.TeachingCurriculumDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-06T08:12:07+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20221012-0724, environment: Java 17.0.5 (Eclipse Adoptium)"
)
@Component
public class TeachingCurriculumMapperImpl implements TeachingCurriculumMapper {

    @Override
    public TeachingCurriculum toEntity(TeachingCurriculumDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TeachingCurriculum teachingCurriculum = new TeachingCurriculum();

        teachingCurriculum.setId( dto.getId() );
        teachingCurriculum.setBeginningYear( dto.getBeginningYear() );
        teachingCurriculum.setEndYear( dto.getEndYear() );
        teachingCurriculum.setAnnualResult( dto.getAnnualResult() );
        teachingCurriculum.setResult( dto.getResult() );
        teachingCurriculum.setRemarks( dto.getRemarks() );
        byte[] attachedFile = dto.getAttachedFile();
        if ( attachedFile != null ) {
            teachingCurriculum.setAttachedFile( Arrays.copyOf( attachedFile, attachedFile.length ) );
        }
        teachingCurriculum.setAttachedFileContentType( dto.getAttachedFileContentType() );
        teachingCurriculum.setArchivated( dto.getArchivated() );
        teachingCurriculum.schoolLevel( schoolLevelDTOToSchoolLevel( dto.getSchoolLevel() ) );
        teachingCurriculum.child( childDTOToChild( dto.getChild() ) );
        teachingCurriculum.educationalInstitution( educationalInstitutionDTOToEducationalInstitution( dto.getEducationalInstitution() ) );

        return teachingCurriculum;
    }

    @Override
    public List<TeachingCurriculum> toEntity(List<TeachingCurriculumDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<TeachingCurriculum> list = new ArrayList<TeachingCurriculum>( dtoList.size() );
        for ( TeachingCurriculumDTO teachingCurriculumDTO : dtoList ) {
            list.add( toEntity( teachingCurriculumDTO ) );
        }

        return list;
    }

    @Override
    public List<TeachingCurriculumDTO> toDto(List<TeachingCurriculum> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TeachingCurriculumDTO> list = new ArrayList<TeachingCurriculumDTO>( entityList.size() );
        for ( TeachingCurriculum teachingCurriculum : entityList ) {
            list.add( toDto( teachingCurriculum ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(TeachingCurriculum entity, TeachingCurriculumDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getBeginningYear() != null ) {
            entity.setBeginningYear( dto.getBeginningYear() );
        }
        if ( dto.getEndYear() != null ) {
            entity.setEndYear( dto.getEndYear() );
        }
        if ( dto.getAnnualResult() != null ) {
            entity.setAnnualResult( dto.getAnnualResult() );
        }
        if ( dto.getResult() != null ) {
            entity.setResult( dto.getResult() );
        }
        if ( dto.getRemarks() != null ) {
            entity.setRemarks( dto.getRemarks() );
        }
        byte[] attachedFile = dto.getAttachedFile();
        if ( attachedFile != null ) {
            entity.setAttachedFile( Arrays.copyOf( attachedFile, attachedFile.length ) );
        }
        if ( dto.getAttachedFileContentType() != null ) {
            entity.setAttachedFileContentType( dto.getAttachedFileContentType() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
        if ( dto.getSchoolLevel() != null ) {
            if ( entity.getSchoolLevel() == null ) {
                entity.schoolLevel( new SchoolLevel() );
            }
            schoolLevelDTOToSchoolLevel1( dto.getSchoolLevel(), entity.getSchoolLevel() );
        }
        if ( dto.getChild() != null ) {
            if ( entity.getChild() == null ) {
                entity.child( new Child() );
            }
            childDTOToChild1( dto.getChild(), entity.getChild() );
        }
        if ( dto.getEducationalInstitution() != null ) {
            if ( entity.getEducationalInstitution() == null ) {
                entity.educationalInstitution( new EducationalInstitution() );
            }
            educationalInstitutionDTOToEducationalInstitution1( dto.getEducationalInstitution(), entity.getEducationalInstitution() );
        }
    }

    @Override
    public TeachingCurriculumDTO toDto(TeachingCurriculum s) {
        if ( s == null ) {
            return null;
        }

        TeachingCurriculumDTO teachingCurriculumDTO = new TeachingCurriculumDTO();

        teachingCurriculumDTO.setSchoolLevel( toDtoSchoolLevelSchoolLevel( s.getSchoolLevel() ) );
        teachingCurriculumDTO.setChild( toDtoChildId( s.getChild() ) );
        teachingCurriculumDTO.setEducationalInstitution( toDtoEducationalInstitutionName( s.getEducationalInstitution() ) );
        teachingCurriculumDTO.setId( s.getId() );
        teachingCurriculumDTO.setBeginningYear( s.getBeginningYear() );
        teachingCurriculumDTO.setEndYear( s.getEndYear() );
        teachingCurriculumDTO.setAnnualResult( s.getAnnualResult() );
        teachingCurriculumDTO.setResult( s.getResult() );
        teachingCurriculumDTO.setRemarks( s.getRemarks() );
        byte[] attachedFile = s.getAttachedFile();
        if ( attachedFile != null ) {
            teachingCurriculumDTO.setAttachedFile( Arrays.copyOf( attachedFile, attachedFile.length ) );
        }
        teachingCurriculumDTO.setAttachedFileContentType( s.getAttachedFileContentType() );
        teachingCurriculumDTO.setArchivated( s.getArchivated() );

        return teachingCurriculumDTO;
    }

    @Override
    public SchoolLevelDTO toDtoSchoolLevelSchoolLevel(SchoolLevel schoolLevel) {
        if ( schoolLevel == null ) {
            return null;
        }

        SchoolLevelDTO schoolLevelDTO = new SchoolLevelDTO();

        schoolLevelDTO.setId( schoolLevel.getId() );
        schoolLevelDTO.setSchoolLevel( schoolLevel.getSchoolLevel() );

        return schoolLevelDTO;
    }

    @Override
    public ChildDTO toDtoChildId(Child child) {
        if ( child == null ) {
            return null;
        }

        ChildDTO childDTO = new ChildDTO();

        childDTO.setId( child.getId() );

        return childDTO;
    }

    @Override
    public EducationalInstitutionDTO toDtoEducationalInstitutionName(EducationalInstitution educationalInstitution) {
        if ( educationalInstitution == null ) {
            return null;
        }

        EducationalInstitutionDTO educationalInstitutionDTO = new EducationalInstitutionDTO();

        educationalInstitutionDTO.setId( educationalInstitution.getId() );
        educationalInstitutionDTO.setName( educationalInstitution.getName() );

        return educationalInstitutionDTO;
    }

    protected SchoolLevel schoolLevelDTOToSchoolLevel(SchoolLevelDTO schoolLevelDTO) {
        if ( schoolLevelDTO == null ) {
            return null;
        }

        SchoolLevel schoolLevel = new SchoolLevel();

        schoolLevel.setId( schoolLevelDTO.getId() );
        schoolLevel.setSchoolLevel( schoolLevelDTO.getSchoolLevel() );
        schoolLevel.setArchivated( schoolLevelDTO.getArchivated() );

        return schoolLevel;
    }

    protected AuthorizingOfficer profileDTOToAuthorizingOfficer(ProfileDTO profileDTO) {
        if ( profileDTO == null ) {
            return null;
        }

        AuthorizingOfficer authorizingOfficer = new AuthorizingOfficer();

        authorizingOfficer.setId( profileDTO.getId() );
        authorizingOfficer.setActivity( profileDTO.getActivity() );

        return authorizingOfficer;
    }

    protected Tutor profileDTOToTutor(ProfileDTO profileDTO) {
        if ( profileDTO == null ) {
            return null;
        }

        Tutor tutor = new Tutor();

        tutor.setId( profileDTO.getId() );
        tutor.setActivity( profileDTO.getActivity() );

        return tutor;
    }

    protected Family familyDTOToFamily(FamilyDTO familyDTO) {
        if ( familyDTO == null ) {
            return null;
        }

        Family family = new Family();

        family.setAuthorizingOfficer( profileDTOToAuthorizingOfficer( familyDTO.getAuthorizingOfficer() ) );
        family.setTutor( profileDTOToTutor( familyDTO.getTutor() ) );
        family.setId( familyDTO.getId() );
        family.setFamilyName( familyDTO.getFamilyName() );
        family.setDwelling( familyDTO.getDwelling() );
        family.setArea( familyDTO.getArea() );
        family.setNotebookOfPoverty( familyDTO.getNotebookOfPoverty() );
        family.setNotebookOfHandicap( familyDTO.getNotebookOfHandicap() );
        family.setArchivated( familyDTO.getArchivated() );

        return family;
    }

    protected Child childDTOToChild(ChildDTO childDTO) {
        if ( childDTO == null ) {
            return null;
        }

        Child child = new Child();

        child.setAuthorizingOfficer( profileDTOToAuthorizingOfficer( childDTO.getAuthorizingOfficer() ) );
        child.setTutor( profileDTOToTutor( childDTO.getTutor() ) );
        child.setId( childDTO.getId() );
        child.family( familyDTOToFamily( childDTO.getFamily() ) );

        return child;
    }

    protected City cityDTOToCity(CityDTO cityDTO) {
        if ( cityDTO == null ) {
            return null;
        }

        City city = new City();

        city.setId( cityDTO.getId() );
        city.setName( cityDTO.getName() );
        city.setGovernorate( cityDTO.getGovernorate() );
        city.setIsGovernorate( cityDTO.getIsGovernorate() );
        city.setArchivated( cityDTO.getArchivated() );

        return city;
    }

    protected EducationalInstitution educationalInstitutionDTOToEducationalInstitution(EducationalInstitutionDTO educationalInstitutionDTO) {
        if ( educationalInstitutionDTO == null ) {
            return null;
        }

        EducationalInstitution educationalInstitution = new EducationalInstitution();

        educationalInstitution.setId( educationalInstitutionDTO.getId() );
        educationalInstitution.setName( educationalInstitutionDTO.getName() );
        educationalInstitution.setAdress( educationalInstitutionDTO.getAdress() );
        educationalInstitution.setArchivated( educationalInstitutionDTO.getArchivated() );
        educationalInstitution.city( cityDTOToCity( educationalInstitutionDTO.getCity() ) );

        return educationalInstitution;
    }

    protected void schoolLevelDTOToSchoolLevel1(SchoolLevelDTO schoolLevelDTO, SchoolLevel mappingTarget) {
        if ( schoolLevelDTO == null ) {
            return;
        }

        if ( schoolLevelDTO.getId() != null ) {
            mappingTarget.setId( schoolLevelDTO.getId() );
        }
        if ( schoolLevelDTO.getSchoolLevel() != null ) {
            mappingTarget.setSchoolLevel( schoolLevelDTO.getSchoolLevel() );
        }
        if ( schoolLevelDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( schoolLevelDTO.getArchivated() );
        }
    }

    protected void profileDTOToAuthorizingOfficer1(ProfileDTO profileDTO, AuthorizingOfficer mappingTarget) {
        if ( profileDTO == null ) {
            return;
        }

        if ( profileDTO.getId() != null ) {
            mappingTarget.setId( profileDTO.getId() );
        }
        if ( profileDTO.getActivity() != null ) {
            mappingTarget.setActivity( profileDTO.getActivity() );
        }
    }

    protected void profileDTOToTutor1(ProfileDTO profileDTO, Tutor mappingTarget) {
        if ( profileDTO == null ) {
            return;
        }

        if ( profileDTO.getId() != null ) {
            mappingTarget.setId( profileDTO.getId() );
        }
        if ( profileDTO.getActivity() != null ) {
            mappingTarget.setActivity( profileDTO.getActivity() );
        }
    }

    protected void familyDTOToFamily1(FamilyDTO familyDTO, Family mappingTarget) {
        if ( familyDTO == null ) {
            return;
        }

        if ( familyDTO.getAuthorizingOfficer() != null ) {
            if ( mappingTarget.getAuthorizingOfficer() == null ) {
                mappingTarget.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            profileDTOToAuthorizingOfficer1( familyDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
        if ( familyDTO.getTutor() != null ) {
            if ( mappingTarget.getTutor() == null ) {
                mappingTarget.setTutor( new Tutor() );
            }
            profileDTOToTutor1( familyDTO.getTutor(), mappingTarget.getTutor() );
        }
        if ( familyDTO.getId() != null ) {
            mappingTarget.setId( familyDTO.getId() );
        }
        if ( familyDTO.getFamilyName() != null ) {
            mappingTarget.setFamilyName( familyDTO.getFamilyName() );
        }
        if ( familyDTO.getDwelling() != null ) {
            mappingTarget.setDwelling( familyDTO.getDwelling() );
        }
        if ( familyDTO.getArea() != null ) {
            mappingTarget.setArea( familyDTO.getArea() );
        }
        if ( familyDTO.getNotebookOfPoverty() != null ) {
            mappingTarget.setNotebookOfPoverty( familyDTO.getNotebookOfPoverty() );
        }
        if ( familyDTO.getNotebookOfHandicap() != null ) {
            mappingTarget.setNotebookOfHandicap( familyDTO.getNotebookOfHandicap() );
        }
        if ( familyDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( familyDTO.getArchivated() );
        }
    }

    protected void childDTOToChild1(ChildDTO childDTO, Child mappingTarget) {
        if ( childDTO == null ) {
            return;
        }

        if ( childDTO.getAuthorizingOfficer() != null ) {
            if ( mappingTarget.getAuthorizingOfficer() == null ) {
                mappingTarget.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            profileDTOToAuthorizingOfficer1( childDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
        if ( childDTO.getTutor() != null ) {
            if ( mappingTarget.getTutor() == null ) {
                mappingTarget.setTutor( new Tutor() );
            }
            profileDTOToTutor1( childDTO.getTutor(), mappingTarget.getTutor() );
        }
        if ( childDTO.getId() != null ) {
            mappingTarget.setId( childDTO.getId() );
        }
        if ( childDTO.getFamily() != null ) {
            if ( mappingTarget.getFamily() == null ) {
                mappingTarget.family( new Family() );
            }
            familyDTOToFamily1( childDTO.getFamily(), mappingTarget.getFamily() );
        }
    }

    protected void cityDTOToCity1(CityDTO cityDTO, City mappingTarget) {
        if ( cityDTO == null ) {
            return;
        }

        if ( cityDTO.getId() != null ) {
            mappingTarget.setId( cityDTO.getId() );
        }
        if ( cityDTO.getName() != null ) {
            mappingTarget.setName( cityDTO.getName() );
        }
        if ( cityDTO.getGovernorate() != null ) {
            mappingTarget.setGovernorate( cityDTO.getGovernorate() );
        }
        if ( cityDTO.getIsGovernorate() != null ) {
            mappingTarget.setIsGovernorate( cityDTO.getIsGovernorate() );
        }
        if ( cityDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( cityDTO.getArchivated() );
        }
    }

    protected void educationalInstitutionDTOToEducationalInstitution1(EducationalInstitutionDTO educationalInstitutionDTO, EducationalInstitution mappingTarget) {
        if ( educationalInstitutionDTO == null ) {
            return;
        }

        if ( educationalInstitutionDTO.getId() != null ) {
            mappingTarget.setId( educationalInstitutionDTO.getId() );
        }
        if ( educationalInstitutionDTO.getName() != null ) {
            mappingTarget.setName( educationalInstitutionDTO.getName() );
        }
        if ( educationalInstitutionDTO.getAdress() != null ) {
            mappingTarget.setAdress( educationalInstitutionDTO.getAdress() );
        }
        if ( educationalInstitutionDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( educationalInstitutionDTO.getArchivated() );
        }
        if ( educationalInstitutionDTO.getCity() != null ) {
            if ( mappingTarget.getCity() == null ) {
                mappingTarget.city( new City() );
            }
            cityDTOToCity1( educationalInstitutionDTO.getCity(), mappingTarget.getCity() );
        }
    }
}
