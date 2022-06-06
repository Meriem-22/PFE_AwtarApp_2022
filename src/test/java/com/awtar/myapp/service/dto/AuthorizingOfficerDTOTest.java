package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuthorizingOfficerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuthorizingOfficerDTO.class);
        AuthorizingOfficerDTO authorizingOfficerDTO1 = new AuthorizingOfficerDTO();
        authorizingOfficerDTO1.setId(1L);
        AuthorizingOfficerDTO authorizingOfficerDTO2 = new AuthorizingOfficerDTO();
        assertThat(authorizingOfficerDTO1).isNotEqualTo(authorizingOfficerDTO2);
        authorizingOfficerDTO2.setId(authorizingOfficerDTO1.getId());
        assertThat(authorizingOfficerDTO1).isEqualTo(authorizingOfficerDTO2);
        authorizingOfficerDTO2.setId(2L);
        assertThat(authorizingOfficerDTO1).isNotEqualTo(authorizingOfficerDTO2);
        authorizingOfficerDTO1.setId(null);
        assertThat(authorizingOfficerDTO1).isNotEqualTo(authorizingOfficerDTO2);
    }
}
