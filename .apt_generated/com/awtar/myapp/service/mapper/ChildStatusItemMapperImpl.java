package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.ChildStatus;
import com.awtar.myapp.domain.ChildStatusItem;
import com.awtar.myapp.domain.Item;
import com.awtar.myapp.domain.Nature;
import com.awtar.myapp.service.dto.ChildStatusDTO;
import com.awtar.myapp.service.dto.ChildStatusItemDTO;
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
public class ChildStatusItemMapperImpl implements ChildStatusItemMapper {

    @Override
    public void partialUpdate(ChildStatusItem arg0, ChildStatusItemDTO arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getAffected() != null ) {
            arg0.setAffected( arg1.getAffected() );
        }
        if ( arg1.getArchivated() != null ) {
            arg0.setArchivated( arg1.getArchivated() );
        }
        if ( arg1.getChildStatus() != null ) {
            if ( arg0.getChildStatus() == null ) {
                arg0.setChildStatus( new ChildStatus() );
            }
            childStatusDTOToChildStatus( arg1.getChildStatus(), arg0.getChildStatus() );
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
    }

    @Override
    public List<ChildStatusItemDTO> toDto(List<ChildStatusItem> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<ChildStatusItemDTO> list = new ArrayList<ChildStatusItemDTO>( arg0.size() );
        for ( ChildStatusItem childStatusItem : arg0 ) {
            list.add( toDto( childStatusItem ) );
        }

        return list;
    }

    @Override
    public ChildStatusItem toEntity(ChildStatusItemDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        ChildStatusItem childStatusItem = new ChildStatusItem();

        childStatusItem.setAffected( arg0.getAffected() );
        childStatusItem.setArchivated( arg0.getArchivated() );
        childStatusItem.setChildStatus( childStatusDTOToChildStatus1( arg0.getChildStatus() ) );
        childStatusItem.setId( arg0.getId() );
        childStatusItem.setItem( itemDTOToItem1( arg0.getItem() ) );

        return childStatusItem;
    }

    @Override
    public List<ChildStatusItem> toEntity(List<ChildStatusItemDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<ChildStatusItem> list = new ArrayList<ChildStatusItem>( arg0.size() );
        for ( ChildStatusItemDTO childStatusItemDTO : arg0 ) {
            list.add( toEntity( childStatusItemDTO ) );
        }

        return list;
    }

    @Override
    public ChildStatusItemDTO toDto(ChildStatusItem s) {
        if ( s == null ) {
            return null;
        }

        ChildStatusItemDTO childStatusItemDTO = new ChildStatusItemDTO();

        childStatusItemDTO.setItem( toDtoItemName( s.getItem() ) );
        childStatusItemDTO.setChildStatus( toDtoChildStatusStaus( s.getChildStatus() ) );
        childStatusItemDTO.setId( s.getId() );
        childStatusItemDTO.setAffected( s.getAffected() );
        childStatusItemDTO.setArchivated( s.getArchivated() );

        return childStatusItemDTO;
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
    public ChildStatusDTO toDtoChildStatusStaus(ChildStatus childStatus) {
        if ( childStatus == null ) {
            return null;
        }

        ChildStatusDTO childStatusDTO = new ChildStatusDTO();

        childStatusDTO.setId( childStatus.getId() );
        childStatusDTO.setStaus( childStatus.getStaus() );

        return childStatusDTO;
    }

    protected void childStatusDTOToChildStatus(ChildStatusDTO childStatusDTO, ChildStatus mappingTarget) {
        if ( childStatusDTO == null ) {
            return;
        }

        if ( childStatusDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( childStatusDTO.getArchivated() );
        }
        if ( childStatusDTO.getId() != null ) {
            mappingTarget.setId( childStatusDTO.getId() );
        }
        if ( childStatusDTO.getStaus() != null ) {
            mappingTarget.staus( childStatusDTO.getStaus() );
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

    protected ChildStatus childStatusDTOToChildStatus1(ChildStatusDTO childStatusDTO) {
        if ( childStatusDTO == null ) {
            return null;
        }

        ChildStatus childStatus = new ChildStatus();

        childStatus.setArchivated( childStatusDTO.getArchivated() );
        childStatus.setId( childStatusDTO.getId() );
        childStatus.staus( childStatusDTO.getStaus() );

        return childStatus;
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
