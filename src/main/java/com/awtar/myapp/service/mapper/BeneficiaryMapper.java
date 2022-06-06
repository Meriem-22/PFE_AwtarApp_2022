package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.Beneficiary;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import com.awtar.myapp.service.dto.BeneficiaryDTO;
import com.awtar.myapp.service.dto.TutorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Beneficiary} and its DTO {@link BeneficiaryDTO}.
 */
@Mapper(componentModel = "spring")
public interface BeneficiaryMapper extends EntityMapper<BeneficiaryDTO, Beneficiary> {
    @Mapping(target = "authorizingOfficer", source = "authorizingOfficer", qualifiedByName = "authorizingOfficerAbbreviation")
    @Mapping(target = "tutor", source = "tutor", qualifiedByName = "tutorId")
    BeneficiaryDTO toDto(Beneficiary s);

    @Named("authorizingOfficerAbbreviation")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "abbreviation", source = "abbreviation")
    AuthorizingOfficerDTO toDtoAuthorizingOfficerAbbreviation(AuthorizingOfficer authorizingOfficer);

    @Named("tutorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TutorDTO toDtoTutorId(Tutor tutor);
}
