package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.City;
import com.awtar.myapp.domain.Establishment;
import com.awtar.myapp.domain.EstablishmentType;
import com.awtar.myapp.domain.Report;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.CityDTO;
import com.awtar.myapp.service.dto.EstablishmentDTO;
import com.awtar.myapp.service.dto.EstablishmentTypeDTO;
import com.awtar.myapp.service.dto.ProfileDTO;
import com.awtar.myapp.service.dto.ReportDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-12T16:07:06+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20221012-0724, environment: Java 17.0.4.1 (Eclipse Adoptium)"
)
@Component
public class ReportMapperImpl implements ReportMapper {

    @Override
    public Report toEntity(ReportDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Report report = new Report();

        report.setId( dto.getId() );
        report.setNature( dto.getNature() );
        report.setDate( dto.getDate() );
        byte[] urlEnclosed = dto.getUrlEnclosed();
        if ( urlEnclosed != null ) {
            report.setUrlEnclosed( Arrays.copyOf( urlEnclosed, urlEnclosed.length ) );
        }
        report.setUrlEnclosedContentType( dto.getUrlEnclosedContentType() );
        report.setArchivated( dto.getArchivated() );
        report.establishment( establishmentDTOToEstablishment( dto.getEstablishment() ) );

        return report;
    }

    @Override
    public List<Report> toEntity(List<ReportDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Report> list = new ArrayList<Report>( dtoList.size() );
        for ( ReportDTO reportDTO : dtoList ) {
            list.add( toEntity( reportDTO ) );
        }

        return list;
    }

