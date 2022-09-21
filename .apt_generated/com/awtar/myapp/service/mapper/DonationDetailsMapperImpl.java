package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.Beneficiary;
import com.awtar.myapp.domain.DonationDetails;
import com.awtar.myapp.domain.DonationsIssued;
import com.awtar.myapp.domain.Nature;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import com.awtar.myapp.service.dto.BeneficiaryDTO;
import com.awtar.myapp.service.dto.DonationDetailsDTO;
import com.awtar.myapp.service.dto.DonationsIssuedDTO;
import com.awtar.myapp.service.dto.NatureDTO;
import com.awtar.myapp.service.dto.TutorDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-21T08:01:11+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class DonationDetailsMapperImpl implements DonationDetailsMapper {

    @Override
    public List<DonationDetails> toEntity(List<DonationDetailsDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DonationDetails> list = new ArrayList<DonationDetails>( dtoList.size() );
        for ( DonationDetailsDTO donationDetailsDTO : dtoList ) {
            list.add( toEntity( donationDetailsDTO ) );
        }

        return list;
    }

    @Override
    public List<DonationDetailsDTO> toDto(List<DonationDetails> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DonationDetailsDTO> list = new ArrayList<DonationDetailsDTO>( entityList.size() );
        for ( DonationDetails donationDetails : entityList ) {
            list.add( toDto( donationDetails ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(DonationDetails entity, DonationDetailsDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
        if ( dto.getDonationsIssued() != null ) {
            if ( entity.getDonationsIssued() == null ) {
                entity.donationsIssued( new DonationsIssued() );
            }
            donationsIssuedDTOToDonationsIssued( dto.getDonationsIssued(), entity.getDonationsIssued() );
        }
        if ( dto.getNature() != null ) {
            if ( entity.getNature() == null ) {
                entity.nature( new Nature() );
            }
            natureDTOToNature( dto.getNature(), entity.getNature() );
        }
        if ( dto.getBeneficiary() != null ) {
            if ( entity.getBeneficiary() == null ) {
                entity.beneficiary( new Beneficiary() );
            }
            beneficiaryDTOToBeneficiary( dto.getBeneficiary(), entity.getBeneficiary() );
        }
    }

    @Override
    public DonationDetailsDTO toDto(DonationDetails s) {
        if ( s == null ) {
            return null;
        }

        DonationDetailsDTO donationDetailsDTO = new DonationDetailsDTO();

        donationDetailsDTO.setDonationsIssued( toDtoDonationsIssuedId( s.getDonationsIssued() ) );
        donationDetailsDTO.setNature( toDtoNatureName( s.getNature() ) );
        donationDetailsDTO.setBeneficiary( toDtoBeneficiaryBeneficiaryReference( s.getBeneficiary() ) );
        donationDetailsDTO.setId( s.getId() );
        donationDetailsDTO.setDescription( s.getDescription() );
        donationDetailsDTO.setArchivated( s.getArchivated() );

        return donationDetailsDTO;
    }

    @Override
    public DonationsIssuedDTO toDtoDonationsIssuedId(DonationsIssued donationsIssued) {
        if ( donationsIssued == null ) {
            return null;
        }

        DonationsIssuedDTO donationsIssuedDTO = new DonationsIssuedDTO();

        donationsIssuedDTO.setId( donationsIssued.getId() );

        return donationsIssuedDTO;
    }

    @Override
    public NatureDTO toDtoNatureName(Nature nature) {
        if ( nature == null ) {
            return null;
        }

        NatureDTO natureDTO = new NatureDTO();

        natureDTO.setId( nature.getId() );
        natureDTO.setName( nature.getName() );

        return natureDTO;
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

    @Override
    public DonationDetails toEntity(DonationDetailsDTO donationDetailsDTO) {
        if ( donationDetailsDTO == null ) {
            return null;
        }

        DonationDetails donationDetails = new DonationDetails();

        donationDetails.setId( donationDetailsDTO.getId() );
        donationDetails.setDescription( donationDetailsDTO.getDescription() );
        donationDetails.setArchivated( donationDetailsDTO.getArchivated() );
        donationDetails.donationsIssued( donationsIssuedDTOToDonationsIssued1( donationDetailsDTO.getDonationsIssued() ) );
        donationDetails.nature( natureDTOToNature1( donationDetailsDTO.getNature() ) );
        donationDetails.beneficiary( beneficiaryDTOToBeneficiary1( donationDetailsDTO.getBeneficiary() ) );

        return donationDetails;
    }

    protected void donationsIssuedDTOToDonationsIssued(DonationsIssuedDTO donationsIssuedDTO, DonationsIssued mappingTarget) {
        if ( donationsIssuedDTO == null ) {
            return;
        }

        if ( donationsIssuedDTO.getId() != null ) {
            mappingTarget.setId( donationsIssuedDTO.getId() );
        }
        if ( donationsIssuedDTO.getModel() != null ) {
            mappingTarget.setModel( donationsIssuedDTO.getModel() );
        }
        if ( donationsIssuedDTO.getIsValidated() != null ) {
            mappingTarget.setIsValidated( donationsIssuedDTO.getIsValidated() );
        }
        if ( donationsIssuedDTO.getValidationDate() != null ) {
            mappingTarget.setValidationDate( donationsIssuedDTO.getValidationDate() );
        }
        if ( donationsIssuedDTO.getValidationUser() != null ) {
            mappingTarget.setValidationUser( donationsIssuedDTO.getValidationUser() );
        }
        if ( donationsIssuedDTO.getDonationsDate() != null ) {
            mappingTarget.setDonationsDate( donationsIssuedDTO.getDonationsDate() );
        }
        if ( donationsIssuedDTO.getCanceledDonations() != null ) {
            mappingTarget.setCanceledDonations( donationsIssuedDTO.getCanceledDonations() );
        }
        if ( donationsIssuedDTO.getCanceledOn() != null ) {
            mappingTarget.setCanceledOn( donationsIssuedDTO.getCanceledOn() );
        }
        if ( donationsIssuedDTO.getCanceledBy() != null ) {
            mappingTarget.setCanceledBy( donationsIssuedDTO.getCanceledBy() );
        }
        if ( donationsIssuedDTO.getCancellationReason() != null ) {
            mappingTarget.setCancellationReason( donationsIssuedDTO.getCancellationReason() );
        }
        if ( donationsIssuedDTO.getRecurringDonations() != null ) {
            mappingTarget.setRecurringDonations( donationsIssuedDTO.getRecurringDonations() );
        }
        if ( donationsIssuedDTO.getPeriodicity() != null ) {
            mappingTarget.setPeriodicity( donationsIssuedDTO.getPeriodicity() );
        }
        if ( donationsIssuedDTO.getRecurrence() != null ) {
            mappingTarget.setRecurrence( donationsIssuedDTO.getRecurrence() );
        }
        if ( donationsIssuedDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( donationsIssuedDTO.getArchivated() );
        }
    }

    protected void natureDTOToNature(NatureDTO natureDTO, Nature mappingTarget) {
        if ( natureDTO == null ) {
            return;
        }

        if ( natureDTO.getId() != null ) {
            mappingTarget.setId( natureDTO.getId() );
        }
        if ( natureDTO.getName() != null ) {
            mappingTarget.setName( natureDTO.getName() );
        }
        if ( natureDTO.getDestinedTo() != null ) {
            mappingTarget.setDestinedTo( natureDTO.getDestinedTo() );
        }
        if ( natureDTO.getNecessityValue() != null ) {
            mappingTarget.setNecessityValue( natureDTO.getNecessityValue() );
        }
        if ( natureDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( natureDTO.getArchivated() );
        }
    }

    protected void authorizingOfficerDTOToAuthorizingOfficer(AuthorizingOfficerDTO authorizingOfficerDTO, AuthorizingOfficer mappingTarget) {
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

    protected void tutorDTOToTutor(TutorDTO tutorDTO, Tutor mappingTarget) {
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

    protected void beneficiaryDTOToBeneficiary(BeneficiaryDTO beneficiaryDTO, Beneficiary mappingTarget) {
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
            authorizingOfficerDTOToAuthorizingOfficer( beneficiaryDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
        if ( beneficiaryDTO.getTutor() != null ) {
            if ( mappingTarget.getTutor() == null ) {
                mappingTarget.tutor( new Tutor() );
            }
            tutorDTOToTutor( beneficiaryDTO.getTutor(), mappingTarget.getTutor() );
        }
    }

    protected DonationsIssued donationsIssuedDTOToDonationsIssued1(DonationsIssuedDTO donationsIssuedDTO) {
        if ( donationsIssuedDTO == null ) {
            return null;
        }

        DonationsIssued donationsIssued = new DonationsIssued();

        donationsIssued.setId( donationsIssuedDTO.getId() );
        donationsIssued.setModel( donationsIssuedDTO.getModel() );
        donationsIssued.setIsValidated( donationsIssuedDTO.getIsValidated() );
        donationsIssued.setValidationDate( donationsIssuedDTO.getValidationDate() );
        donationsIssued.setValidationUser( donationsIssuedDTO.getValidationUser() );
        donationsIssued.setDonationsDate( donationsIssuedDTO.getDonationsDate() );
        donationsIssued.setCanceledDonations( donationsIssuedDTO.getCanceledDonations() );
        donationsIssued.setCanceledOn( donationsIssuedDTO.getCanceledOn() );
        donationsIssued.setCanceledBy( donationsIssuedDTO.getCanceledBy() );
        donationsIssued.setCancellationReason( donationsIssuedDTO.getCancellationReason() );
        donationsIssued.setRecurringDonations( donationsIssuedDTO.getRecurringDonations() );
        donationsIssued.setPeriodicity( donationsIssuedDTO.getPeriodicity() );
        donationsIssued.setRecurrence( donationsIssuedDTO.getRecurrence() );
        donationsIssued.setArchivated( donationsIssuedDTO.getArchivated() );

        return donationsIssued;
    }

    protected Nature natureDTOToNature1(NatureDTO natureDTO) {
        if ( natureDTO == null ) {
            return null;
        }

        Nature nature = new Nature();

        nature.setId( natureDTO.getId() );
        nature.setName( natureDTO.getName() );
        nature.setDestinedTo( natureDTO.getDestinedTo() );
        nature.setNecessityValue( natureDTO.getNecessityValue() );
        nature.setArchivated( natureDTO.getArchivated() );

        return nature;
    }

    protected AuthorizingOfficer authorizingOfficerDTOToAuthorizingOfficer1(AuthorizingOfficerDTO authorizingOfficerDTO) {
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

    protected Tutor tutorDTOToTutor1(TutorDTO tutorDTO) {
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

    protected Beneficiary beneficiaryDTOToBeneficiary1(BeneficiaryDTO beneficiaryDTO) {
        if ( beneficiaryDTO == null ) {
            return null;
        }

        Beneficiary beneficiary = new Beneficiary();

        beneficiary.setId( beneficiaryDTO.getId() );
        beneficiary.setBeneficiaryReference( beneficiaryDTO.getBeneficiaryReference() );
        beneficiary.setBeneficiaryType( beneficiaryDTO.getBeneficiaryType() );
        beneficiary.setArchivated( beneficiaryDTO.getArchivated() );
        beneficiary.authorizingOfficer( authorizingOfficerDTOToAuthorizingOfficer1( beneficiaryDTO.getAuthorizingOfficer() ) );
        beneficiary.tutor( tutorDTOToTutor1( beneficiaryDTO.getTutor() ) );

        return beneficiary;
    }
}
