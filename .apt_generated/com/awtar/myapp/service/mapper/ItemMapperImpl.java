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
    date = "2022-09-21T08:01:12+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class ItemMapperImpl implements ItemMapper {

    @Override
    public Item toEntity(ItemDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Item item = new Item();

        item.setId( dto.getId() );
        item.setName( dto.getName() );
        byte[] urlPhoto = dto.getUrlPhoto();
        if ( urlPhoto != null ) {
            item.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        item.setUrlPhotoContentType( dto.getUrlPhotoContentType() );
        item.setGender( dto.getGender() );
        item.setComposed( dto.getComposed() );
        item.setArchivated( dto.getArchivated() );
        item.nature( natureDTOToNature( dto.getNature() ) );

        return item;
    }

    @Override
    public List<Item> toEntity(List<ItemDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Item> list = new ArrayList<Item>( dtoList.size() );
        for ( ItemDTO itemDTO : dtoList ) {
            list.add( toEntity( itemDTO ) );
        }

        return list;
    }

    @Override
    public List<ItemDTO> toDto(List<Item> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ItemDTO> list = new ArrayList<ItemDTO>( entityList.size() );
        for ( Item item : entityList ) {
            list.add( toDto( item ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Item entity, ItemDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        byte[] urlPhoto = dto.getUrlPhoto();
        if ( urlPhoto != null ) {
            entity.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        if ( dto.getUrlPhotoContentType() != null ) {
            entity.setUrlPhotoContentType( dto.getUrlPhotoContentType() );
        }
        if ( dto.getGender() != null ) {
            entity.setGender( dto.getGender() );
        }
        if ( dto.getComposed() != null ) {
            entity.setComposed( dto.getComposed() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
        if ( dto.getNature() != null ) {
            if ( entity.getNature() == null ) {
                entity.nature( new Nature() );
            }
            natureDTOToNature1( dto.getNature(), entity.getNature() );
        }
    }

    @Override
    public ItemDTO toDto(Item s) {
        if ( s == null ) {
            return null;
        }

        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setNature( toDtoNatureName( s.getNature() ) );
        itemDTO.setId( s.getId() );
        itemDTO.setName( s.getName() );
        byte[] urlPhoto = s.getUrlPhoto();
        if ( urlPhoto != null ) {
            itemDTO.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        itemDTO.setUrlPhotoContentType( s.getUrlPhotoContentType() );
        itemDTO.setGender( s.getGender() );
        itemDTO.setComposed( s.getComposed() );
        itemDTO.setArchivated( s.getArchivated() );

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
}
