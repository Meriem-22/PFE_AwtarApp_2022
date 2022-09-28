package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.City;
import com.awtar.myapp.service.dto.CityDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-28T13:33:54+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class CityMapperImpl implements CityMapper {

    @Override
    public City toEntity(CityDTO dto) {
        if ( dto == null ) {
            return null;
        }

        City city = new City();

        city.setId( dto.getId() );
        city.setName( dto.getName() );
        city.setGovernorate( dto.getGovernorate() );
        city.setIsGovernorate( dto.getIsGovernorate() );
        city.setArchivated( dto.getArchivated() );

        return city;
    }

    @Override
    public CityDTO toDto(City entity) {
        if ( entity == null ) {
            return null;
        }

        CityDTO cityDTO = new CityDTO();

        cityDTO.setId( entity.getId() );
        cityDTO.setName( entity.getName() );
        cityDTO.setGovernorate( entity.getGovernorate() );
        cityDTO.setIsGovernorate( entity.getIsGovernorate() );
        cityDTO.setArchivated( entity.getArchivated() );

        return cityDTO;
    }

    @Override
    public List<City> toEntity(List<CityDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<City> list = new ArrayList<City>( dtoList.size() );
        for ( CityDTO cityDTO : dtoList ) {
            list.add( toEntity( cityDTO ) );
        }

        return list;
    }

    @Override
    public List<CityDTO> toDto(List<City> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CityDTO> list = new ArrayList<CityDTO>( entityList.size() );
        for ( City city : entityList ) {
            list.add( toDto( city ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(City entity, CityDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getGovernorate() != null ) {
            entity.setGovernorate( dto.getGovernorate() );
        }
        if ( dto.getIsGovernorate() != null ) {
            entity.setIsGovernorate( dto.getIsGovernorate() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
    }
}
