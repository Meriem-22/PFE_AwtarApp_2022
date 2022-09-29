package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.DonationsIssued;
import com.awtar.myapp.service.dto.DonationsIssuedDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-29T20:53:01+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class DonationsIssuedMapperImpl implements DonationsIssuedMapper {

    @Override
    public void partialUpdate(DonationsIssued arg0, DonationsIssuedDTO arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getArchivated() != null ) {
            arg0.setArchivated( arg1.getArchivated() );
        }
        if ( arg1.getCanceledBy() != null ) {
            arg0.setCanceledBy( arg1.getCanceledBy() );
        }
        if ( arg1.getCanceledDonations() != null ) {
            arg0.setCanceledDonations( arg1.getCanceledDonations() );
        }
        if ( arg1.getCanceledOn() != null ) {
            arg0.setCanceledOn( arg1.getCanceledOn() );
        }
        if ( arg1.getCancellationReason() != null ) {
            arg0.setCancellationReason( arg1.getCancellationReason() );
        }
        if ( arg1.getDonationsDate() != null ) {
            arg0.setDonationsDate( arg1.getDonationsDate() );
        }
        if ( arg1.getId() != null ) {
            arg0.setId( arg1.getId() );
        }
        if ( arg1.getIsValidated() != null ) {
            arg0.setIsValidated( arg1.getIsValidated() );
        }
        if ( arg1.getModel() != null ) {
            arg0.setModel( arg1.getModel() );
        }
        if ( arg1.getPeriodicity() != null ) {
            arg0.setPeriodicity( arg1.getPeriodicity() );
        }
        if ( arg1.getRecurrence() != null ) {
            arg0.setRecurrence( arg1.getRecurrence() );
        }
        if ( arg1.getRecurringDonations() != null ) {
            arg0.setRecurringDonations( arg1.getRecurringDonations() );
        }
        if ( arg1.getValidationDate() != null ) {
            arg0.validationDate( arg1.getValidationDate() );
        }
        if ( arg1.getValidationUser() != null ) {
            arg0.validationUser( arg1.getValidationUser() );
        }
    }

    @Override
    public DonationsIssuedDTO toDto(DonationsIssued arg0) {
        if ( arg0 == null ) {
            return null;
        }

        DonationsIssuedDTO donationsIssuedDTO = new DonationsIssuedDTO();

        donationsIssuedDTO.setArchivated( arg0.getArchivated() );
        donationsIssuedDTO.setCanceledBy( arg0.getCanceledBy() );
        donationsIssuedDTO.setCanceledDonations( arg0.getCanceledDonations() );
        donationsIssuedDTO.setCanceledOn( arg0.getCanceledOn() );
        donationsIssuedDTO.setCancellationReason( arg0.getCancellationReason() );
        donationsIssuedDTO.setDonationsDate( arg0.getDonationsDate() );
        donationsIssuedDTO.setId( arg0.getId() );
        donationsIssuedDTO.setIsValidated( arg0.getIsValidated() );
        donationsIssuedDTO.setModel( arg0.getModel() );
        donationsIssuedDTO.setPeriodicity( arg0.getPeriodicity() );
        donationsIssuedDTO.setRecurrence( arg0.getRecurrence() );
        donationsIssuedDTO.setRecurringDonations( arg0.getRecurringDonations() );
        donationsIssuedDTO.setValidationDate( arg0.getValidationDate() );
        donationsIssuedDTO.setValidationUser( arg0.getValidationUser() );

        return donationsIssuedDTO;
    }

    @Override
    public List<DonationsIssuedDTO> toDto(List<DonationsIssued> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<DonationsIssuedDTO> list = new ArrayList<DonationsIssuedDTO>( arg0.size() );
        for ( DonationsIssued donationsIssued : arg0 ) {
            list.add( toDto( donationsIssued ) );
        }

        return list;
    }

    @Override
    public DonationsIssued toEntity(DonationsIssuedDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        DonationsIssued donationsIssued = new DonationsIssued();

        donationsIssued.setArchivated( arg0.getArchivated() );
        donationsIssued.setCanceledBy( arg0.getCanceledBy() );
        donationsIssued.setCanceledDonations( arg0.getCanceledDonations() );
        donationsIssued.setCanceledOn( arg0.getCanceledOn() );
        donationsIssued.setCancellationReason( arg0.getCancellationReason() );
        donationsIssued.setDonationsDate( arg0.getDonationsDate() );
        donationsIssued.setId( arg0.getId() );
        donationsIssued.setIsValidated( arg0.getIsValidated() );
        donationsIssued.setModel( arg0.getModel() );
        donationsIssued.setPeriodicity( arg0.getPeriodicity() );
        donationsIssued.setRecurrence( arg0.getRecurrence() );
        donationsIssued.setRecurringDonations( arg0.getRecurringDonations() );
        donationsIssued.validationDate( arg0.getValidationDate() );
        donationsIssued.validationUser( arg0.getValidationUser() );

        return donationsIssued;
    }

    @Override
    public List<DonationsIssued> toEntity(List<DonationsIssuedDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<DonationsIssued> list = new ArrayList<DonationsIssued>( arg0.size() );
        for ( DonationsIssuedDTO donationsIssuedDTO : arg0 ) {
            list.add( toEntity( donationsIssuedDTO ) );
        }

        return list;
    }
}
