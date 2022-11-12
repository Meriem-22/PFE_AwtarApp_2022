package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.DonationsIssued;
import com.awtar.myapp.service.dto.DonationsIssuedDTO;
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
public class DonationsIssuedMapperImpl implements DonationsIssuedMapper {

    @Override
    public DonationsIssued toEntity(DonationsIssuedDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DonationsIssued donationsIssued = new DonationsIssued();

        donationsIssued.setId( dto.getId() );
        donationsIssued.setModel( dto.getModel() );
        donationsIssued.setIsValidated( dto.getIsValidated() );
        donationsIssued.setValidationDate( dto.getValidationDate() );
        donationsIssued.setValidationUser( dto.getValidationUser() );
        donationsIssued.setDonationsDate( dto.getDonationsDate() );
        donationsIssued.setCanceledDonations( dto.getCanceledDonations() );
        donationsIssued.setCanceledOn( dto.getCanceledOn() );
        donationsIssued.setCanceledBy( dto.getCanceledBy() );
        donationsIssued.setCancellationReason( dto.getCancellationReason() );
        donationsIssued.setRecurringDonations( dto.getRecurringDonations() );
        donationsIssued.setPeriodicity( dto.getPeriodicity() );
        donationsIssued.setRecurrence( dto.getRecurrence() );
        donationsIssued.setArchivated( dto.getArchivated() );

        return donationsIssued;
    }

    @Override
    public DonationsIssuedDTO toDto(DonationsIssued entity) {
        if ( entity == null ) {
            return null;
        }

        DonationsIssuedDTO donationsIssuedDTO = new DonationsIssuedDTO();

        donationsIssuedDTO.setId( entity.getId() );
        donationsIssuedDTO.setModel( entity.getModel() );
        donationsIssuedDTO.setIsValidated( entity.getIsValidated() );
        donationsIssuedDTO.setValidationDate( entity.getValidationDate() );
        donationsIssuedDTO.setValidationUser( entity.getValidationUser() );
        donationsIssuedDTO.setDonationsDate( entity.getDonationsDate() );
        donationsIssuedDTO.setCanceledDonations( entity.getCanceledDonations() );
        donationsIssuedDTO.setCanceledOn( entity.getCanceledOn() );
        donationsIssuedDTO.setCanceledBy( entity.getCanceledBy() );
        donationsIssuedDTO.setCancellationReason( entity.getCancellationReason() );
        donationsIssuedDTO.setRecurringDonations( entity.getRecurringDonations() );
        donationsIssuedDTO.setPeriodicity( entity.getPeriodicity() );
        donationsIssuedDTO.setRecurrence( entity.getRecurrence() );
        donationsIssuedDTO.setArchivated( entity.getArchivated() );

        return donationsIssuedDTO;
    }

    @Override
    public List<DonationsIssued> toEntity(List<DonationsIssuedDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DonationsIssued> list = new ArrayList<DonationsIssued>( dtoList.size() );
        for ( DonationsIssuedDTO donationsIssuedDTO : dtoList ) {
            list.add( toEntity( donationsIssuedDTO ) );
        }

        return list;
    }

    @Override
    public List<DonationsIssuedDTO> toDto(List<DonationsIssued> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DonationsIssuedDTO> list = new ArrayList<DonationsIssuedDTO>( entityList.size() );
        for ( DonationsIssued donationsIssued : entityList ) {
            list.add( toDto( donationsIssued ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(DonationsIssued entity, DonationsIssuedDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getModel() != null ) {
            entity.setModel( dto.getModel() );
        }
        if ( dto.getIsValidated() != null ) {
            entity.setIsValidated( dto.getIsValidated() );
        }
        if ( dto.getValidationDate() != null ) {
            entity.setValidationDate( dto.getValidationDate() );
        }
        if ( dto.getValidationUser() != null ) {
            entity.setValidationUser( dto.getValidationUser() );
        }
        if ( dto.getDonationsDate() != null ) {
            entity.setDonationsDate( dto.getDonationsDate() );
        }
        if ( dto.getCanceledDonations() != null ) {
            entity.setCanceledDonations( dto.getCanceledDonations() );
        }
        if ( dto.getCanceledOn() != null ) {
            entity.setCanceledOn( dto.getCanceledOn() );
        }
        if ( dto.getCanceledBy() != null ) {
            entity.setCanceledBy( dto.getCanceledBy() );
        }
        if ( dto.getCancellationReason() != null ) {
            entity.setCancellationReason( dto.getCancellationReason() );
        }
        if ( dto.getRecurringDonations() != null ) {
            entity.setRecurringDonations( dto.getRecurringDonations() );
        }
        if ( dto.getPeriodicity() != null ) {
            entity.setPeriodicity( dto.getPeriodicity() );
        }
        if ( dto.getRecurrence() != null ) {
            entity.setRecurrence( dto.getRecurrence() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
    }
}
