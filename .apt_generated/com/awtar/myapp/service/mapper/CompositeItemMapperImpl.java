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
    date = "2022-09-21T09:01:57+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class CompositeItemMapperImpl implements CompositeItemMapper {

    @Override
    public void partialUpdate(CompositeItem arg0, CompositeItemDTO arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getArchivated() != null ) {
            arg0.setArchivated( arg1.getArchivated() );
        }
        if ( arg1.getComposant() != null ) {
            if ( arg0.getComposant() == null ) {
                arg0.setComposant( new Item() );
            }
            itemDTOToItem( arg1.getComposant(), arg0.getComposant() );
        }
        if ( arg1.getComposer() != null ) {
            if ( arg0.getComposer() == null ) {
                arg0.setComposer( new Item() );
            }
            itemDTOToItem( arg1.getComposer(), arg0.getComposer() );
        }
        if ( arg1.getId() != null ) {
            arg0.setId( arg1.getId() );
        }
        if ( arg1.getQuantity() != null ) {
            arg0.setQuantity( arg1.getQuantity() );
        }
    }

    @Override
    public List<CompositeItemDTO> toDto(List<CompositeItem> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<CompositeItemDTO> list = new ArrayList<CompositeItemDTO>( arg0.size() );
        for ( CompositeItem compositeItem : arg0 ) {
            list.add( toDto( compositeItem ) );
        }

        return list;
    }

    @Override
    public CompositeItem toEntity(CompositeItemDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        CompositeItem compositeItem = new CompositeItem();

        compositeItem.setArchivated( arg0.getArchivated() );
        compositeItem.setComposant( itemDTOToItem1( arg0.getComposant() ) );
        compositeItem.setComposer( itemDTOToItem1( arg0.getComposer() ) );
        compositeItem.setId( arg0.getId() );
        compositeItem.setQuantity( arg0.getQuantity() );

        return compositeItem;
    }

    @Override
    public List<CompositeItem> toEntity(List<CompositeItemDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<CompositeItem> list = new ArrayList<CompositeItem>( arg0.size() );
        for ( CompositeItemDTO compositeItemDTO : arg0 ) {
            list.add( toEntity( compositeItemDTO ) );
        }

        return list;
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
