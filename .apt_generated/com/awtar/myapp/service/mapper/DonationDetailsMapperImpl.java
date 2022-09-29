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
    date = "2022-09-29T21:08:48+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class DonationDetailsMapperImpl implements DonationDetailsMapper {

    @Override
    public void partialUpdate(DonationDetails arg0, DonationDetailsDTO arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getArchivated() != null ) {
            arg0.setArchivated( arg1.getArchivated() );
        }
        if ( arg1.getBeneficiary() != null ) {
            if ( arg0.getBeneficiary() == null ) {
                arg0.setBeneficiary( new Beneficiary() );
            }
            beneficiaryDTOToBeneficiary( arg1.getBeneficiary(), arg0.getBeneficiary() );
        }
        if ( arg1.getDescription() != null ) {
            arg0.setDescription( arg1.getDescription() );
        }
        if ( arg1.getDonationsIssued() != null ) {
            if ( arg0.getDonationsIssued() == null ) {
                arg0.setDonationsIssued( new DonationsIssued() );
            }
            donationsIssuedDTOToDonationsIssued( arg1.getDonationsIssued(), arg0.getDonationsIssued() );
        }
        if ( arg1.getId() != null ) {
            arg0.setId( arg1.getId() );
        }
        if ( arg1.getNature() != null ) {
            if ( arg0.getNature() == null ) {
                arg0.setNature( new Nature() );
            }
            natureDTOToNature( arg1.getNature(), arg0.getNature() );
        }
    }

    @Override
    public List<DonationDetailsDTO> toDto(List<DonationDetails> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<DonationDetailsDTO> list = new ArrayList<DonationDetailsDTO>( arg0.size() );
        for ( DonationDetails donationDetails : arg0 ) {
            list.add( toDto( donationDetails ) );
        }

        return list;
    }

    @Override
    public List<DonationDetails> toEntity(List<DonationDetailsDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<DonationDetails> list = new ArrayList<DonationDetails>( arg0.size() );
        for ( DonationDetailsDTO donationDetailsDTO : arg0 ) {
            list.add( toEntity( donationDetailsDTO ) );
        }

        return list;
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
        donationDetailsDTO.setArchivated( s.getArchivated() );
        donationDetailsDTO.setDescription( s.getDescription() );
        donationDetailsDTO.setId( s.getId() );

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

        donationDetails.setArchivated( donationDetailsDTO.getArchivated() );
        donationDetails.setBeneficiary( beneficiaryDTOToBeneficiary1( donationDetailsDTO.getBeneficiary() ) );
        donationDetails.setDescription( donationDetailsDTO.getDescription() );
        donationDetails.setDonationsIssued( donationsIssuedDTOToDonationsIssued1( donationDetailsDTO.getDonationsIssued() ) );
        donationDetails.setId( donationDetailsDTO.getId() );
        donationDetails.setNature( natureDTOToNature1( donationDetailsDTO.getNature() ) );

        return donationDetails;
    }

    protected void authorizingOfficerDTOToAuthorizingOfficer(AuthorizingOfficerDTO authorizingOfficerDTO, AuthorizingOfficer mappingTarget) {
        if ( authorizingOfficerDTO == null ) {
            return;
        }

        if ( authorizingOfficerDTO.getAbbreviation() != null ) {
            mappingTarget.setAbbreviation( authorizingOfficerDTO.getAbbreviation() );
        }
        if ( authorizingOfficerDTO.getActivity() != null ) {
            mappingTarget.setActivity( authorizingOfficerDTO.getActivity() );
        }
        if ( authorizingOfficerDTO.getId() != null ) {
            mappingTarget.setId( authorizingOfficerDTO.getId() );
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

        if ( tutorDTO.getActivity() != null ) {
            mappingTarget.setActivity( tutorDTO.getActivity() );
        }
        if ( tutorDTO.getId() != null ) {
            mappingTarget.setId( tutorDTO.getId() );
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

        if ( beneficiaryDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( beneficiaryDTO.getArchivated() );
        }
        if ( beneficiaryDTO.getAuthorizingOfficer() != null ) {
            if ( mappingTarget.getAuthorizingOfficer() == null ) {
                mappingTarget.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            authorizingOfficerDTOToAuthorizingOfficer( beneficiaryDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
        if ( beneficiaryDTO.getBeneficiaryReference() != null ) {
            mappingTarget.setBeneficiaryReference( beneficiaryDTO.getBeneficiaryReference() );
        }
        if ( beneficiaryDTO.getBeneficiaryType() != null ) {
            mappingTarget.setBeneficiaryType( beneficiaryDTO.getBeneficiaryType() );
        }
        if ( beneficiaryDTO.getId() != null ) {
            mappingTarget.setId( beneficiaryDTO.getId() );
        }
        if ( beneficiaryDTO.getTutor() != null ) {
            if ( mappingTarget.getTutor() == null ) {
                mappingTarget.tutor( new Tutor() );
            }
            tutorDTOToTutor( beneficiaryDTO.getTutor(), mappingTarget.getTutor() );
        }
    }

    protected void donationsIssuedDTOToDonationsIssued(DonationsIssuedDTO donationsIssuedDTO, DonationsIssued mappingTarget) {
        if ( donationsIssuedDTO == null ) {
            return;
        }

        if ( donationsIssuedDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( donationsIssuedDTO.getArchivated() );
        }
        if ( donationsIssuedDTO.getCanceledBy() != null ) {
            mappingTarget.setCanceledBy( donationsIssuedDTO.getCanceledBy() );
        }
        if ( donationsIssuedDTO.getCanceledDonations() != null ) {
            mappingTarget.setCanceledDonations( donationsIssuedDTO.getCanceledDonations() );
        }
        if ( donationsIssuedDTO.getCanceledOn() != null ) {
            mappingTarget.setCanceledOn( donationsIssuedDTO.getCanceledOn() );
        }
        if ( donationsIssuedDTO.getCancellationReason() != null ) {
            mappingTarget.setCancellationReason( donationsIssuedDTO.getCancellationReason() );
        }
        if ( donationsIssuedDTO.getDonationsDate() != null ) {
            mappingTarget.setDonationsDate( donationsIssuedDTO.getDonationsDate() );
        }
        if ( donationsIssuedDTO.getId() != null ) {
            mappingTarget.setId( donationsIssuedDTO.getId() );
        }
        if ( donationsIssuedDTO.getIsValidated() != null ) {
            mappingTarget.setIsValidated( donationsIssuedDTO.getIsValidated() );
        }
        if ( donationsIssuedDTO.getModel() != null ) {
            mappingTarget.setModel( donationsIssuedDTO.getModel() );
        }
        if ( donationsIssuedDTO.getPeriodicity() != null ) {
            mappingTarget.setPeriodicity( donationsIssuedDTO.getPeriodicity() );
        }
        if ( donationsIssuedDTO.getRecurrence() != null ) {
            mappingTarget.setRecurrence( donationsIssuedDTO.getRecurrence() );
        }
        if ( donationsIssuedDTO.getRecurringDonations() != null ) {
            mappingTarget.setRecurringDonations( donationsIssuedDTO.getRecurringDonations() );
        }
        if ( donationsIssuedDTO.getValidationDate() != null ) {
            mappingTarget.validationDate( donationsIssuedDTO.getValidationDate() );
        }
        if ( donationsIssuedDTO.getValidationUser() != null ) {
            mappingTarget.validationUser( donationsIssuedDTO.getValidationUser() );
        }
    }

    protected void natureDTOToNature(NatureDTO natureDTO, Nature mappingTarget) {
        if ( natureDTO == null ) {
            return;
        }

        if ( natureDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( natureDTO.getArchivated() );
        }
        if ( natureDTO.getDestinedTo() != null ) {
            mappingTarget.setDestinedTo( natureDTO.getDestinedTo() );
        }
        if ( natureDTO.getId() != null ) {
            mappingTarget.setId( natureDTO.getId() );
        }
        if ( natureDTO.getName() != null ) {
            mappingTarget.setName( natureDTO.getName() );
        }
        if ( natureDTO.getNecessityValue() != null ) {
            mappingTarget.setNecessityValue( natureDTO.getNecessityValue() );
        }
    }

    protected AuthorizingOfficer authorizingOfficerDTOToAuthorizingOfficer1(AuthorizingOfficerDTO authorizingOfficerDTO) {
        if ( authorizingOfficerDTO == null ) {
            return null;
        }

        AuthorizingOfficer authorizingOfficer = new AuthorizingOfficer();

        authorizingOfficer.setAbbreviation( authorizingOfficerDTO.getAbbreviation() );
        authorizingOfficer.setActivity( authorizingOfficerDTO.getActivity() );
        authorizingOfficer.setId( authorizingOfficerDTO.getId() );
        authorizingOfficer.setManager( authorizingOfficerDTO.getManager() );
        authorizingOfficer.setManagerCin( authorizingOfficerDTO.getManagerCin() );

        return authorizingOfficer;
    }

    protected Tutor tutorDTOToTutor1(TutorDTO tutorDTO) {
        if ( tutorDTO == null ) {
            return null;
        }

        Tutor tutor = new Tutor();

        tutor.setActivity( tutorDTO.getActivity() );
        tutor.setId( tutorDTO.getId() );
        tutor.setManager( tutorDTO.getManager() );
        tutor.setManagerCin( tutorDTO.getManagerCin() );

        return tutor;
    }

    protected Beneficiary beneficiaryDTOToBeneficiary1(BeneficiaryDTO beneficiaryDTO) {
        if ( beneficiaryDTO == null ) {
            return null;
        }

        Beneficiary beneficiary = new Beneficiary();

        beneficiary.setArchivated( beneficiaryDTO.getArchivated() );
        beneficiary.setAuthorizingOfficer( authorizingOfficerDTOToAuthorizingOfficer1( beneficiaryDTO.getAuthorizingOfficer() ) );
        beneficiary.setBeneficiaryReference( beneficiaryDTO.getBeneficiaryReference() );
        beneficiary.setBeneficiaryType( beneficiaryDTO.getBeneficiaryType() );
        beneficiary.setId( beneficiaryDTO.getId() );
        beneficiary.tutor( tutorDTOToTutor1( beneficiaryDTO.getTutor() ) );

        return beneficiary;
    }

    protected DonationsIssued donationsIssuedDTOToDonationsIssued1(DonationsIssuedDTO donationsIssuedDTO) {
        if ( donationsIssuedDTO == null ) {
            return null;
        }

        DonationsIssued donationsIssued = new DonationsIssued();

        donationsIssued.setArchivated( donationsIssuedDTO.getArchivated() );
        donationsIssued.setCanceledBy( donationsIssuedDTO.getCanceledBy() );
        donationsIssued.setCanceledDonations( donationsIssuedDTO.getCanceledDonations() );
        donationsIssued.setCanceledOn( donationsIssuedDTO.getCanceledOn() );
        donationsIssued.setCancellationReason( donationsIssuedDTO.getCancellationReason() );
        donationsIssued.setDonationsDate( donationsIssuedDTO.getDonationsDate() );
        donationsIssued.setId( donationsIssuedDTO.getId() );
        donationsIssued.setIsValidated( donationsIssuedDTO.getIsValidated() );
        donationsIssued.setModel( donationsIssuedDTO.getModel() );
        donationsIssued.setPeriodicity( donationsIssuedDTO.getPeriodicity() );
        donationsIssued.setRecurrence( donationsIssuedDTO.getRecurrence() );
        donationsIssued.setRecurringDonations( donationsIssuedDTO.getRecurringDonations() );
        donationsIssued.validationDate( donationsIssuedDTO.getValidationDate() );
        donationsIssued.validationUser( donationsIssuedDTO.getValidationUser() );

        return donationsIssued;
    }

    protected Nature natureDTOToNature1(NatureDTO natureDTO) {
        if ( natureDTO == null ) {
            return null;
        }

        Nature nature = new Nature();

        nature.setArchivated( natureDTO.getArchivated() );
        nature.setDestinedTo( natureDTO.getDestinedTo() );
        nature.setId( natureDTO.getId() );
        nature.setName( natureDTO.getName() );
        nature.setNecessityValue( natureDTO.getNecessityValue() );

        return nature;
    }
}
