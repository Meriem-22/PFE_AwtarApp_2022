package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Item;
import com.awtar.myapp.domain.Nature;
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
public class ItemMapperImpl implements ItemMapper {

    @Override
    public void partialUpdate(Item arg0, ItemDTO arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getArchivated() != null ) {
            arg0.setArchivated( arg1.getArchivated() );
        }
        if ( arg1.getComposed() != null ) {
            arg0.setComposed( arg1.getComposed() );
        }
        if ( arg1.getGender() != null ) {
            arg0.setGender( arg1.getGender() );
        }
        if ( arg1.getId() != null ) {
            arg0.setId( arg1.getId() );
        }
        if ( arg1.getName() != null ) {
            arg0.setName( arg1.getName() );
        }
        if ( arg1.getNature() != null ) {
            if ( arg0.getNature() == null ) {
                arg0.setNature( new Nature() );
            }
            natureDTOToNature( arg1.getNature(), arg0.getNature() );
        }
        byte[] urlPhoto = arg1.getUrlPhoto();
        if ( urlPhoto != null ) {
            arg0.urlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        if ( arg1.getUrlPhotoContentType() != null ) {
            arg0.urlPhotoContentType( arg1.getUrlPhotoContentType() );
        }
    }

    @Override
    public List<ItemDTO> toDto(List<Item> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<ItemDTO> list = new ArrayList<ItemDTO>( arg0.size() );
        for ( Item item : arg0 ) {
            list.add( toDto( item ) );
        }

        return list;
    }

    @Override
    public Item toEntity(ItemDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Item item = new Item();

        item.setArchivated( arg0.getArchivated() );
        item.setComposed( arg0.getComposed() );
        item.setGender( arg0.getGender() );
        item.setId( arg0.getId() );
        item.setName( arg0.getName() );
        item.setNature( natureDTOToNature1( arg0.getNature() ) );
        byte[] urlPhoto = arg0.getUrlPhoto();
        if ( urlPhoto != null ) {
            item.urlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        item.urlPhotoContentType( arg0.getUrlPhotoContentType() );

        return item;
    }

    @Override
    public List<Item> toEntity(List<ItemDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Item> list = new ArrayList<Item>( arg0.size() );
        for ( ItemDTO itemDTO : arg0 ) {
            list.add( toEntity( itemDTO ) );
        }

        return list;
    }

    @Override
    public ItemDTO toDto(Item s) {
        if ( s == null ) {
            return null;
        }

        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setNature( toDtoNatureName( s.getNature() ) );
        itemDTO.setArchivated( s.getArchivated() );
        itemDTO.setComposed( s.getComposed() );
        itemDTO.setGender( s.getGender() );
        itemDTO.setId( s.getId() );
        itemDTO.setName( s.getName() );
        byte[] urlPhoto = s.getUrlPhoto();
        if ( urlPhoto != null ) {
            itemDTO.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        itemDTO.setUrlPhotoContentType( s.getUrlPhotoContentType() );

        return itemDTO;
    }

    @Override
    public NatureDTO toDtoNatureName(Nature nature) {
        if ( nature == null ) {
            return null;
        }

        NatureDTO natureDTO = new NatureDTO();

        natureDTO.setId( nature.getId() );
        natureDTO.setName( nature.getName() );

        return natureDTO;
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
}
