package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.TutorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tutor} and its DTO {@link TutorDTO}.
 */
@Mapper(componentModel = "spring")
public interface TutorMapper extends EntityMapper<TutorDTO, Tutor> {}
