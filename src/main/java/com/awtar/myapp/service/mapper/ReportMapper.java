package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Establishment;
import com.awtar.myapp.domain.Report;
import com.awtar.myapp.service.dto.EstablishmentDTO;
import com.awtar.myapp.service.dto.ReportDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Report} and its DTO {@link ReportDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReportMapper extends EntityMapper<ReportDTO, Report> {
    @Mapping(target = "establishment", source = "establishment", qualifiedByName = "establishmentName")
    ReportDTO toDto(Report s);

    @Named("establishmentName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    EstablishmentDTO toDtoEstablishmentName(Establishment establishment);
}
