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
    date = "2022-09-21T09:01:57+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class ItemValueMapperImpl implements ItemValueMapper {

    @Override
    public void partialUpdate(ItemValue arg0, ItemValueDTO arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getArchivated() != null ) {
            arg0.setArchivated( arg1.getArchivated() );
        }
        if ( arg1.getAvailableStockQuantity() != null ) {
            arg0.setAvailableStockQuantity( arg1.getAvailableStockQuantity() );
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
        if ( arg1.getPrice() != null ) {
            arg0.setPrice( arg1.getPrice() );
        }
        if ( arg1.getPriceDate() != null ) {
            arg0.setPriceDate( arg1.getPriceDate() );
        }
    }

    @Override
    public List<ItemValueDTO> toDto(List<ItemValue> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<ItemValueDTO> list = new ArrayList<ItemValueDTO>( arg0.size() );
        for ( ItemValue itemValue : arg0 ) {
            list.add( toDto( itemValue ) );
        }

        return list;
    }

    @Override
    public ItemValue toEntity(ItemValueDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        ItemValue itemValue = new ItemValue();

        itemValue.setArchivated( arg0.getArchivated() );
        itemValue.setAvailableStockQuantity( arg0.getAvailableStockQuantity() );
        itemValue.setId( arg0.getId() );
        itemValue.setItem( itemDTOToItem1( arg0.getItem() ) );
        itemValue.setPrice( arg0.getPrice() );
        itemValue.setPriceDate( arg0.getPriceDate() );

        return itemValue;
    }

    @Override
    public List<ItemValue> toEntity(List<ItemValueDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<ItemValue> list = new ArrayList<ItemValue>( arg0.size() );
        for ( ItemValueDTO itemValueDTO : arg0 ) {
            list.add( toEntity( itemValueDTO ) );
        }

        return list;
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