    @Override
    public List<ReportDTO> toDto(List<Report> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ReportDTO> list = new ArrayList<ReportDTO>( entityList.size() );
        for ( Report report : entityList ) {
            list.add( toDto( report ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Report entity, ReportDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getNature() != null ) {
            entity.setNature( dto.getNature() );
        }
        if ( dto.getDate() != null ) {
            entity.setDate( dto.getDate() );
        }
        byte[] urlEnclosed = dto.getUrlEnclosed();
        if ( urlEnclosed != null ) {
            entity.setUrlEnclosed( Arrays.copyOf( urlEnclosed, urlEnclosed.length ) );
        }
        if ( dto.getUrlEnclosedContentType() != null ) {
            entity.setUrlEnclosedContentType( dto.getUrlEnclosedContentType() );
        }
        if ( dto.getArchivated() != null ) {
            entity.setArchivated( dto.getArchivated() );
        }
        if ( dto.getEstablishment() != null ) {
            if ( entity.getEstablishment() == null ) {
                entity.establishment( new Establishment() );
            }
            establishmentDTOToEstablishment1( dto.getEstablishment(), entity.getEstablishment() );
        }
    }

    @Override
    public ReportDTO toDto(Report s) {
        if ( s == null ) {
            return null;
        }

        ReportDTO reportDTO = new ReportDTO();

        reportDTO.setEstablishment( toDtoEstablishmentName( s.getEstablishment() ) );
        reportDTO.setId( s.getId() );
        reportDTO.setNature( s.getNature() );
        reportDTO.setDate( s.getDate() );
        byte[] urlEnclosed = s.getUrlEnclosed();
        if ( urlEnclosed != null ) {
            reportDTO.setUrlEnclosed( Arrays.copyOf( urlEnclosed, urlEnclosed.length ) );
        }
        reportDTO.setUrlEnclosedContentType( s.getUrlEnclosedContentType() );
        reportDTO.setArchivated( s.getArchivated() );

        return reportDTO;
    }

    @Override
    public EstablishmentDTO toDtoEstablishmentName(Establishment establishment) {
        if ( establishment == null ) {
            return null;
        }

        EstablishmentDTO establishmentDTO = new EstablishmentDTO();

        establishmentDTO.setId( establishment.getId() );
        establishmentDTO.setName( establishment.getName() );

        return establishmentDTO;
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

    protected Establishment establishmentDTOToEstablishment(EstablishmentDTO establishmentDTO) {
        if ( establishmentDTO == null ) {
            return null;
        }

        Establishment establishment = new Establishment();

        establishment.setAuthorizingOfficer( profileDTOToAuthorizingOfficer( establishmentDTO.getAuthorizingOfficer() ) );
        establishment.setTutor( profileDTOToTutor( establishmentDTO.getTutor() ) );
        establishment.setId( establishmentDTO.getId() );
        establishment.setName( establishmentDTO.getName() );
        establishment.setActivity( establishmentDTO.getActivity() );
        establishment.setManager( establishmentDTO.getManager() );
        establishment.setManagerCin( establishmentDTO.getManagerCin() );
        establishment.setContact( establishmentDTO.getContact() );
        establishment.setAdress( establishmentDTO.getAdress() );
        establishment.setCp( establishmentDTO.getCp() );
        establishment.setRegion( establishmentDTO.getRegion() );
        establishment.setPhone( establishmentDTO.getPhone() );
        establishment.setFax( establishmentDTO.getFax() );
        establishment.setEmail( establishmentDTO.getEmail() );
        establishment.setSite( establishmentDTO.getSite() );
        establishment.setRemark( establishmentDTO.getRemark() );
        establishment.establishmentType( establishmentTypeDTOToEstablishmentType( establishmentDTO.getEstablishmentType() ) );
        establishment.city( cityDTOToCity( establishmentDTO.getCity() ) );

        return establishment;
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

    protected void establishmentDTOToEstablishment1(EstablishmentDTO establishmentDTO, Establishment mappingTarget) {
        if ( establishmentDTO == null ) {
            return;
        }

        if ( establishmentDTO.getAuthorizingOfficer() != null ) {
            if ( mappingTarget.getAuthorizingOfficer() == null ) {
                mappingTarget.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            profileDTOToAuthorizingOfficer1( establishmentDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
        if ( establishmentDTO.getTutor() != null ) {
            if ( mappingTarget.getTutor() == null ) {
                mappingTarget.setTutor( new Tutor() );
            }
            profileDTOToTutor1( establishmentDTO.getTutor(), mappingTarget.getTutor() );
        }
        if ( establishmentDTO.getId() != null ) {
            mappingTarget.setId( establishmentDTO.getId() );
        }
        if ( establishmentDTO.getName() != null ) {
            mappingTarget.setName( establishmentDTO.getName() );
        }
        if ( establishmentDTO.getActivity() != null ) {
            mappingTarget.setActivity( establishmentDTO.getActivity() );
        }
        if ( establishmentDTO.getManager() != null ) {
            mappingTarget.setManager( establishmentDTO.getManager() );
        }
        if ( establishmentDTO.getManagerCin() != null ) {
            mappingTarget.setManagerCin( establishmentDTO.getManagerCin() );
        }
        if ( establishmentDTO.getContact() != null ) {
            mappingTarget.setContact( establishmentDTO.getContact() );
        }
        if ( establishmentDTO.getAdress() != null ) {
            mappingTarget.setAdress( establishmentDTO.getAdress() );
        }
        if ( establishmentDTO.getCp() != null ) {
            mappingTarget.setCp( establishmentDTO.getCp() );
        }
        if ( establishmentDTO.getRegion() != null ) {
            mappingTarget.setRegion( establishmentDTO.getRegion() );
        }
        if ( establishmentDTO.getPhone() != null ) {
            mappingTarget.setPhone( establishmentDTO.getPhone() );
        }
        if ( establishmentDTO.getFax() != null ) {
            mappingTarget.setFax( establishmentDTO.getFax() );
        }
        if ( establishmentDTO.getEmail() != null ) {
            mappingTarget.setEmail( establishmentDTO.getEmail() );
        }
        if ( establishmentDTO.getSite() != null ) {
            mappingTarget.setSite( establishmentDTO.getSite() );
        }
        if ( establishmentDTO.getRemark() != null ) {
            mappingTarget.setRemark( establishmentDTO.getRemark() );
        }
        if ( establishmentDTO.getEstablishmentType() != null ) {
            if ( mappingTarget.getEstablishmentType() == null ) {
                mappingTarget.establishmentType( new EstablishmentType() );
            }
            establishmentTypeDTOToEstablishmentType1( establishmentDTO.getEstablishmentType(), mappingTarget.getEstablishmentType() );
        }
        if ( establishmentDTO.getCity() != null ) {
            if ( mappingTarget.getCity() == null ) {
                mappingTarget.city( new City() );
            }
            cityDTOToCity1( establishmentDTO.getCity(), mappingTarget.getCity() );
        }
    }
}
