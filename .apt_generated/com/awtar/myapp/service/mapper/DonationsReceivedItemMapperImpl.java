package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.DonationsReceived;
import com.awtar.myapp.domain.DonationsReceivedItem;
import com.awtar.myapp.domain.Item;
import com.awtar.myapp.domain.Nature;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import com.awtar.myapp.service.dto.DonationsReceivedDTO;
import com.awtar.myapp.service.dto.DonationsReceivedItemDTO;
import com.awtar.myapp.service.dto.ItemDTO;
import com.awtar.myapp.service.dto.NatureDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-21T09:01:57+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class DonationsReceivedItemMapperImpl implements DonationsReceivedItemMapper {

    @Override
    public void partialUpdate(DonationsReceivedItem arg0, DonationsReceivedItemDTO arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getArchivated() != null ) {
            arg0.setArchivated( arg1.getArchivated() );
        }
        if ( arg1.getDate() != null ) {
            arg0.setDate( arg1.getDate() );
        }
        if ( arg1.getDonationsReceived() != null ) {
            if ( arg0.getDonationsReceived() == null ) {
                arg0.setDonationsReceived( new DonationsReceived() );
            }
            donationsReceivedDTOToDonationsReceived( arg1.getDonationsReceived(), arg0.getDonationsReceived() );
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
    public List<DonationsReceivedItemDTO> toDto(List<DonationsReceivedItem> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<DonationsReceivedItemDTO> list = new ArrayList<DonationsReceivedItemDTO>( arg0.size() );
        for ( DonationsReceivedItem donationsReceivedItem : arg0 ) {
            list.add( toDto( donationsReceivedItem ) );
        }

        return list;
    }

    @Override
    public DonationsReceivedItem toEntity(DonationsReceivedItemDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        DonationsReceivedItem donationsReceivedItem = new DonationsReceivedItem();

        donationsReceivedItem.setArchivated( arg0.getArchivated() );
        donationsReceivedItem.setDate( arg0.getDate() );
        donationsReceivedItem.setDonationsReceived( donationsReceivedDTOToDonationsReceived1( arg0.getDonationsReceived() ) );
        donationsReceivedItem.setId( arg0.getId() );
        donationsReceivedItem.setItem( itemDTOToItem1( arg0.getItem() ) );
        donationsReceivedItem.setQuantity( arg0.getQuantity() );

        return donationsReceivedItem;
    }

    @Override
    public List<DonationsReceivedItem> toEntity(List<DonationsReceivedItemDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<DonationsReceivedItem> list = new ArrayList<DonationsReceivedItem>( arg0.size() );
        for ( DonationsReceivedItemDTO donationsReceivedItemDTO : arg0 ) {
            list.add( toEntity( donationsReceivedItemDTO ) );
        }

        return list;
    }

    @Override
    public DonationsReceivedItemDTO toDto(DonationsReceivedItem s) {
        if ( s == null ) {
            return null;
        }

        DonationsReceivedItemDTO donationsReceivedItemDTO = new DonationsReceivedItemDTO();

        donationsReceivedItemDTO.setItem( toDtoItemName( s.getItem() ) );
        donationsReceivedItemDTO.setDonationsReceived( toDtoDonationsReceivedId( s.getDonationsReceived() ) );
        donationsReceivedItemDTO.setId( s.getId() );
        donationsReceivedItemDTO.setQuantity( s.getQuantity() );
        donationsReceivedItemDTO.setDate( s.getDate() );
        donationsReceivedItemDTO.setArchivated( s.getArchivated() );

        return donationsReceivedItemDTO;
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

    @Override
    public DonationsReceivedDTO toDtoDonationsReceivedId(DonationsReceived donationsReceived) {
        if ( donationsReceived == null ) {
            return null;
        }

        DonationsReceivedDTO donationsReceivedDTO = new DonationsReceivedDTO();

        donationsReceivedDTO.setId( donationsReceived.getId() );

        return donationsReceivedDTO;
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

    protected void donationsReceivedDTOToDonationsReceived(DonationsReceivedDTO donationsReceivedDTO, DonationsReceived mappingTarget) {
        if ( donationsReceivedDTO == null ) {
            return;
        }

        if ( donationsReceivedDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( donationsReceivedDTO.getArchivated() );
        }
        if ( donationsReceivedDTO.getAuthorizingOfficer() != null ) {
            if ( mappingTarget.getAuthorizingOfficer() == null ) {
                mappingTarget.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            authorizingOfficerDTOToAuthorizingOfficer( donationsReceivedDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
        if ( donationsReceivedDTO.getId() != null ) {
            mappingTarget.setId( donationsReceivedDTO.getId() );
        }
        byte[] urlPhoto = donationsReceivedDTO.getUrlPhoto();
        if ( urlPhoto != null ) {
            mappingTarget.urlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        if ( donationsReceivedDTO.getUrlPhotoContentType() != null ) {
            mappingTarget.urlPhotoContentType( donationsReceivedDTO.getUrlPhotoContentType() );
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

    protected DonationsReceived donationsReceivedDTOToDonationsReceived1(DonationsReceivedDTO donationsReceivedDTO) {
        if ( donationsReceivedDTO == null ) {
            return null;
        }

        DonationsReceived donationsReceived = new DonationsReceived();

        donationsReceived.setArchivated( donationsReceivedDTO.getArchivated() );
        donationsReceived.setAuthorizingOfficer( authorizingOfficerDTOToAuthorizingOfficer1( donationsReceivedDTO.getAuthorizingOfficer() ) );
        donationsReceived.setId( donationsReceivedDTO.getId() );
        byte[] urlPhoto = donationsReceivedDTO.getUrlPhoto();
        if ( urlPhoto != null ) {
            donationsReceived.urlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        donationsReceived.urlPhotoContentType( donationsReceivedDTO.getUrlPhotoContentType() );

        return donationsReceived;
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
