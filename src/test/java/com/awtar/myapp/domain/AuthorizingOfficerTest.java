package com.awtar.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuthorizingOfficerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuthorizingOfficer.class);
        AuthorizingOfficer authorizingOfficer1 = new AuthorizingOfficer();
        authorizingOfficer1.setId(1L);
        AuthorizingOfficer authorizingOfficer2 = new AuthorizingOfficer();
        authorizingOfficer2.setId(authorizingOfficer1.getId());
        assertThat(authorizingOfficer1).isEqualTo(authorizingOfficer2);
        authorizingOfficer2.setId(2L);
        assertThat(authorizingOfficer1).isNotEqualTo(authorizingOfficer2);
        authorizingOfficer1.setId(null);
        assertThat(authorizingOfficer1).isNotEqualTo(authorizingOfficer2);
    }
}
