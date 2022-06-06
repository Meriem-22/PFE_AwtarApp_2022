package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.DonationsReceived;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import com.awtar.myapp.service.dto.DonationsReceivedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DonationsReceived} and its DTO {@link DonationsReceivedDTO}.
 */
@Mapper(componentModel = "spring")
public interface DonationsReceivedMapper extends EntityMapper<DonationsReceivedDTO, DonationsReceived> {
    @Mapping(target = "authorizingOfficer", source = "authorizingOfficer", qualifiedByName = "authorizingOfficerAbbreviation")
    DonationsReceivedDTO toDto(DonationsReceived s);

    @Named("authorizingOfficerAbbreviation")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "abbreviation", source = "abbreviation")
    AuthorizingOfficerDTO toDtoAuthorizingOfficerAbbreviation(AuthorizingOfficer authorizingOfficer);
}
