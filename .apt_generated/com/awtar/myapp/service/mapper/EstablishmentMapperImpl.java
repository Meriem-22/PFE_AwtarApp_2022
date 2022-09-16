package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.City;
import com.awtar.myapp.domain.Establishment;
import com.awtar.myapp.domain.EstablishmentType;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.CityDTO;
import com.awtar.myapp.service.dto.EstablishmentDTO;
import com.awtar.myapp.service.dto.EstablishmentTypeDTO;
import com.awtar.myapp.service.dto.ProfileDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-16T07:55:46+0200",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class EstablishmentMapperImpl implements EstablishmentMapper {

    @Override
    public Establishment toEntity(EstablishmentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Establishment establishment = new Establishment();

        establishment.setAuthorizingOfficer( profileDTOToAuthorizingOfficer( dto.getAuthorizingOfficer() ) );
        establishment.setTutor( profileDTOToTutor( dto.getTutor() ) );
        establishment.setId( dto.getId() );
        establishment.setName( dto.getName() );
        establishment.setActivity( dto.getActivity() );
        establishment.setManager( dto.getManager() );
        establishment.setManagerCin( dto.getManagerCin() );
        establishment.setContact( dto.getContact() );
        establishment.setAdress( dto.getAdress() );
        establishment.setCp( dto.getCp() );
        establishment.setRegion( dto.getRegion() );
        establishment.setPhone( dto.getPhone() );
        establishment.setFax( dto.getFax() );
        establishment.setEmail( dto.getEmail() );
        establishment.setSite( dto.getSite() );
        establishment.setRemark( dto.getRemark() );
        establishment.establishmentType( establishmentTypeDTOToEstablishmentType( dto.getEstablishmentType() ) );
        establishment.city( cityDTOToCity( dto.getCity() ) );

        return establishment;
    }

    @Override
    public List<Establishment> toEntity(List<EstablishmentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Establishment> list = new ArrayList<Establishment>( dtoList.size() );
        for ( EstablishmentDTO establishmentDTO : dtoList ) {
            list.add( toEntity( establishmentDTO ) );
        }

        return list;
    }

    @Override
    public List<EstablishmentDTO> toDto(List<Establishment> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<EstablishmentDTO> list = new ArrayList<EstablishmentDTO>( entityList.size() );
        for ( Establishment establishment : entityList ) {
            list.add( toDto( establishment ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Establishment entity, EstablishmentDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getAuthorizingOfficer() != null ) {
            if ( entity.getAuthorizingOfficer() == null ) {
                entity.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            profileDTOToAuthorizingOfficer1( dto.getAuthorizingOfficer(), entity.getAuthorizingOfficer() );
        }
        if ( dto.getTutor() != null ) {
            if ( entity.getTutor() == null ) {
                entity.setTutor( new Tutor() );
            }
            profileDTOToTutor1( dto.getTutor(), entity.getTutor() );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getActivity() != null ) {
            entity.setActivity( dto.getActivity() );
        }
        if ( dto.getManager() != null ) {
            entity.setManager( dto.getManager() );
        }
        if ( dto.getManagerCin() != null ) {
            entity.setManagerCin( dto.getManagerCin() );
        }
        if ( dto.getContact() != null ) {
            entity.setContact( dto.getContact() );
        }
        if ( dto.getAdress() != null ) {
            entity.setAdress( dto.getAdress() );
        }
        if ( dto.getCp() != null ) {
            entity.setCp( dto.getCp() );
        }
        if ( dto.getRegion() != null ) {
            entity.setRegion( dto.getRegion() );
        }
        if ( dto.getPhone() != null ) {
            entity.setPhone( dto.getPhone() );
        }
        if ( dto.getFax() != null ) {
            entity.setFax( dto.getFax() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getSite() != null ) {
            entity.setSite( dto.getSite() );
        }
        if ( dto.getRemark() != null ) {
            entity.setRemark( dto.getRemark() );
        }
        if ( dto.getEstablishmentType() != null ) {
            if ( entity.getEstablishmentType() == null ) {
                entity.establishmentType( new EstablishmentType() );
            }
            establishmentTypeDTOToEstablishmentType1( dto.getEstablishmentType(), entity.getEstablishmentType() );
        }
        if ( dto.getCity() != null ) {
            if ( entity.getCity() == null ) {
                entity.city( new City() );
            }
            cityDTOToCity1( dto.getCity(), entity.getCity() );
        }
    }

    @Override
    public EstablishmentDTO toDto(Establishment s) {
        if ( s == null ) {
            return null;
        }

        EstablishmentDTO establishmentDTO = new EstablishmentDTO();

        establishmentDTO.setEstablishmentType( toDtoEstablishmentTypeTypeName( s.getEstablishmentType() ) );
        establishmentDTO.setCity( toDtoCityName( s.getCity() ) );
        establishmentDTO.setId( s.getId() );
        establishmentDTO.setName( s.getName() );
        establishmentDTO.setActivity( s.getActivity() );
        establishmentDTO.setManager( s.getManager() );
        establishmentDTO.setManagerCin( s.getManagerCin() );
        establishmentDTO.setContact( s.getContact() );
        establishmentDTO.setAdress( s.getAdress() );
        establishmentDTO.setCp( s.getCp() );
        establishmentDTO.setRegion( s.getRegion() );
        establishmentDTO.setPhone( s.getPhone() );
        establishmentDTO.setFax( s.getFax() );
        establishmentDTO.setEmail( s.getEmail() );
        establishmentDTO.setSite( s.getSite() );
        establishmentDTO.setRemark( s.getRemark() );
        establishmentDTO.setAuthorizingOfficer( authorizingOfficerToProfileDTO( s.getAuthorizingOfficer() ) );
        establishmentDTO.setTutor( tutorToProfileDTO( s.getTutor() ) );

        return establishmentDTO;
    }

    @Override
    public EstablishmentTypeDTO toDtoEstablishmentTypeTypeName(EstablishmentType establishmentType) {
        if ( establishmentType == null ) {
            return null;
        }

        EstablishmentTypeDTO establishmentTypeDTO = new EstablishmentTypeDTO();

        establishmentTypeDTO.setId( establishmentType.getId() );
        establishmentTypeDTO.setTypeName( establishmentType.getTypeName() );

        return establishmentTypeDTO;
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

    protected AuthorizingOfficer profileDTOToAuthorizingOfficer(ProfileDTO profileDTO) {
        if ( profileDTO == null ) {
            return null;
        }

        AuthorizingOfficer authorizingOfficer = new AuthorizingOfficer();

        authorizingOfficer.setId( profileDTO.getId() );
        authorizingOfficer.setActivity( profileDTO.getActivity() );

        return authorizingOfficer;
    }

    protected Tutor profileDTOToTutor(ProfileDTO profileDTO) {
        if ( profileDTO == null ) {
            return null;
        }

        Tutor tutor = new Tutor();

        tutor.setId( profileDTO.getId() );
        tutor.setActivity( profileDTO.getActivity() );

        return tutor;
    }

    protected EstablishmentType establishmentTypeDTOToEstablishmentType(EstablishmentTypeDTO establishmentTypeDTO) {
        if ( establishmentTypeDTO == null ) {
            return null;
        }

        EstablishmentType establishmentType = new EstablishmentType();

        establishmentType.setId( establishmentTypeDTO.getId() );
        establishmentType.setTypeName( establishmentTypeDTO.getTypeName() );
        establishmentType.setArchivated( establishmentTypeDTO.getArchivated() );

        return establishmentType;
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

    protected void profileDTOToAuthorizingOfficer1(ProfileDTO profileDTO, AuthorizingOfficer mappingTarget) {
        if ( profileDTO == null ) {
            return;
        }

        if ( profileDTO.getId() != null ) {
            mappingTarget.setId( profileDTO.getId() );
        }
        if ( profileDTO.getActivity() != null ) {
            mappingTarget.setActivity( profileDTO.getActivity() );
        }
    }

    protected void profileDTOToTutor1(ProfileDTO profileDTO, Tutor mappingTarget) {
        if ( profileDTO == null ) {
            return;
        }

        if ( profileDTO.getId() != null ) {
            mappingTarget.setId( profileDTO.getId() );
        }
        if ( profileDTO.getActivity() != null ) {
            mappingTarget.setActivity( profileDTO.getActivity() );
        }
    }

    protected void establishmentTypeDTOToEstablishmentType1(EstablishmentTypeDTO establishmentTypeDTO, EstablishmentType mappingTarget) {
        if ( establishmentTypeDTO == null ) {
            return;
        }

        if ( establishmentTypeDTO.getId() != null ) {
            mappingTarget.setId( establishmentTypeDTO.getId() );
        }
        if ( establishmentTypeDTO.getTypeName() != null ) {
            mappingTarget.setTypeName( establishmentTypeDTO.getTypeName() );
        }
        if ( establishmentTypeDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( establishmentTypeDTO.getArchivated() );
        }
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

    protected ProfileDTO authorizingOfficerToProfileDTO(AuthorizingOfficer authorizingOfficer) {
        if ( authorizingOfficer == null ) {
            return null;
        }

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setId( authorizingOfficer.getId() );
        profileDTO.setActivity( authorizingOfficer.getActivity() );

        return profileDTO;
    }

    protected ProfileDTO tutorToProfileDTO(Tutor tutor) {
        if ( tutor == null ) {
            return null;
        }

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setId( tutor.getId() );
        profileDTO.setActivity( tutor.getActivity() );

        return profileDTO;
    }
}
