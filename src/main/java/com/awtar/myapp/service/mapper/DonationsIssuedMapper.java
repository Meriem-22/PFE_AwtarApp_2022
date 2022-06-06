package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.DonationsIssued;
import com.awtar.myapp.service.dto.DonationsIssuedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DonationsIssued} and its DTO {@link DonationsIssuedDTO}.
 */
@Mapper(componentModel = "spring")
public interface DonationsIssuedMapper extends EntityMapper<DonationsIssuedDTO, DonationsIssued> {}
