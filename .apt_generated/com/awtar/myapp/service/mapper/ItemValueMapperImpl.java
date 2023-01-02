package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Item;
import com.awtar.myapp.domain.ItemValue;
import com.awtar.myapp.domain.Nature;
import com.awtar.myapp.service.dto.ItemDTO;
import com.awtar.myapp.service.dto.ItemValueDTO;
import com.awtar.myapp.service.dto.NatureDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-06T08:12:06+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20221012-0724, environment: Java 17.0.5 (Eclipse Adoptium)"
)
@Component
public class ItemValueMapperImpl implements ItemValueMapper {

    @Override
    public ItemValue toEntity(ItemValueDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ItemValue itemValue = new ItemValue();

        itemValue.setId( dto.getId() );
        itemValue.setPrice( dto.getPrice() );
        itemValue.setPriceDate( dto.getPriceDate() );
        itemValue.setAvailableStockQuantity( dto.getAvailableStockQuantity() );
        itemValue.setArchivated( dto.getArchivated() );
        itemValue.item( itemDTOToItem( dto.getItem() ) );

        return itemValue;
    }

    @Override
    public List<ItemValue> toEntity(List<ItemValueDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ItemValue> list = new ArrayList<ItemValue>( dtoList.size() );
        for ( ItemValueDTO itemValueDTO : dtoList ) {
            list.add( toEntity( itemValueDTO ) );
        }

        return list;
    }

    @Override
    public List<ItemValueDTO> toDto(List<ItemValue> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ItemValueDTO> list = new ArrayList<ItemValueDTO>( entityList.size() );
        for ( ItemValue itemValue : entityList ) {
            list.add( toDto( itemValue ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(ItemValue entity, ItemValueDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getPrice() != null ) {
            entity.setPrice( dto.getPrice() );
        }
        if ( dto.getPriceDate() != null ) {
            entity.setPriceDate( dto.getPriceDate() );
        }
        if ( dto.getAvailableStockQuantity() != null ) {
            entity.setAvailableStockQuantity( dto.getAvailableStockQuantity() );
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
    }

    @Override
    public ItemValueDTO toDto(ItemValue s) {
        if ( s == null ) {
            return null;
        }

        ItemValueDTO itemValueDTO = new ItemValueDTO();

        itemValueDTO.setItem( toDtoItemName( s.getItem() ) );
        itemValueDTO.setId( s.getId() );
        itemValueDTO.setPrice( s.getPrice() );
        itemValueDTO.setPriceDate( s.getPriceDate() );
        itemValueDTO.setAvailableStockQuantity( s.getAvailableStockQuantity() );
        itemValueDTO.setArchivated( s.getArchivated() );

        return itemValueDTO;
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
}
