package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Beneficiary;
import com.awtar.myapp.domain.Visit;
import com.awtar.myapp.service.dto.BeneficiaryDTO;
import com.awtar.myapp.service.dto.VisitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Visit} and its DTO {@link VisitDTO}.
 */
@Mapper(componentModel = "spring")
public interface VisitMapper extends EntityMapper<VisitDTO, Visit> {
    @Mapping(target = "beneficiary", source = "beneficiary", qualifiedByName = "beneficiaryBeneficiaryReference")
    VisitDTO toDto(Visit s);

    @Named("beneficiaryBeneficiaryReference")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "beneficiaryReference", source = "beneficiaryReference")
    BeneficiaryDTO toDtoBeneficiaryBeneficiaryReference(Beneficiary beneficiary);
}
