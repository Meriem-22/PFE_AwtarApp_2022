package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Item;
import com.awtar.myapp.domain.Nature;
import com.awtar.myapp.domain.SchoolLevel;
import com.awtar.myapp.domain.SchoolLevelItem;
import com.awtar.myapp.service.dto.ItemDTO;
import com.awtar.myapp.service.dto.NatureDTO;
import com.awtar.myapp.service.dto.SchoolLevelDTO;
import com.awtar.myapp.service.dto.SchoolLevelItemDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-06T08:12:10+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20221012-0724, environment: Java 17.0.5 (Eclipse Adoptium)"
)
@Component
public class SchoolLevelItemMapperImpl implements SchoolLevelItemMapper {

    @Override
    public SchoolLevelItem toEntity(SchoolLevelItemDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SchoolLevelItem schoolLevelItem = new SchoolLevelItem();

        schoolLevelItem.setId( dto.getId() );
        schoolLevelItem.setIsSchoolItem( dto.getIsSchoolItem() );
        schoolLevelItem.setQuantityNeeded( dto.getQuantityNeeded() );
        schoolLevelItem.setArchivated( dto.getArchivated() );
        schoolLevelItem.item( itemDTOToItem( dto.getItem() ) );
        schoolLevelItem.schoolLevel( schoolLevelDTOToSchoolLevel( dto.getSchoolLevel() ) );

        return schoolLevelItem;
    }

    @Override
    public List<SchoolLevelItem> toEntity(List<SchoolLevelItemDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SchoolLevelItem> list = new ArrayList<SchoolLevelItem>( dtoList.size() );
        for ( SchoolLevelItemDTO schoolLevelItemDTO : dtoList ) {
            list.add( toEntity( schoolLevelItemDTO ) );
        }

        return list;
    }

    @Override
    public List<SchoolLevelItemDTO> toDto(List<SchoolLevelItem> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SchoolLevelItemDTO> list = new ArrayList<SchoolLevelItemDTO>( entityList.size() );
        for ( SchoolLevelItem schoolLevelItem : entityList ) {
            list.add( toDto( schoolLevelItem ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(SchoolLevelItem entity, SchoolLevelItemDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getIsSchoolItem() != null ) {
            entity.setIsSchoolItem( dto.getIsSchoolItem() );
        }
        if ( dto.getQuantityNeeded() != null ) {
            entity.setQuantityNeeded( dto.getQuantityNeeded() );
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
        if ( dto.getSchoolLevel() != null ) {
            if ( entity.getSchoolLevel() == null ) {
                entity.schoolLevel( new SchoolLevel() );
            }
            schoolLevelDTOToSchoolLevel1( dto.getSchoolLevel(), entity.getSchoolLevel() );
        }
    }

    @Override
    public SchoolLevelItemDTO toDto(SchoolLevelItem s) {
        if ( s == null ) {
            return null;
        }

        SchoolLevelItemDTO schoolLevelItemDTO = new SchoolLevelItemDTO();

        schoolLevelItemDTO.setItem( toDtoItemName( s.getItem() ) );
        schoolLevelItemDTO.setSchoolLevel( toDtoSchoolLevelSchoolLevel( s.getSchoolLevel() ) );
        schoolLevelItemDTO.setId( s.getId() );
        schoolLevelItemDTO.setIsSchoolItem( s.getIsSchoolItem() );
        schoolLevelItemDTO.setQuantityNeeded( s.getQuantityNeeded() );
        schoolLevelItemDTO.setArchivated( s.getArchivated() );

        return schoolLevelItemDTO;
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
    public SchoolLevelDTO toDtoSchoolLevelSchoolLevel(SchoolLevel schoolLevel) {
        if ( schoolLevel == null ) {
            return null;
        }

        SchoolLevelDTO schoolLevelDTO = new SchoolLevelDTO();

        schoolLevelDTO.setId( schoolLevel.getId() );
        schoolLevelDTO.setSchoolLevel( schoolLevel.getSchoolLevel() );

        return schoolLevelDTO;
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

    protected SchoolLevel schoolLevelDTOToSchoolLevel(SchoolLevelDTO schoolLevelDTO) {
        if ( schoolLevelDTO == null ) {
            return null;
        }

        SchoolLevel schoolLevel = new SchoolLevel();

        schoolLevel.setId( schoolLevelDTO.getId() );
        schoolLevel.setSchoolLevel( schoolLevelDTO.getSchoolLevel() );
        schoolLevel.setArchivated( schoolLevelDTO.getArchivated() );

        return schoolLevel;
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

    protected void schoolLevelDTOToSchoolLevel1(SchoolLevelDTO schoolLevelDTO, SchoolLevel mappingTarget) {
        if ( schoolLevelDTO == null ) {
            return;
        }

        if ( schoolLevelDTO.getId() != null ) {
            mappingTarget.setId( schoolLevelDTO.getId() );
        }
        if ( schoolLevelDTO.getSchoolLevel() != null ) {
            mappingTarget.setSchoolLevel( schoolLevelDTO.getSchoolLevel() );
        }
        if ( schoolLevelDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( schoolLevelDTO.getArchivated() );
        }
    }
}
