package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Nature;
import com.awtar.myapp.service.dto.NatureDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-29T20:24:18+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class NatureMapperImpl implements NatureMapper {

    @Override
    public Nature toEntity(NatureDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Nature nature = new Nature();

        nature.setId( dto.getId() );
        nature.setName( dto.getName() );
        nature.setDestinedTo( dto.getDestinedTo() );
        nature.setNecessityValue( dto.getNecessityValue() );
        nature.setArchivated( dto.getArchivated() );

        return nature;
    }

    @Override
    public NatureDTO toDto(Nature entity) {
        if ( entity == null ) {
            return null;
        }

        NatureDTO natureDTO = new NatureDTO();

        natureDTO.setId( entity.getId() );
        natureDTO.setName( entity.getName() );
        natureDTO.setDestinedTo( entity.getDestinedTo() );
        natureDTO.setNecessityValue( entity.getNecessityValue() );
        natureDTO.setArchivated( entity.getArchivated() );

        return natureDTO;
    }

    @Override
    public List<Nature> toEntity(List<NatureDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Nature> list = new ArrayList<Nature>( dtoList.size() );
        for ( NatureDTO natureDTO : dtoList ) {
            list.add( toEntity( natureDTO ) );
        }

        return list;
    }

    @Override
    public List<NatureDTO> toDto(List<Nature> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<NatureDTO> list = new ArrayList<NatureDTO>( entityList.size() );
        for ( Nature nature : entityList ) {
            list.add( toDto( nature ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Nature entity, NatureDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getDestinedTo() != null ) {
            entity.setDestinedTo( dto.getDestinedTo() );
        }
        if ( dto.getNecessityValue() != null ) {
            entity.setNecessityValue( dto.getNecessityValue() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
    }
}
