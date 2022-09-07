package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.ChildStatus;
import com.awtar.myapp.service.dto.ChildStatusDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-07T09:58:07+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class ChildStatusMapperImpl implements ChildStatusMapper {

    @Override
    public ChildStatus toEntity(ChildStatusDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ChildStatus childStatus = new ChildStatus();

        childStatus.setId( dto.getId() );
        childStatus.setStaus( dto.getStaus() );
        childStatus.setArchivated( dto.getArchivated() );

        return childStatus;
    }

    @Override
    public ChildStatusDTO toDto(ChildStatus entity) {
        if ( entity == null ) {
            return null;
        }

        ChildStatusDTO childStatusDTO = new ChildStatusDTO();

        childStatusDTO.setId( entity.getId() );
        childStatusDTO.setStaus( entity.getStaus() );
        childStatusDTO.setArchivated( entity.getArchivated() );

        return childStatusDTO;
    }

    @Override
    public List<ChildStatus> toEntity(List<ChildStatusDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ChildStatus> list = new ArrayList<ChildStatus>( dtoList.size() );
        for ( ChildStatusDTO childStatusDTO : dtoList ) {
            list.add( toEntity( childStatusDTO ) );
        }

        return list;
    }

    @Override
    public List<ChildStatusDTO> toDto(List<ChildStatus> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ChildStatusDTO> list = new ArrayList<ChildStatusDTO>( entityList.size() );
        for ( ChildStatus childStatus : entityList ) {
            list.add( toDto( childStatus ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(ChildStatus entity, ChildStatusDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getStaus() != null ) {
            entity.setStaus( dto.getStaus() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
    }
}
