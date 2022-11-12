package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-12T16:07:06+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20221012-0724, environment: Java 17.0.4.1 (Eclipse Adoptium)"
)
@Component
public class AuthorizingOfficerMapperImpl implements AuthorizingOfficerMapper {

    @Override
    public AuthorizingOfficer toEntity(AuthorizingOfficerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        AuthorizingOfficer authorizingOfficer = new AuthorizingOfficer();

        authorizingOfficer.setId( dto.getId() );
        authorizingOfficer.setAbbreviation( dto.getAbbreviation() );
        authorizingOfficer.setActivity( dto.getActivity() );
        authorizingOfficer.setManager( dto.getManager() );
        authorizingOfficer.setManagerCin( dto.getManagerCin() );

        return authorizingOfficer;
    }

    @Override
    public AuthorizingOfficerDTO toDto(AuthorizingOfficer entity) {
        if ( entity == null ) {
            return null;
        }

        AuthorizingOfficerDTO authorizingOfficerDTO = new AuthorizingOfficerDTO();

        authorizingOfficerDTO.setId( entity.getId() );
        authorizingOfficerDTO.setAbbreviation( entity.getAbbreviation() );
        authorizingOfficerDTO.setActivity( entity.getActivity() );
        authorizingOfficerDTO.setManager( entity.getManager() );
        authorizingOfficerDTO.setManagerCin( entity.getManagerCin() );

        return authorizingOfficerDTO;
    }

    @Override
    public List<AuthorizingOfficer> toEntity(List<AuthorizingOfficerDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<AuthorizingOfficer> list = new ArrayList<AuthorizingOfficer>( dtoList.size() );
        for ( AuthorizingOfficerDTO authorizingOfficerDTO : dtoList ) {
            list.add( toEntity( authorizingOfficerDTO ) );
        }

        return list;
    }

    @Override
    public List<AuthorizingOfficerDTO> toDto(List<AuthorizingOfficer> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AuthorizingOfficerDTO> list = new ArrayList<AuthorizingOfficerDTO>( entityList.size() );
        for ( AuthorizingOfficer authorizingOfficer : entityList ) {
            list.add( toDto( authorizingOfficer ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(AuthorizingOfficer entity, AuthorizingOfficerDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getAbbreviation() != null ) {
            entity.setAbbreviation( dto.getAbbreviation() );
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
    }
}
