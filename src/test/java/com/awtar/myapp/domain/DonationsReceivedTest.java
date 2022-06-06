package com.awtar.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DonationsReceivedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonationsReceived.class);
        DonationsReceived donationsReceived1 = new DonationsReceived();
        donationsReceived1.setId(1L);
        DonationsReceived donationsReceived2 = new DonationsReceived();
        donationsReceived2.setId(donationsReceived1.getId());
        assertThat(donationsReceived1).isEqualTo(donationsReceived2);
        donationsReceived2.setId(2L);
        assertThat(donationsReceived1).isNotEqualTo(donationsReceived2);
        donationsReceived1.setId(null);
        assertThat(donationsReceived1).isNotEqualTo(donationsReceived2);
    }
}
