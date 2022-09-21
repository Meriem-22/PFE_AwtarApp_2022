package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.City;
import com.awtar.myapp.domain.EducationalInstitution;
import com.awtar.myapp.service.dto.CityDTO;
import com.awtar.myapp.service.dto.EducationalInstitutionDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-21T08:01:11+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class EducationalInstitutionMapperImpl implements EducationalInstitutionMapper {

    @Override
    public EducationalInstitution toEntity(EducationalInstitutionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        EducationalInstitution educationalInstitution = new EducationalInstitution();

        educationalInstitution.setId( dto.getId() );
        educationalInstitution.setName( dto.getName() );
        educationalInstitution.setAdress( dto.getAdress() );
        educationalInstitution.setArchivated( dto.getArchivated() );
        educationalInstitution.city( cityDTOToCity( dto.getCity() ) );

        return educationalInstitution;
    }

    @Override
    public List<EducationalInstitution> toEntity(List<EducationalInstitutionDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<EducationalInstitution> list = new ArrayList<EducationalInstitution>( dtoList.size() );
        for ( EducationalInstitutionDTO educationalInstitutionDTO : dtoList ) {
            list.add( toEntity( educationalInstitutionDTO ) );
        }

        return list;
    }

    @Override
    public List<EducationalInstitutionDTO> toDto(List<EducationalInstitution> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<EducationalInstitutionDTO> list = new ArrayList<EducationalInstitutionDTO>( entityList.size() );
        for ( EducationalInstitution educationalInstitution : entityList ) {
            list.add( toDto( educationalInstitution ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(EducationalInstitution entity, EducationalInstitutionDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getAdress() != null ) {
            entity.setAdress( dto.getAdress() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
        if ( dto.getCity() != null ) {
            if ( entity.getCity() == null ) {
                entity.city( new City() );
            }
            cityDTOToCity1( dto.getCity(), entity.getCity() );
        }
    }

    @Override
    public EducationalInstitutionDTO toDto(EducationalInstitution s) {
        if ( s == null ) {
            return null;
        }

        EducationalInstitutionDTO educationalInstitutionDTO = new EducationalInstitutionDTO();

        educationalInstitutionDTO.setCity( toDtoCityName( s.getCity() ) );
        educationalInstitutionDTO.setId( s.getId() );
        educationalInstitutionDTO.setName( s.getName() );
        educationalInstitutionDTO.setAdress( s.getAdress() );
        educationalInstitutionDTO.setArchivated( s.getArchivated() );

        return educationalInstitutionDTO;
    }

    @Override
    public CityDTO toDtoCityName(City city) {
        if ( city == null ) {
            return null;
        }

        CityDTO cityDTO = new CityDTO();

        cityDTO.setId( city.getId() );
        cityDTO.setName( city.getName() );

        return cityDTO;
    }

    protected City cityDTOToCity(CityDTO cityDTO) {
        if ( cityDTO == null ) {
            return null;
        }

        City city = new City();

        city.setId( cityDTO.getId() );
        city.setName( cityDTO.getName() );
        city.setGovernorate( cityDTO.getGovernorate() );
        city.setIsGovernorate( cityDTO.getIsGovernorate() );
        city.setArchivated( cityDTO.getArchivated() );

        return city;
    }

    protected void cityDTOToCity1(CityDTO cityDTO, City mappingTarget) {
        if ( cityDTO == null ) {
            return;
        }

        if ( cityDTO.getId() != null ) {
            mappingTarget.setId( cityDTO.getId() );
        }
        if ( cityDTO.getName() != null ) {
            mappingTarget.setName( cityDTO.getName() );
        }
        if ( cityDTO.getGovernorate() != null ) {
            mappingTarget.setGovernorate( cityDTO.getGovernorate() );
        }
        if ( cityDTO.getIsGovernorate() != null ) {
            mappingTarget.setIsGovernorate( cityDTO.getIsGovernorate() );
        }
        if ( cityDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( cityDTO.getArchivated() );
        }
    }
}
