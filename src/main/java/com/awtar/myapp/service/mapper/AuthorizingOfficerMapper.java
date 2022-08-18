package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import java.util.List;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AuthorizingOfficer} and its DTO {@link AuthorizingOfficerDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuthorizingOfficerMapper extends EntityMapper<AuthorizingOfficerDTO, AuthorizingOfficer> {}
