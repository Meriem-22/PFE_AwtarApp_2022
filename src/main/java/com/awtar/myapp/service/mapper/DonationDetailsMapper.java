package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Beneficiary;
import com.awtar.myapp.domain.DonationDetails;
import com.awtar.myapp.domain.DonationsIssued;
import com.awtar.myapp.domain.Nature;
import com.awtar.myapp.service.dto.BeneficiaryDTO;
import com.awtar.myapp.service.dto.DonationDetailsDTO;
import com.awtar.myapp.service.dto.DonationsIssuedDTO;
import com.awtar.myapp.service.dto.NatureDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DonationDetails} and its DTO {@link DonationDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DonationDetailsMapper extends EntityMapper<DonationDetailsDTO, DonationDetails> {
    @Mapping(target = "donationsIssued", source = "donationsIssued", qualifiedByName = "donationsIssuedId")
    @Mapping(target = "nature", source = "nature", qualifiedByName = "natureName")
    @Mapping(target = "beneficiary", source = "beneficiary", qualifiedByName = "beneficiaryBeneficiaryReference")
    DonationDetailsDTO toDto(DonationDetails s);

    @Named("donationsIssuedId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DonationsIssuedDTO toDtoDonationsIssuedId(DonationsIssued donationsIssued);

    @Named("natureName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    NatureDTO toDtoNatureName(Nature nature);

    @Named("beneficiaryBeneficiaryReference")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "beneficiaryReference", source = "beneficiaryReference")
    BeneficiaryDTO toDtoBeneficiaryBeneficiaryReference(Beneficiary beneficiary);
}
