package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.Beneficiary;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.domain.Visit;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import com.awtar.myapp.service.dto.BeneficiaryDTO;
import com.awtar.myapp.service.dto.TutorDTO;
import com.awtar.myapp.service.dto.VisitDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-17T14:57:35+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.3 (Eclipse Adoptium)"
)
@Component
public class VisitMapperImpl implements VisitMapper {

    @Override
    public Visit toEntity(VisitDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Visit visit = new Visit();

        visit.setId( dto.getId() );
        visit.setVisitDate( dto.getVisitDate() );
        visit.setRealizedBy( dto.getRealizedBy() );
        visit.setDescription( dto.getDescription() );
        byte[] attachedFile = dto.getAttachedFile();
        if ( attachedFile != null ) {
            visit.setAttachedFile( Arrays.copyOf( attachedFile, attachedFile.length ) );
        }
        visit.setAttachedFileContentType( dto.getAttachedFileContentType() );
        visit.setArchivated( dto.getArchivated() );
        visit.beneficiary( beneficiaryDTOToBeneficiary( dto.getBeneficiary() ) );

        return visit;
    }

    @Override
    public List<Visit> toEntity(List<VisitDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Visit> list = new ArrayList<Visit>( dtoList.size() );
        for ( VisitDTO visitDTO : dtoList ) {
            list.add( toEntity( visitDTO ) );
        }

        return list;
    }

