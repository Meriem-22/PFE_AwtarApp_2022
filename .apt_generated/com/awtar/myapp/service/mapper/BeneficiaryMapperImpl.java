package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.Beneficiary;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import com.awtar.myapp.service.dto.BeneficiaryDTO;
import com.awtar.myapp.service.dto.TutorDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-26T08:24:59+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class BeneficiaryMapperImpl implements BeneficiaryMapper {

    @Override
    public Beneficiary toEntity(BeneficiaryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Beneficiary beneficiary = new Beneficiary();

        beneficiary.setId( dto.getId() );
        beneficiary.setBeneficiaryReference( dto.getBeneficiaryReference() );
        beneficiary.setBeneficiaryType( dto.getBeneficiaryType() );
        beneficiary.setArchivated( dto.getArchivated() );
        beneficiary.authorizingOfficer( authorizingOfficerDTOToAuthorizingOfficer( dto.getAuthorizingOfficer() ) );
        beneficiary.tutor( tutorDTOToTutor( dto.getTutor() ) );

        return beneficiary;
    }

    @Override
    public List<Beneficiary> toEntity(List<BeneficiaryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Beneficiary> list = new ArrayList<Beneficiary>( dtoList.size() );
        for ( BeneficiaryDTO beneficiaryDTO : dtoList ) {
            list.add( toEntity( beneficiaryDTO ) );
        }

        return list;
    }

    @Override
    public List<BeneficiaryDTO> toDto(List<Beneficiary> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BeneficiaryDTO> list = new ArrayList<BeneficiaryDTO>( entityList.size() );
        for ( Beneficiary beneficiary : entityList ) {
            list.add( toDto( beneficiary ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Beneficiary entity, BeneficiaryDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getBeneficiaryReference() != null ) {
            entity.setBeneficiaryReference( dto.getBeneficiaryReference() );
        }
        if ( dto.getBeneficiaryType() != null ) {
            entity.setBeneficiaryType( dto.getBeneficiaryType() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
        if ( dto.getAuthorizingOfficer() != null ) {
            if ( entity.getAuthorizingOfficer() == null ) {
                entity.authorizingOfficer( new AuthorizingOfficer() );
            }
            authorizingOfficerDTOToAuthorizingOfficer1( dto.getAuthorizingOfficer(), entity.getAuthorizingOfficer() );
        }
        if ( dto.getTutor() != null ) {
            if ( entity.getTutor() == null ) {
                entity.tutor( new Tutor() );
            }
            tutorDTOToTutor1( dto.getTutor(), entity.getTutor() );
        }
    }

    @Override
    public BeneficiaryDTO toDto(Beneficiary s) {
        if ( s == null ) {
            return null;
        }

        BeneficiaryDTO beneficiaryDTO = new BeneficiaryDTO();

        beneficiaryDTO.setAuthorizingOfficer( toDtoAuthorizingOfficerAbbreviation( s.getAuthorizingOfficer() ) );
        beneficiaryDTO.setTutor( toDtoTutorId( s.getTutor() ) );
        beneficiaryDTO.setId( s.getId() );
        beneficiaryDTO.setBeneficiaryReference( s.getBeneficiaryReference() );
        beneficiaryDTO.setBeneficiaryType( s.getBeneficiaryType() );
        beneficiaryDTO.setArchivated( s.getArchivated() );

        return beneficiaryDTO;
    }

    @Override
    public AuthorizingOfficerDTO toDtoAuthorizingOfficerAbbreviation(AuthorizingOfficer authorizingOfficer) {
        if ( authorizingOfficer == null ) {
            return null;
        }

        AuthorizingOfficerDTO authorizingOfficerDTO = new AuthorizingOfficerDTO();

        authorizingOfficerDTO.setId( authorizingOfficer.getId() );
        authorizingOfficerDTO.setAbbreviation( authorizingOfficer.getAbbreviation() );

        return authorizingOfficerDTO;
    }

    @Override
    public TutorDTO toDtoTutorId(Tutor tutor) {
        if ( tutor == null ) {
            return null;
        }

        TutorDTO tutorDTO = new TutorDTO();

        tutorDTO.setId( tutor.getId() );

        return tutorDTO;
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
}
