package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.HealthStatusCategory;
import com.awtar.myapp.service.dto.HealthStatusCategoryDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-26T08:25:00+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class HealthStatusCategoryMapperImpl implements HealthStatusCategoryMapper {

    @Override
    public HealthStatusCategory toEntity(HealthStatusCategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        HealthStatusCategory healthStatusCategory = new HealthStatusCategory();

        healthStatusCategory.setId( dto.getId() );
        healthStatusCategory.setName( dto.getName() );
        healthStatusCategory.setArchivated( dto.getArchivated() );

        return healthStatusCategory;
    }

    @Override
    public HealthStatusCategoryDTO toDto(HealthStatusCategory entity) {
        if ( entity == null ) {
            return null;
        }

        HealthStatusCategoryDTO healthStatusCategoryDTO = new HealthStatusCategoryDTO();

        healthStatusCategoryDTO.setId( entity.getId() );
        healthStatusCategoryDTO.setName( entity.getName() );
        healthStatusCategoryDTO.setArchivated( entity.getArchivated() );

        return healthStatusCategoryDTO;
    }

    @Override
    public List<HealthStatusCategory> toEntity(List<HealthStatusCategoryDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<HealthStatusCategory> list = new ArrayList<HealthStatusCategory>( dtoList.size() );
        for ( HealthStatusCategoryDTO healthStatusCategoryDTO : dtoList ) {
            list.add( toEntity( healthStatusCategoryDTO ) );
        }

        return list;
    }

    @Override
    public List<HealthStatusCategoryDTO> toDto(List<HealthStatusCategory> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<HealthStatusCategoryDTO> list = new ArrayList<HealthStatusCategoryDTO>( entityList.size() );
        for ( HealthStatusCategory healthStatusCategory : entityList ) {
            list.add( toDto( healthStatusCategory ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(HealthStatusCategory entity, HealthStatusCategoryDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
    }
}
