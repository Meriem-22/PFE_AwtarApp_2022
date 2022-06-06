package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DonationsReceivedItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonationsReceivedItemDTO.class);
        DonationsReceivedItemDTO donationsReceivedItemDTO1 = new DonationsReceivedItemDTO();
        donationsReceivedItemDTO1.setId(1L);
        DonationsReceivedItemDTO donationsReceivedItemDTO2 = new DonationsReceivedItemDTO();
        assertThat(donationsReceivedItemDTO1).isNotEqualTo(donationsReceivedItemDTO2);
        donationsReceivedItemDTO2.setId(donationsReceivedItemDTO1.getId());
        assertThat(donationsReceivedItemDTO1).isEqualTo(donationsReceivedItemDTO2);
        donationsReceivedItemDTO2.setId(2L);
        assertThat(donationsReceivedItemDTO1).isNotEqualTo(donationsReceivedItemDTO2);
        donationsReceivedItemDTO1.setId(null);
        assertThat(donationsReceivedItemDTO1).isNotEqualTo(donationsReceivedItemDTO2);
    }
}
