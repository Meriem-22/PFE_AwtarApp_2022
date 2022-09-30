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
    date = "2022-09-30T07:21:22+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class DonationsReceivedItemMapperImpl implements DonationsReceivedItemMapper {

    @Override
    public DonationsReceivedItem toEntity(DonationsReceivedItemDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DonationsReceivedItem donationsReceivedItem = new DonationsReceivedItem();

        donationsReceivedItem.setId( dto.getId() );
        donationsReceivedItem.setQuantity( dto.getQuantity() );
        donationsReceivedItem.setDate( dto.getDate() );
        donationsReceivedItem.setArchivated( dto.getArchivated() );
        donationsReceivedItem.item( itemDTOToItem( dto.getItem() ) );
        donationsReceivedItem.donationsReceived( donationsReceivedDTOToDonationsReceived( dto.getDonationsReceived() ) );

        return donationsReceivedItem;
    }

    @Override
    public List<DonationsReceivedItem> toEntity(List<DonationsReceivedItemDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DonationsReceivedItem> list = new ArrayList<DonationsReceivedItem>( dtoList.size() );
        for ( DonationsReceivedItemDTO donationsReceivedItemDTO : dtoList ) {
            list.add( toEntity( donationsReceivedItemDTO ) );
        }

        return list;
    }

    @Override
    public List<DonationsReceivedItemDTO> toDto(List<DonationsReceivedItem> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DonationsReceivedItemDTO> list = new ArrayList<DonationsReceivedItemDTO>( entityList.size() );
        for ( DonationsReceivedItem donationsReceivedItem : entityList ) {
            list.add( toDto( donationsReceivedItem ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(DonationsReceivedItem entity, DonationsReceivedItemDTO dto) {
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
        if ( dto.getItem() != null ) {
            if ( entity.getItem() == null ) {
                entity.item( new Item() );
            }
            itemDTOToItem1( dto.getItem(), entity.getItem() );
        }
        if ( dto.getDonationsReceived() != null ) {
            if ( entity.getDonationsReceived() == null ) {
                entity.donationsReceived( new DonationsReceived() );
            }
            donationsReceivedDTOToDonationsReceived1( dto.getDonationsReceived(), entity.getDonationsReceived() );
        }
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

    protected DonationsReceived donationsReceivedDTOToDonationsReceived(DonationsReceivedDTO donationsReceivedDTO) {
        if ( donationsReceivedDTO == null ) {
            return null;
        }

        DonationsReceived donationsReceived = new DonationsReceived();

        donationsReceived.setId( donationsReceivedDTO.getId() );
        byte[] urlPhoto = donationsReceivedDTO.getUrlPhoto();
        if ( urlPhoto != null ) {
            donationsReceived.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        donationsReceived.setUrlPhotoContentType( donationsReceivedDTO.getUrlPhotoContentType() );
        donationsReceived.setArchivated( donationsReceivedDTO.getArchivated() );
        donationsReceived.authorizingOfficer( authorizingOfficerDTOToAuthorizingOfficer( donationsReceivedDTO.getAuthorizingOfficer() ) );

        return donationsReceived;
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

    protected void donationsReceivedDTOToDonationsReceived1(DonationsReceivedDTO donationsReceivedDTO, DonationsReceived mappingTarget) {
        if ( donationsReceivedDTO == null ) {
            return;
        }

        if ( donationsReceivedDTO.getId() != null ) {
            mappingTarget.setId( donationsReceivedDTO.getId() );
        }
        byte[] urlPhoto = donationsReceivedDTO.getUrlPhoto();
        if ( urlPhoto != null ) {
            mappingTarget.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        if ( donationsReceivedDTO.getUrlPhotoContentType() != null ) {
            mappingTarget.setUrlPhotoContentType( donationsReceivedDTO.getUrlPhotoContentType() );
        }
        if ( donationsReceivedDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( donationsReceivedDTO.getArchivated() );
        }
        if ( donationsReceivedDTO.getAuthorizingOfficer() != null ) {
            if ( mappingTarget.getAuthorizingOfficer() == null ) {
                mappingTarget.authorizingOfficer( new AuthorizingOfficer() );
            }
            authorizingOfficerDTOToAuthorizingOfficer1( donationsReceivedDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
    }
}
