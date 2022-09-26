package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.SchoolLevel;
import com.awtar.myapp.service.dto.SchoolLevelDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-26T08:25:03+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class SchoolLevelMapperImpl implements SchoolLevelMapper {

    @Override
    public SchoolLevel toEntity(SchoolLevelDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SchoolLevel schoolLevel = new SchoolLevel();

        schoolLevel.setId( dto.getId() );
        schoolLevel.setSchoolLevel( dto.getSchoolLevel() );
        schoolLevel.setArchivated( dto.getArchivated() );

        return schoolLevel;
    }

    @Override
    public SchoolLevelDTO toDto(SchoolLevel entity) {
        if ( entity == null ) {
            return null;
        }

        SchoolLevelDTO schoolLevelDTO = new SchoolLevelDTO();

        schoolLevelDTO.setId( entity.getId() );
        schoolLevelDTO.setSchoolLevel( entity.getSchoolLevel() );
        schoolLevelDTO.setArchivated( entity.getArchivated() );

        return schoolLevelDTO;
    }

    @Override
    public List<SchoolLevel> toEntity(List<SchoolLevelDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<SchoolLevel> list = new ArrayList<SchoolLevel>( dtoList.size() );
        for ( SchoolLevelDTO schoolLevelDTO : dtoList ) {
            list.add( toEntity( schoolLevelDTO ) );
        }

        return list;
    }

    @Override
    public List<SchoolLevelDTO> toDto(List<SchoolLevel> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SchoolLevelDTO> list = new ArrayList<SchoolLevelDTO>( entityList.size() );
        for ( SchoolLevel schoolLevel : entityList ) {
            list.add( toDto( schoolLevel ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(SchoolLevel entity, SchoolLevelDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getSchoolLevel() != null ) {
            entity.setSchoolLevel( dto.getSchoolLevel() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
    }
}
