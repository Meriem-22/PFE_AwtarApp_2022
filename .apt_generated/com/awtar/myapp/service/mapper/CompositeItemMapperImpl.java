package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.CompositeItem;
import com.awtar.myapp.domain.Item;
import com.awtar.myapp.domain.Nature;
import com.awtar.myapp.service.dto.CompositeItemDTO;
import com.awtar.myapp.service.dto.ItemDTO;
import com.awtar.myapp.service.dto.NatureDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-06T15:59:28+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20221012-0724, environment: Java 17.0.4.1 (Eclipse Adoptium)"
)
@Component
public class CompositeItemMapperImpl implements CompositeItemMapper {

    @Override
    public CompositeItem toEntity(CompositeItemDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CompositeItem compositeItem = new CompositeItem();

        compositeItem.setId( dto.getId() );
        compositeItem.setQuantity( dto.getQuantity() );
        compositeItem.setArchivated( dto.getArchivated() );
        compositeItem.composant( itemDTOToItem( dto.getComposant() ) );
        compositeItem.composer( itemDTOToItem( dto.getComposer() ) );

        return compositeItem;
    }

    @Override
    public List<CompositeItem> toEntity(List<CompositeItemDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CompositeItem> list = new ArrayList<CompositeItem>( dtoList.size() );
        for ( CompositeItemDTO compositeItemDTO : dtoList ) {
            list.add( toEntity( compositeItemDTO ) );
        }

        return list;
    }

    @Override
    public List<CompositeItemDTO> toDto(List<CompositeItem> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CompositeItemDTO> list = new ArrayList<CompositeItemDTO>( entityList.size() );
        for ( CompositeItem compositeItem : entityList ) {
            list.add( toDto( compositeItem ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(CompositeItem entity, CompositeItemDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getQuantity() != null ) {
            entity.setQuantity( dto.getQuantity() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
        if ( dto.getComposant() != null ) {
            if ( entity.getComposant() == null ) {
                entity.composant( new Item() );
            }
            itemDTOToItem1( dto.getComposant(), entity.getComposant() );
        }
        if ( dto.getComposer() != null ) {
            if ( entity.getComposer() == null ) {
                entity.composer( new Item() );
            }
            itemDTOToItem1( dto.getComposer(), entity.getComposer() );
        }
    }

    @Override
    public CompositeItemDTO toDto(CompositeItem s) {
        if ( s == null ) {
            return null;
        }

        CompositeItemDTO compositeItemDTO = new CompositeItemDTO();

        compositeItemDTO.setComposant( toDtoItemName( s.getComposant() ) );
        compositeItemDTO.setComposer( toDtoItemName( s.getComposer() ) );
        compositeItemDTO.setId( s.getId() );
        compositeItemDTO.setQuantity( s.getQuantity() );
        compositeItemDTO.setArchivated( s.getArchivated() );

        return compositeItemDTO;
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
