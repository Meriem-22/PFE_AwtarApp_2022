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
    date = "2022-11-10T17:58:42+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20221012-0724, environment: Java 17.0.4.1 (Eclipse Adoptium)"
)
@Component
public class ChildMapperImpl implements ChildMapper {

    @Override
    public void partialUpdate(Child arg0, ChildDTO arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getAuthorizingOfficer() != null ) {
            if ( arg0.getAuthorizingOfficer() == null ) {
                arg0.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            profileDTOToAuthorizingOfficer( arg1.getAuthorizingOfficer(), arg0.getAuthorizingOfficer() );
        }
        if ( arg1.getTutor() != null ) {
            if ( arg0.getTutor() == null ) {
                arg0.setTutor( new Tutor() );
            }
            profileDTOToTutor( arg1.getTutor(), arg0.getTutor() );
        }
        if ( arg1.getFamily() != null ) {
            if ( arg0.getFamily() == null ) {
                arg0.setFamily( new Family() );
            }
            familyDTOToFamily( arg1.getFamily(), arg0.getFamily() );
        }
        if ( arg1.getId() != null ) {
            arg0.setId( arg1.getId() );
        }
    }

    @Override
    public List<ChildDTO> toDto(List<Child> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<ChildDTO> list = new ArrayList<ChildDTO>( arg0.size() );
        for ( Child child : arg0 ) {
            list.add( toDto( child ) );
        }

        return list;
    }

    @Override
    public Child toEntity(ChildDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Child child = new Child();

        child.setAuthorizingOfficer( profileDTOToAuthorizingOfficer1( arg0.getAuthorizingOfficer() ) );
        child.setTutor( profileDTOToTutor1( arg0.getTutor() ) );
        child.setFamily( familyDTOToFamily1( arg0.getFamily() ) );
        child.setId( arg0.getId() );

        return child;
    }

    @Override
    public List<Child> toEntity(List<ChildDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Child> list = new ArrayList<Child>( arg0.size() );
        for ( ChildDTO childDTO : arg0 ) {
            list.add( toEntity( childDTO ) );
        }

        return list;
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

    protected void profileDTOToAuthorizingOfficer(ProfileDTO profileDTO, AuthorizingOfficer mappingTarget) {
        if ( profileDTO == null ) {
            return;
        }

        if ( profileDTO.getActivity() != null ) {
            mappingTarget.setActivity( profileDTO.getActivity() );
        }
        if ( profileDTO.getId() != null ) {
            mappingTarget.setId( profileDTO.getId() );
        }
    }

    protected void profileDTOToTutor(ProfileDTO profileDTO, Tutor mappingTarget) {
        if ( profileDTO == null ) {
            return;
        }

        if ( profileDTO.getActivity() != null ) {
            mappingTarget.setActivity( profileDTO.getActivity() );
        }
        if ( profileDTO.getId() != null ) {
            mappingTarget.setId( profileDTO.getId() );
        }
    }

    protected void familyDTOToFamily(FamilyDTO familyDTO, Family mappingTarget) {
        if ( familyDTO == null ) {
            return;
        }

        if ( familyDTO.getAuthorizingOfficer() != null ) {
            if ( mappingTarget.getAuthorizingOfficer() == null ) {
                mappingTarget.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            profileDTOToAuthorizingOfficer( familyDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
        if ( familyDTO.getTutor() != null ) {
            if ( mappingTarget.getTutor() == null ) {
                mappingTarget.setTutor( new Tutor() );
            }
            profileDTOToTutor( familyDTO.getTutor(), mappingTarget.getTutor() );
        }
        if ( familyDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( familyDTO.getArchivated() );
        }
        if ( familyDTO.getArea() != null ) {
            mappingTarget.setArea( familyDTO.getArea() );
        }
        if ( familyDTO.getDwelling() != null ) {
            mappingTarget.setDwelling( familyDTO.getDwelling() );
        }
        if ( familyDTO.getFamilyName() != null ) {
            mappingTarget.setFamilyName( familyDTO.getFamilyName() );
        }
        if ( familyDTO.getId() != null ) {
            mappingTarget.setId( familyDTO.getId() );
        }
        if ( familyDTO.getNotebookOfHandicap() != null ) {
            mappingTarget.setNotebookOfHandicap( familyDTO.getNotebookOfHandicap() );
        }
        if ( familyDTO.getNotebookOfPoverty() != null ) {
            mappingTarget.setNotebookOfPoverty( familyDTO.getNotebookOfPoverty() );
        }
    }

    protected AuthorizingOfficer profileDTOToAuthorizingOfficer1(ProfileDTO profileDTO) {
        if ( profileDTO == null ) {
            return null;
        }

        AuthorizingOfficer authorizingOfficer = new AuthorizingOfficer();

        authorizingOfficer.setActivity( profileDTO.getActivity() );
        authorizingOfficer.setId( profileDTO.getId() );

        return authorizingOfficer;
    }

    protected Tutor profileDTOToTutor1(ProfileDTO profileDTO) {
        if ( profileDTO == null ) {
            return null;
        }

        Tutor tutor = new Tutor();

        tutor.setActivity( profileDTO.getActivity() );
        tutor.setId( profileDTO.getId() );

        return tutor;
    }

    protected Family familyDTOToFamily1(FamilyDTO familyDTO) {
        if ( familyDTO == null ) {
            return null;
        }

        Family family = new Family();

        family.setAuthorizingOfficer( profileDTOToAuthorizingOfficer1( familyDTO.getAuthorizingOfficer() ) );
        family.setTutor( profileDTOToTutor1( familyDTO.getTutor() ) );
        family.setArchivated( familyDTO.getArchivated() );
        family.setArea( familyDTO.getArea() );
        family.setDwelling( familyDTO.getDwelling() );
        family.setFamilyName( familyDTO.getFamilyName() );
        family.setId( familyDTO.getId() );
        family.setNotebookOfHandicap( familyDTO.getNotebookOfHandicap() );
        family.setNotebookOfPoverty( familyDTO.getNotebookOfPoverty() );

        return family;
    }

    protected ProfileDTO authorizingOfficerToProfileDTO(AuthorizingOfficer authorizingOfficer) {
        if ( authorizingOfficer == null ) {
            return null;
        }

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setActivity( authorizingOfficer.getActivity() );
        profileDTO.setId( authorizingOfficer.getId() );

        return profileDTO;
    }

    protected ProfileDTO tutorToProfileDTO(Tutor tutor) {
        if ( tutor == null ) {
            return null;
        }

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setActivity( tutor.getActivity() );
        profileDTO.setId( tutor.getId() );

        return profileDTO;
    }
}
