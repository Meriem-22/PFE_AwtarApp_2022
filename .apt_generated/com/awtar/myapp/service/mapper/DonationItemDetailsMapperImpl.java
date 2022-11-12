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
    date = "2022-11-12T16:07:08+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20221012-0724, environment: Java 17.0.4.1 (Eclipse Adoptium)"
)
@Component
public class DonationItemDetailsMapperImpl implements DonationItemDetailsMapper {

    @Override
    public DonationItemDetails toEntity(DonationItemDetailsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DonationItemDetails donationItemDetails = new DonationItemDetails();

        donationItemDetails.setId( dto.getId() );
        donationItemDetails.setQuantity( dto.getQuantity() );
        donationItemDetails.setDate( dto.getDate() );
        donationItemDetails.setArchivated( dto.getArchivated() );
        donationItemDetails.donationDetails( donationDetailsDTOToDonationDetails( dto.getDonationDetails() ) );
        donationItemDetails.item( itemDTOToItem( dto.getItem() ) );

        return donationItemDetails;
    }

    @Override
    public List<DonationItemDetails> toEntity(List<DonationItemDetailsDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DonationItemDetails> list = new ArrayList<DonationItemDetails>( dtoList.size() );
        for ( DonationItemDetailsDTO donationItemDetailsDTO : dtoList ) {
            list.add( toEntity( donationItemDetailsDTO ) );
        }

        return list;
    }

    @Override
    public List<DonationItemDetailsDTO> toDto(List<DonationItemDetails> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DonationItemDetailsDTO> list = new ArrayList<DonationItemDetailsDTO>( entityList.size() );
        for ( DonationItemDetails donationItemDetails : entityList ) {
            list.add( toDto( donationItemDetails ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(DonationItemDetails entity, DonationItemDetailsDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getQuantity() != null ) {
            entity.setQuantity( dto.getQuantity() );
        }
        if ( dto.getDate() != null ) {
            entity.setDate( dto.getDate() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
        if ( dto.getDonationDetails() != null ) {
            if ( entity.getDonationDetails() == null ) {
                entity.donationDetails( new DonationDetails() );
            }
            donationDetailsDTOToDonationDetails1( dto.getDonationDetails(), entity.getDonationDetails() );
        }
        if ( dto.getItem() != null ) {
            if ( entity.getItem() == null ) {
                entity.item( new Item() );
            }
            itemDTOToItem1( dto.getItem(), entity.getItem() );
        }
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

    protected DonationsIssued donationsIssuedDTOToDonationsIssued(DonationsIssuedDTO donationsIssuedDTO) {
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

    protected Nature natureDTOToNature(NatureDTO natureDTO) {
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

    protected DonationDetails donationDetailsDTOToDonationDetails(DonationDetailsDTO donationDetailsDTO) {
        if ( donationDetailsDTO == null ) {
            return null;
        }

        DonationDetails donationDetails = new DonationDetails();

        donationDetails.setId( donationDetailsDTO.getId() );
        donationDetails.setDescription( donationDetailsDTO.getDescription() );
        donationDetails.setArchivated( donationDetailsDTO.getArchivated() );
        donationDetails.donationsIssued( donationsIssuedDTOToDonationsIssued( donationDetailsDTO.getDonationsIssued() ) );
        donationDetails.nature( natureDTOToNature( donationDetailsDTO.getNature() ) );
        donationDetails.beneficiary( beneficiaryDTOToBeneficiary( donationDetailsDTO.getBeneficiary() ) );

        return donationDetails;
    }

    protected Item itemDTOToItem(ItemDTO itemDTO) {
        if ( itemDTO == null ) {
            return null;
        }

        Item item = new Item();

        item.setId( itemDTO.getId() );
        item.setName( itemDTO.getName() );
        byte[] urlPhoto = itemDTO.getUrlPhoto();
        if ( urlPhoto != null ) {
            item.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        item.setUrlPhotoContentType( itemDTO.getUrlPhotoContentType() );
        item.setGender( itemDTO.getGender() );
        item.setComposed( itemDTO.getComposed() );
        item.setArchivated( itemDTO.getArchivated() );
        item.nature( natureDTOToNature( itemDTO.getNature() ) );

        return item;
    }

    protected void donationsIssuedDTOToDonationsIssued1(DonationsIssuedDTO donationsIssuedDTO, DonationsIssued mappingTarget) {
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

    protected void natureDTOToNature1(NatureDTO natureDTO, Nature mappingTarget) {
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

    protected void donationDetailsDTOToDonationDetails1(DonationDetailsDTO donationDetailsDTO, DonationDetails mappingTarget) {
        if ( donationDetailsDTO == null ) {
            return;
        }

        if ( donationDetailsDTO.getId() != null ) {
            mappingTarget.setId( donationDetailsDTO.getId() );
        }
        if ( donationDetailsDTO.getDescription() != null ) {
            mappingTarget.setDescription( donationDetailsDTO.getDescription() );
        }
        if ( donationDetailsDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( donationDetailsDTO.getArchivated() );
        }
        if ( donationDetailsDTO.getDonationsIssued() != null ) {
            if ( mappingTarget.getDonationsIssued() == null ) {
                mappingTarget.donationsIssued( new DonationsIssued() );
            }
            donationsIssuedDTOToDonationsIssued1( donationDetailsDTO.getDonationsIssued(), mappingTarget.getDonationsIssued() );
        }
        if ( donationDetailsDTO.getNature() != null ) {
            if ( mappingTarget.getNature() == null ) {
                mappingTarget.nature( new Nature() );
            }
            natureDTOToNature1( donationDetailsDTO.getNature(), mappingTarget.getNature() );
        }
        if ( donationDetailsDTO.getBeneficiary() != null ) {
            if ( mappingTarget.getBeneficiary() == null ) {
                mappingTarget.beneficiary( new Beneficiary() );
            }
            beneficiaryDTOToBeneficiary1( donationDetailsDTO.getBeneficiary(), mappingTarget.getBeneficiary() );
        }
    }

    protected void itemDTOToItem1(ItemDTO itemDTO, Item mappingTarget) {
        if ( itemDTO == null ) {
            return;
        }

        if ( itemDTO.getId() != null ) {
            mappingTarget.setId( itemDTO.getId() );
        }
        if ( itemDTO.getName() != null ) {
            mappingTarget.setName( itemDTO.getName() );
        }
        byte[] urlPhoto = itemDTO.getUrlPhoto();
        if ( urlPhoto != null ) {
            mappingTarget.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        if ( itemDTO.getUrlPhotoContentType() != null ) {
            mappingTarget.setUrlPhotoContentType( itemDTO.getUrlPhotoContentType() );
        }
        if ( itemDTO.getGender() != null ) {
            mappingTarget.setGender( itemDTO.getGender() );
        }
        if ( itemDTO.getComposed() != null ) {
            mappingTarget.setComposed( itemDTO.getComposed() );
        }
        if ( itemDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( itemDTO.getArchivated() );
        }
        if ( itemDTO.getNature() != null ) {
            if ( mappingTarget.getNature() == null ) {
                mappingTarget.nature( new Nature() );
            }
            natureDTOToNature1( itemDTO.getNature(), mappingTarget.getNature() );
        }
    }
}
