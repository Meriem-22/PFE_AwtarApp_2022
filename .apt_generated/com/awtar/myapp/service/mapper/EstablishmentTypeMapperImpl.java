package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.EstablishmentType;
import com.awtar.myapp.service.dto.EstablishmentTypeDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-16T07:55:45+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class EstablishmentTypeMapperImpl implements EstablishmentTypeMapper {

    @Override
    public EstablishmentType toEntity(EstablishmentTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        EstablishmentType establishmentType = new EstablishmentType();

        establishmentType.setId( dto.getId() );
        establishmentType.setTypeName( dto.getTypeName() );
        establishmentType.setArchivated( dto.getArchivated() );

        return establishmentType;
    }

    @Override
    public EstablishmentTypeDTO toDto(EstablishmentType entity) {
        if ( entity == null ) {
            return null;
        }

        EstablishmentTypeDTO establishmentTypeDTO = new EstablishmentTypeDTO();

        establishmentTypeDTO.setId( entity.getId() );
        establishmentTypeDTO.setTypeName( entity.getTypeName() );
        establishmentTypeDTO.setArchivated( entity.getArchivated() );

        return establishmentTypeDTO;
    }

    @Override
    public List<EstablishmentType> toEntity(List<EstablishmentTypeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<EstablishmentType> list = new ArrayList<EstablishmentType>( dtoList.size() );
        for ( EstablishmentTypeDTO establishmentTypeDTO : dtoList ) {
            list.add( toEntity( establishmentTypeDTO ) );
        }

        return list;
    }

    @Override
    public List<EstablishmentTypeDTO> toDto(List<EstablishmentType> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<EstablishmentTypeDTO> list = new ArrayList<EstablishmentTypeDTO>( entityList.size() );
        for ( EstablishmentType establishmentType : entityList ) {
            list.add( toDto( establishmentType ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(EstablishmentType entity, EstablishmentTypeDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getTypeName() != null ) {
            entity.setTypeName( dto.getTypeName() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
    }
}
