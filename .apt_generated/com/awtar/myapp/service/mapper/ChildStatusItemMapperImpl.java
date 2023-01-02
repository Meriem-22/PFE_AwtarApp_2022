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
    date = "2022-12-06T08:12:07+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20221012-0724, environment: Java 17.0.5 (Eclipse Adoptium)"
)
@Component
public class ChildStatusItemMapperImpl implements ChildStatusItemMapper {

    @Override
    public ChildStatusItem toEntity(ChildStatusItemDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ChildStatusItem childStatusItem = new ChildStatusItem();

        childStatusItem.setId( dto.getId() );
        childStatusItem.setAffected( dto.getAffected() );
        childStatusItem.setArchivated( dto.getArchivated() );
        childStatusItem.item( itemDTOToItem( dto.getItem() ) );
        childStatusItem.childStatus( childStatusDTOToChildStatus( dto.getChildStatus() ) );

        return childStatusItem;
    }

    @Override
    public List<ChildStatusItem> toEntity(List<ChildStatusItemDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ChildStatusItem> list = new ArrayList<ChildStatusItem>( dtoList.size() );
        for ( ChildStatusItemDTO childStatusItemDTO : dtoList ) {
            list.add( toEntity( childStatusItemDTO ) );
        }

        return list;
    }

    @Override
    public List<ChildStatusItemDTO> toDto(List<ChildStatusItem> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ChildStatusItemDTO> list = new ArrayList<ChildStatusItemDTO>( entityList.size() );
        for ( ChildStatusItem childStatusItem : entityList ) {
            list.add( toDto( childStatusItem ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(ChildStatusItem entity, ChildStatusItemDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getAffected() != null ) {
            entity.setAffected( dto.getAffected() );
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
        if ( dto.getChildStatus() != null ) {
            if ( entity.getChildStatus() == null ) {
                entity.childStatus( new ChildStatus() );
            }
            childStatusDTOToChildStatus1( dto.getChildStatus(), entity.getChildStatus() );
        }
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

    protected ChildStatus childStatusDTOToChildStatus(ChildStatusDTO childStatusDTO) {
        if ( childStatusDTO == null ) {
            return null;
        }

        ChildStatus childStatus = new ChildStatus();

        childStatus.setId( childStatusDTO.getId() );
        childStatus.setStaus( childStatusDTO.getStaus() );
        childStatus.setArchivated( childStatusDTO.getArchivated() );

        return childStatus;
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

    protected void childStatusDTOToChildStatus1(ChildStatusDTO childStatusDTO, ChildStatus mappingTarget) {
        if ( childStatusDTO == null ) {
            return;
        }

        if ( childStatusDTO.getId() != null ) {
            mappingTarget.setId( childStatusDTO.getId() );
        }
        if ( childStatusDTO.getStaus() != null ) {
            mappingTarget.setStaus( childStatusDTO.getStaus() );
        }
        if ( childStatusDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( childStatusDTO.getArchivated() );
        }
    }
}
