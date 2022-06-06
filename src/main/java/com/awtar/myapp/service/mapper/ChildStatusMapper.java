package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.ChildStatus;
import com.awtar.myapp.service.dto.ChildStatusDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ChildStatus} and its DTO {@link ChildStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface ChildStatusMapper extends EntityMapper<ChildStatusDTO, ChildStatus> {}
