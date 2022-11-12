package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.Family;
import com.awtar.myapp.domain.Parent;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.FamilyDTO;
import com.awtar.myapp.service.dto.ParentDTO;
import com.awtar.myapp.service.dto.ProfileDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-12T16:07:06+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20221012-0724, environment: Java 17.0.4.1 (Eclipse Adoptium)"
)
@Component
public class ParentMapperImpl implements ParentMapper {

    @Override
    public Parent toEntity(ParentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Parent parent = new Parent();

        parent.setId( dto.getId() );
        parent.setAnnualRevenue( dto.getAnnualRevenue() );
        parent.setCnss( dto.getCnss() );
        parent.setMaritalStatus( dto.getMaritalStatus() );
        parent.setOccupation( dto.getOccupation() );
        parent.setDeceased( dto.getDeceased() );
        parent.setDateOfDeath( dto.getDateOfDeath() );
        parent.familyHead( familyDTOToFamily( dto.getFamilyHead() ) );
        parent.family( familyDTOToFamily( dto.getFamily() ) );

        return parent;
    }

    @Override
    public List<Parent> toEntity(List<ParentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Parent> list = new ArrayList<Parent>( dtoList.size() );
        for ( ParentDTO parentDTO : dtoList ) {
            list.add( toEntity( parentDTO ) );
        }

        return list;
    }

    @Override
    public List<ParentDTO> toDto(List<Parent> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ParentDTO> list = new ArrayList<ParentDTO>( entityList.size() );
        for ( Parent parent : entityList ) {
            list.add( toDto( parent ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Parent entity, ParentDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getAnnualRevenue() != null ) {
            entity.setAnnualRevenue( dto.getAnnualRevenue() );
        }
        if ( dto.getCnss() != null ) {
            entity.setCnss( dto.getCnss() );
        }
        if ( dto.getMaritalStatus() != null ) {
            entity.setMaritalStatus( dto.getMaritalStatus() );
        }
        if ( dto.getOccupation() != null ) {
            entity.setOccupation( dto.getOccupation() );
        }
        if ( dto.getDeceased() != null ) {
            entity.setDeceased( dto.getDeceased() );
        }
        if ( dto.getDateOfDeath() != null ) {
            entity.setDateOfDeath( dto.getDateOfDeath() );
        }
        if ( dto.getFamilyHead() != null ) {
            if ( entity.getFamilyHead() == null ) {
                entity.familyHead( new Family() );
            }
            familyDTOToFamily1( dto.getFamilyHead(), entity.getFamilyHead() );
        }
        if ( dto.getFamily() != null ) {
            if ( entity.getFamily() == null ) {
                entity.family( new Family() );
            }
            familyDTOToFamily1( dto.getFamily(), entity.getFamily() );
        }
    }

    @Override
    public ParentDTO toDto(Parent s) {
        if ( s == null ) {
            return null;
        }

        ParentDTO parentDTO = new ParentDTO();

        parentDTO.setFamilyHead( toDtoFamilyId( s.getFamilyHead() ) );
        parentDTO.setFamily( toDtoFamilyId( s.getFamily() ) );
        parentDTO.setId( s.getId() );
        parentDTO.setAnnualRevenue( s.getAnnualRevenue() );
        parentDTO.setCnss( s.getCnss() );
        parentDTO.setMaritalStatus( s.getMaritalStatus() );
        parentDTO.setOccupation( s.getOccupation() );
        parentDTO.setDeceased( s.getDeceased() );
        parentDTO.setDateOfDeath( s.getDateOfDeath() );

        return parentDTO;
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
}
