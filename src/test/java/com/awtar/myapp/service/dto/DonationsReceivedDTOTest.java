package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DonationsReceivedDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonationsReceivedDTO.class);
        DonationsReceivedDTO donationsReceivedDTO1 = new DonationsReceivedDTO();
        donationsReceivedDTO1.setId(1L);
        DonationsReceivedDTO donationsReceivedDTO2 = new DonationsReceivedDTO();
        assertThat(donationsReceivedDTO1).isNotEqualTo(donationsReceivedDTO2);
        donationsReceivedDTO2.setId(donationsReceivedDTO1.getId());
        assertThat(donationsReceivedDTO1).isEqualTo(donationsReceivedDTO2);
        donationsReceivedDTO2.setId(2L);
        assertThat(donationsReceivedDTO1).isNotEqualTo(donationsReceivedDTO2);
        donationsReceivedDTO1.setId(null);
        assertThat(donationsReceivedDTO1).isNotEqualTo(donationsReceivedDTO2);
    }
}
