package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.Family;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.FamilyDTO;
import com.awtar.myapp.service.dto.ProfileDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-21T08:01:12+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class FamilyMapperImpl implements FamilyMapper {

    @Override
    public Family toEntity(FamilyDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Family family = new Family();

        family.setAuthorizingOfficer( profileDTOToAuthorizingOfficer( dto.getAuthorizingOfficer() ) );
        family.setTutor( profileDTOToTutor( dto.getTutor() ) );
        family.setId( dto.getId() );
        family.setFamilyName( dto.getFamilyName() );
        family.setDwelling( dto.getDwelling() );
        family.setArea( dto.getArea() );
        family.setNotebookOfPoverty( dto.getNotebookOfPoverty() );
        family.setNotebookOfHandicap( dto.getNotebookOfHandicap() );
        family.setArchivated( dto.getArchivated() );

        return family;
    }

    @Override
    public FamilyDTO toDto(Family entity) {
        if ( entity == null ) {
            return null;
        }

        FamilyDTO familyDTO = new FamilyDTO();

        familyDTO.setId( entity.getId() );
        familyDTO.setFamilyName( entity.getFamilyName() );
        familyDTO.setDwelling( entity.getDwelling() );
        familyDTO.setArea( entity.getArea() );
        familyDTO.setNotebookOfPoverty( entity.getNotebookOfPoverty() );
        familyDTO.setNotebookOfHandicap( entity.getNotebookOfHandicap() );
        familyDTO.setArchivated( entity.getArchivated() );
        familyDTO.setAuthorizingOfficer( authorizingOfficerToProfileDTO( entity.getAuthorizingOfficer() ) );
        familyDTO.setTutor( tutorToProfileDTO( entity.getTutor() ) );

        return familyDTO;
    }

    @Override
    public List<Family> toEntity(List<FamilyDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Family> list = new ArrayList<Family>( dtoList.size() );
        for ( FamilyDTO familyDTO : dtoList ) {
            list.add( toEntity( familyDTO ) );
        }

        return list;
    }

    @Override
    public List<FamilyDTO> toDto(List<Family> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FamilyDTO> list = new ArrayList<FamilyDTO>( entityList.size() );
        for ( Family family : entityList ) {
            list.add( toDto( family ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Family entity, FamilyDTO dto) {
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
        if ( dto.getFamilyName() != null ) {
            entity.setFamilyName( dto.getFamilyName() );
        }
        if ( dto.getDwelling() != null ) {
            entity.setDwelling( dto.getDwelling() );
        }
        if ( dto.getArea() != null ) {
            entity.setArea( dto.getArea() );
        }
        if ( dto.getNotebookOfPoverty() != null ) {
            entity.setNotebookOfPoverty( dto.getNotebookOfPoverty() );
        }
        if ( dto.getNotebookOfHandicap() != null ) {
            entity.setNotebookOfHandicap( dto.getNotebookOfHandicap() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
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
}
