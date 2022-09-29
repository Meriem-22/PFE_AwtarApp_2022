package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.Beneficiary;
import com.awtar.myapp.domain.DonationDetails;
import com.awtar.myapp.domain.DonationItemDetails;
import com.awtar.myapp.domain.DonationsIssued;
import com.awtar.myapp.domain.Item;
import com.awtar.myapp.domain.Nature;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import com.awtar.myapp.service.dto.BeneficiaryDTO;
import com.awtar.myapp.service.dto.DonationDetailsDTO;
import com.awtar.myapp.service.dto.DonationItemDetailsDTO;
import com.awtar.myapp.service.dto.DonationsIssuedDTO;
import com.awtar.myapp.service.dto.ItemDTO;
import com.awtar.myapp.service.dto.NatureDTO;
import com.awtar.myapp.service.dto.TutorDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-29T21:08:49+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class DonationItemDetailsMapperImpl implements DonationItemDetailsMapper {

    @Override
    public void partialUpdate(DonationItemDetails arg0, DonationItemDetailsDTO arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getArchivated() != null ) {
            arg0.setArchivated( arg1.getArchivated() );
        }
        if ( arg1.getDate() != null ) {
            arg0.setDate( arg1.getDate() );
        }
        if ( arg1.getDonationDetails() != null ) {
            if ( arg0.getDonationDetails() == null ) {
                arg0.setDonationDetails( new DonationDetails() );
            }
            donationDetailsDTOToDonationDetails( arg1.getDonationDetails(), arg0.getDonationDetails() );
        }
        if ( arg1.getId() != null ) {
            arg0.setId( arg1.getId() );
        }
        if ( arg1.getItem() != null ) {
            if ( arg0.getItem() == null ) {
                arg0.setItem( new Item() );
            }
            itemDTOToItem( arg1.getItem(), arg0.getItem() );
        }
        if ( arg1.getQuantity() != null ) {
            arg0.setQuantity( arg1.getQuantity() );
        }
    }

    @Override
    public List<DonationItemDetailsDTO> toDto(List<DonationItemDetails> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<DonationItemDetailsDTO> list = new ArrayList<DonationItemDetailsDTO>( arg0.size() );
        for ( DonationItemDetails donationItemDetails : arg0 ) {
            list.add( toDto( donationItemDetails ) );
        }

        return list;
    }

    @Override
    public DonationItemDetails toEntity(DonationItemDetailsDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        DonationItemDetails donationItemDetails = new DonationItemDetails();

        donationItemDetails.setArchivated( arg0.getArchivated() );
        donationItemDetails.setDate( arg0.getDate() );
        donationItemDetails.setDonationDetails( donationDetailsDTOToDonationDetails1( arg0.getDonationDetails() ) );
        donationItemDetails.setId( arg0.getId() );
        donationItemDetails.setItem( itemDTOToItem1( arg0.getItem() ) );
        donationItemDetails.setQuantity( arg0.getQuantity() );

        return donationItemDetails;
    }

    @Override
    public List<DonationItemDetails> toEntity(List<DonationItemDetailsDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<DonationItemDetails> list = new ArrayList<DonationItemDetails>( arg0.size() );
        for ( DonationItemDetailsDTO donationItemDetailsDTO : arg0 ) {
            list.add( toEntity( donationItemDetailsDTO ) );
        }

        return list;
    }

    @Override
    public DonationItemDetailsDTO toDto(DonationItemDetails s) {
        if ( s == null ) {
            return null;
        }

        DonationItemDetailsDTO donationItemDetailsDTO = new DonationItemDetailsDTO();

        donationItemDetailsDTO.setDonationDetails( toDtoDonationDetailsId( s.getDonationDetails() ) );
        donationItemDetailsDTO.setItem( toDtoItemName( s.getItem() ) );
        donationItemDetailsDTO.setId( s.getId() );
        donationItemDetailsDTO.setQuantity( s.getQuantity() );
        donationItemDetailsDTO.setDate( s.getDate() );
        donationItemDetailsDTO.setArchivated( s.getArchivated() );

        return donationItemDetailsDTO;
    }

    @Override
    public DonationDetailsDTO toDtoDonationDetailsId(DonationDetails donationDetails) {
        if ( donationDetails == null ) {
            return null;
        }

        DonationDetailsDTO donationDetailsDTO = new DonationDetailsDTO();

        donationDetailsDTO.setId( donationDetails.getId() );

        return donationDetailsDTO;
    }

    @Override
    public ItemDTO toDtoItemName(Item item) {
        if ( item == null ) {
            return null;
        }

        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setId( item.getId() );
        itemDTO.setName( item.getName() );

        return itemDTO;
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

    protected void donationDetailsDTOToDonationDetails(DonationDetailsDTO donationDetailsDTO, DonationDetails mappingTarget) {
        if ( donationDetailsDTO == null ) {
            return;
        }

        if ( donationDetailsDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( donationDetailsDTO.getArchivated() );
        }
        if ( donationDetailsDTO.getBeneficiary() != null ) {
            if ( mappingTarget.getBeneficiary() == null ) {
                mappingTarget.setBeneficiary( new Beneficiary() );
            }
            beneficiaryDTOToBeneficiary( donationDetailsDTO.getBeneficiary(), mappingTarget.getBeneficiary() );
        }
        if ( donationDetailsDTO.getDescription() != null ) {
            mappingTarget.setDescription( donationDetailsDTO.getDescription() );
        }
        if ( donationDetailsDTO.getDonationsIssued() != null ) {
            if ( mappingTarget.getDonationsIssued() == null ) {
                mappingTarget.setDonationsIssued( new DonationsIssued() );
            }
            donationsIssuedDTOToDonationsIssued( donationDetailsDTO.getDonationsIssued(), mappingTarget.getDonationsIssued() );
        }
        if ( donationDetailsDTO.getId() != null ) {
            mappingTarget.setId( donationDetailsDTO.getId() );
        }
        if ( donationDetailsDTO.getNature() != null ) {
            if ( mappingTarget.getNature() == null ) {
                mappingTarget.setNature( new Nature() );
            }
            natureDTOToNature( donationDetailsDTO.getNature(), mappingTarget.getNature() );
        }
    }

    protected void itemDTOToItem(ItemDTO itemDTO, Item mappingTarget) {
        if ( itemDTO == null ) {
            return;
        }

        if ( itemDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( itemDTO.getArchivated() );
        }
        if ( itemDTO.getComposed() != null ) {
            mappingTarget.setComposed( itemDTO.getComposed() );
        }
        if ( itemDTO.getGender() != null ) {
            mappingTarget.setGender( itemDTO.getGender() );
        }
        if ( itemDTO.getId() != null ) {
            mappingTarget.setId( itemDTO.getId() );
        }
        if ( itemDTO.getName() != null ) {
            mappingTarget.setName( itemDTO.getName() );
        }
        if ( itemDTO.getNature() != null ) {
            if ( mappingTarget.getNature() == null ) {
                mappingTarget.setNature( new Nature() );
            }
            natureDTOToNature( itemDTO.getNature(), mappingTarget.getNature() );
        }
        byte[] urlPhoto = itemDTO.getUrlPhoto();
        if ( urlPhoto != null ) {
            mappingTarget.urlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        if ( itemDTO.getUrlPhotoContentType() != null ) {
            mappingTarget.urlPhotoContentType( itemDTO.getUrlPhotoContentType() );
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

    protected DonationDetails donationDetailsDTOToDonationDetails1(DonationDetailsDTO donationDetailsDTO) {
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

    protected Item itemDTOToItem1(ItemDTO itemDTO) {
        if ( itemDTO == null ) {
            return null;
        }

        Item item = new Item();

        item.setArchivated( itemDTO.getArchivated() );
        item.setComposed( itemDTO.getComposed() );
        item.setGender( itemDTO.getGender() );
        item.setId( itemDTO.getId() );
        item.setName( itemDTO.getName() );
        item.setNature( natureDTOToNature1( itemDTO.getNature() ) );
        byte[] urlPhoto = itemDTO.getUrlPhoto();
        if ( urlPhoto != null ) {
            item.urlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        item.urlPhotoContentType( itemDTO.getUrlPhotoContentType() );

        return item;
    }
}
