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
    date = "2022-09-21T09:01:57+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class SchoolLevelItemMapperImpl implements SchoolLevelItemMapper {

    @Override
    public void partialUpdate(SchoolLevelItem arg0, SchoolLevelItemDTO arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getArchivated() != null ) {
            arg0.setArchivated( arg1.getArchivated() );
        }
        if ( arg1.getId() != null ) {
            arg0.setId( arg1.getId() );
        }
        if ( arg1.getIsSchoolItem() != null ) {
            arg0.setIsSchoolItem( arg1.getIsSchoolItem() );
        }
        if ( arg1.getItem() != null ) {
            if ( arg0.getItem() == null ) {
                arg0.setItem( new Item() );
            }
            itemDTOToItem( arg1.getItem(), arg0.getItem() );
        }
        if ( arg1.getQuantityNeeded() != null ) {
            arg0.setQuantityNeeded( arg1.getQuantityNeeded() );
        }
        if ( arg1.getSchoolLevel() != null ) {
            if ( arg0.getSchoolLevel() == null ) {
                arg0.setSchoolLevel( new SchoolLevel() );
            }
            schoolLevelDTOToSchoolLevel( arg1.getSchoolLevel(), arg0.getSchoolLevel() );
        }
    }

    @Override
    public List<SchoolLevelItemDTO> toDto(List<SchoolLevelItem> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<SchoolLevelItemDTO> list = new ArrayList<SchoolLevelItemDTO>( arg0.size() );
        for ( SchoolLevelItem schoolLevelItem : arg0 ) {
            list.add( toDto( schoolLevelItem ) );
        }

        return list;
    }

    @Override
    public SchoolLevelItem toEntity(SchoolLevelItemDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SchoolLevelItem schoolLevelItem = new SchoolLevelItem();

        schoolLevelItem.setArchivated( arg0.getArchivated() );
        schoolLevelItem.setId( arg0.getId() );
        schoolLevelItem.setIsSchoolItem( arg0.getIsSchoolItem() );
        schoolLevelItem.setItem( itemDTOToItem1( arg0.getItem() ) );
        schoolLevelItem.setQuantityNeeded( arg0.getQuantityNeeded() );
        schoolLevelItem.setSchoolLevel( schoolLevelDTOToSchoolLevel1( arg0.getSchoolLevel() ) );

        return schoolLevelItem;
    }

    @Override
    public List<SchoolLevelItem> toEntity(List<SchoolLevelItemDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<SchoolLevelItem> list = new ArrayList<SchoolLevelItem>( arg0.size() );
        for ( SchoolLevelItemDTO schoolLevelItemDTO : arg0 ) {
            list.add( toEntity( schoolLevelItemDTO ) );
        }

        return list;
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

    protected void schoolLevelDTOToSchoolLevel(SchoolLevelDTO schoolLevelDTO, SchoolLevel mappingTarget) {
        if ( schoolLevelDTO == null ) {
            return;
        }

        if ( schoolLevelDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( schoolLevelDTO.getArchivated() );
        }
        if ( schoolLevelDTO.getId() != null ) {
            mappingTarget.setId( schoolLevelDTO.getId() );
        }
        if ( schoolLevelDTO.getSchoolLevel() != null ) {
            mappingTarget.setSchoolLevel( schoolLevelDTO.getSchoolLevel() );
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

    protected SchoolLevel schoolLevelDTOToSchoolLevel1(SchoolLevelDTO schoolLevelDTO) {
        if ( schoolLevelDTO == null ) {
            return null;
        }

        SchoolLevel schoolLevel = new SchoolLevel();

        schoolLevel.setArchivated( schoolLevelDTO.getArchivated() );
        schoolLevel.setId( schoolLevelDTO.getId() );
        schoolLevel.setSchoolLevel( schoolLevelDTO.getSchoolLevel() );

        return schoolLevel;
    }
}