    @Override
    public List<VisitDTO> toDto(List<Visit> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<VisitDTO> list = new ArrayList<VisitDTO>( entityList.size() );
        for ( Visit visit : entityList ) {
            list.add( toDto( visit ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Visit entity, VisitDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getVisitDate() != null ) {
            entity.setVisitDate( dto.getVisitDate() );
        }
        if ( dto.getRealizedBy() != null ) {
            entity.setRealizedBy( dto.getRealizedBy() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
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
        if ( dto.getBeneficiary() != null ) {
            if ( entity.getBeneficiary() == null ) {
                entity.beneficiary( new Beneficiary() );
            }
            beneficiaryDTOToBeneficiary1( dto.getBeneficiary(), entity.getBeneficiary() );
        }
    }

    @Override
    public VisitDTO toDto(Visit s) {
        if ( s == null ) {
            return null;
        }

        VisitDTO visitDTO = new VisitDTO();

        visitDTO.setBeneficiary( toDtoBeneficiaryBeneficiaryReference( s.getBeneficiary() ) );
        visitDTO.setId( s.getId() );
        visitDTO.setVisitDate( s.getVisitDate() );
        visitDTO.setRealizedBy( s.getRealizedBy() );
        visitDTO.setDescription( s.getDescription() );
        byte[] attachedFile = s.getAttachedFile();
        if ( attachedFile != null ) {
            visitDTO.setAttachedFile( Arrays.copyOf( attachedFile, attachedFile.length ) );
        }
        visitDTO.setAttachedFileContentType( s.getAttachedFileContentType() );
        visitDTO.setArchivated( s.getArchivated() );

        return visitDTO;
    }

    @Override
    public BeneficiaryDTO toDtoBeneficiaryBeneficiaryReference(Beneficiary beneficiary) {
        if ( beneficiary == null ) {
            return null;
        }

        BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();

        beneficiaryDTO.setId( beneficiary.getId() );
        beneficiaryDTO.setBeneficiaryReference( beneficiary.getBeneficiaryReference() );

        return beneficiaryDTO;
    }

    protected AuthorizingOfficer authorizingOfficerDTOToAuthorizingOfficer(AuthorizingOfficerDTO authorizingOfficerDTO) {
        if ( authorizingOfficerDTO == null ) {
            return null;
        }

        AuthorizingOfficer authorizingOfficer = new AuthorizingOfficer();

        authorizingOfficer.setId( authorizingOfficerDTO.getId() );
        authorizingOfficer.setAbbreviation( authorizingOfficerDTO.getAbbreviation() );
        authorizingOfficer.setActivity( authorizingOfficerDTO.getActivity() );
        authorizingOfficer.setManager( authorizingOfficerDTO.getManager() );
        authorizingOfficer.setManagerCin( authorizingOfficerDTO.getManagerCin() );

        return authorizingOfficer;
    }

    protected Tutor tutorDTOToTutor(TutorDTO tutorDTO) {
        if ( tutorDTO == null ) {
            return null;
        }

        Tutor tutor = new Tutor();

        tutor.setId( tutorDTO.getId() );
        tutor.setActivity( tutorDTO.getActivity() );
        tutor.setManager( tutorDTO.getManager() );
        tutor.setManagerCin( tutorDTO.getManagerCin() );

        return tutor;
    }

    protected Beneficiary beneficiaryDTOToBeneficiary(BeneficiaryDTO beneficiaryDTO) {
        if ( beneficiaryDTO == null ) {
            return null;
        }

        Beneficiary beneficiary = new Beneficiary();

        beneficiary.setId( beneficiaryDTO.getId() );
        beneficiary.setBeneficiaryReference( beneficiaryDTO.getBeneficiaryReference() );
        beneficiary.setBeneficiaryType( beneficiaryDTO.getBeneficiaryType() );
        beneficiary.setArchivated( beneficiaryDTO.getArchivated() );
        beneficiary.authorizingOfficer( authorizingOfficerDTOToAuthorizingOfficer( beneficiaryDTO.getAuthorizingOfficer() ) );
        beneficiary.tutor( tutorDTOToTutor( beneficiaryDTO.getTutor() ) );

        return beneficiary;
    }

    protected void authorizingOfficerDTOToAuthorizingOfficer1(AuthorizingOfficerDTO authorizingOfficerDTO, AuthorizingOfficer mappingTarget) {
        if ( authorizingOfficerDTO == null ) {
            return;
        }

        if ( authorizingOfficerDTO.getId() != null ) {
            mappingTarget.setId( authorizingOfficerDTO.getId() );
        }
        if ( authorizingOfficerDTO.getAbbreviation() != null ) {
            mappingTarget.setAbbreviation( authorizingOfficerDTO.getAbbreviation() );
        }
        if ( authorizingOfficerDTO.getActivity() != null ) {
            mappingTarget.setActivity( authorizingOfficerDTO.getActivity() );
        }
        if ( authorizingOfficerDTO.getManager() != null ) {
            mappingTarget.setManager( authorizingOfficerDTO.getManager() );
        }
        if ( authorizingOfficerDTO.getManagerCin() != null ) {
            mappingTarget.setManagerCin( authorizingOfficerDTO.getManagerCin() );
        }
    }

    protected void tutorDTOToTutor1(TutorDTO tutorDTO, Tutor mappingTarget) {
        if ( tutorDTO == null ) {
            return;
        }

        if ( tutorDTO.getId() != null ) {
            mappingTarget.setId( tutorDTO.getId() );
        }
        if ( tutorDTO.getActivity() != null ) {
            mappingTarget.setActivity( tutorDTO.getActivity() );
        }
        if ( tutorDTO.getManager() != null ) {
            mappingTarget.setManager( tutorDTO.getManager() );
        }
        if ( tutorDTO.getManagerCin() != null ) {
            mappingTarget.setManagerCin( tutorDTO.getManagerCin() );
        }
    }

    protected void beneficiaryDTOToBeneficiary1(BeneficiaryDTO beneficiaryDTO, Beneficiary mappingTarget) {
        if ( beneficiaryDTO == null ) {
            return;
        }

        if ( beneficiaryDTO.getId() != null ) {
            mappingTarget.setId( beneficiaryDTO.getId() );
        }
        if ( beneficiaryDTO.getBeneficiaryReference() != null ) {
            mappingTarget.setBeneficiaryReference( beneficiaryDTO.getBeneficiaryReference() );
        }
        if ( beneficiaryDTO.getBeneficiaryType() != null ) {
            mappingTarget.setBeneficiaryType( beneficiaryDTO.getBeneficiaryType() );
        }
        if ( beneficiaryDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( beneficiaryDTO.getArchivated() );
        }
        if ( beneficiaryDTO.getAuthorizingOfficer() != null ) {
            if ( mappingTarget.getAuthorizingOfficer() == null ) {
                mappingTarget.authorizingOfficer( new AuthorizingOfficer() );
            }
            authorizingOfficerDTOToAuthorizingOfficer1( beneficiaryDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
        if ( beneficiaryDTO.getTutor() != null ) {
            if ( mappingTarget.getTutor() == null ) {
                mappingTarget.tutor( new Tutor() );
            }
            tutorDTOToTutor1( beneficiaryDTO.getTutor(), mappingTarget.getTutor() );
        }
    }
}
