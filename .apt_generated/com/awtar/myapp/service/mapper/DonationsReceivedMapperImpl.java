package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.DonationsReceived;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import com.awtar.myapp.service.dto.DonationsReceivedDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-26T08:25:01+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class DonationsReceivedMapperImpl implements DonationsReceivedMapper {

    @Override
    public DonationsReceived toEntity(DonationsReceivedDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DonationsReceived donationsReceived = new DonationsReceived();

        donationsReceived.setId( dto.getId() );
        byte[] urlPhoto = dto.getUrlPhoto();
        if ( urlPhoto != null ) {
            donationsReceived.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        donationsReceived.setUrlPhotoContentType( dto.getUrlPhotoContentType() );
        donationsReceived.setArchivated( dto.getArchivated() );
        donationsReceived.authorizingOfficer( authorizingOfficerDTOToAuthorizingOfficer( dto.getAuthorizingOfficer() ) );

        return donationsReceived;
    }

    @Override
    public List<DonationsReceived> toEntity(List<DonationsReceivedDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DonationsReceived> list = new ArrayList<DonationsReceived>( dtoList.size() );
        for ( DonationsReceivedDTO donationsReceivedDTO : dtoList ) {
            list.add( toEntity( donationsReceivedDTO ) );
        }

        return list;
    }

    @Override
    public List<DonationsReceivedDTO> toDto(List<DonationsReceived> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DonationsReceivedDTO> list = new ArrayList<DonationsReceivedDTO>( entityList.size() );
        for ( DonationsReceived donationsReceived : entityList ) {
            list.add( toDto( donationsReceived ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(DonationsReceived entity, DonationsReceivedDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        byte[] urlPhoto = dto.getUrlPhoto();
        if ( urlPhoto != null ) {
            entity.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        if ( dto.getUrlPhotoContentType() != null ) {
            entity.setUrlPhotoContentType( dto.getUrlPhotoContentType() );
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
    }

    @Override
    public DonationsReceivedDTO toDto(DonationsReceived s) {
        if ( s == null ) {
            return null;
        }

        DonationsReceivedDTO donationsReceivedDTO = new DonationsReceivedDTO();

        donationsReceivedDTO.setAuthorizingOfficer( toDtoAuthorizingOfficerAbbreviation( s.getAuthorizingOfficer() ) );
        donationsReceivedDTO.setId( s.getId() );
        byte[] urlPhoto = s.getUrlPhoto();
        if ( urlPhoto != null ) {
            donationsReceivedDTO.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        donationsReceivedDTO.setUrlPhotoContentType( s.getUrlPhotoContentType() );
        donationsReceivedDTO.setArchivated( s.getArchivated() );

        return donationsReceivedDTO;
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
}
