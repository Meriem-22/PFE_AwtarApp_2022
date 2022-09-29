package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.Child;
import com.awtar.myapp.domain.Family;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.ChildDTO;
import com.awtar.myapp.service.dto.FamilyDTO;
import com.awtar.myapp.service.dto.ProfileDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-29T20:24:20+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class ChildMapperImpl implements ChildMapper {

    @Override
    public Child toEntity(ChildDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Child child = new Child();

        child.setAuthorizingOfficer( profileDTOToAuthorizingOfficer( dto.getAuthorizingOfficer() ) );
        child.setTutor( profileDTOToTutor( dto.getTutor() ) );
        child.setId( dto.getId() );
        child.family( familyDTOToFamily( dto.getFamily() ) );

        return child;
    }

    @Override
    public List<Child> toEntity(List<ChildDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Child> list = new ArrayList<Child>( dtoList.size() );
        for ( ChildDTO childDTO : dtoList ) {
            list.add( toEntity( childDTO ) );
        }

        return list;
    }

    @Override
    public List<ChildDTO> toDto(List<Child> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ChildDTO> list = new ArrayList<ChildDTO>( entityList.size() );
        for ( Child child : entityList ) {
            list.add( toDto( child ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Child entity, ChildDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getAuthorizingOfficer() != null ) {
            if ( entity.getAuthorizingOfficer() == null ) {
                entity.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            profileDTOToAuthorizingOfficer1( dto.getAuthorizingOfficer(), entity.getAuthorizingOfficer() );
        }
        if ( dto.getTutor() != null ) {
            if ( entity.getTutor() == null ) {
                entity.setTutor( new Tutor() );
            }
            profileDTOToTutor1( dto.getTutor(), entity.getTutor() );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getFamily() != null ) {
            if ( entity.getFamily() == null ) {
                entity.family( new Family() );
            }
            familyDTOToFamily1( dto.getFamily(), entity.getFamily() );
        }
    }

    @Override
    public ChildDTO toDto(Child s) {
        if ( s == null ) {
            return null;
        }

        ChildDTO childDTO = new ChildDTO();

        childDTO.setFamily( toDtoFamilyId( s.getFamily() ) );
        childDTO.setId( s.getId() );
        childDTO.setAuthorizingOfficer( authorizingOfficerToProfileDTO( s.getAuthorizingOfficer() ) );
        childDTO.setTutor( tutorToProfileDTO( s.getTutor() ) );

        return childDTO;
    }

    @Override
    public FamilyDTO toDtoFamilyId(Family family) {
        if ( family == null ) {
            return null;
        }

        FamilyDTO familyDTO = new FamilyDTO();

        familyDTO.setId( family.getId() );

        return familyDTO;
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

    protected ProfileDTO authorizingOfficerToProfileDTO(AuthorizingOfficer authorizingOfficer) {
        if ( authorizingOfficer == null ) {
            return null;
        }

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setId( authorizingOfficer.getId() );
        profileDTO.setActivity( authorizingOfficer.getActivity() );

        return profileDTO;
    }

    protected ProfileDTO tutorToProfileDTO(Tutor tutor) {
        if ( tutor == null ) {
            return null;
        }

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setId( tutor.getId() );
        profileDTO.setActivity( tutor.getActivity() );

        return profileDTO;
    }
}
