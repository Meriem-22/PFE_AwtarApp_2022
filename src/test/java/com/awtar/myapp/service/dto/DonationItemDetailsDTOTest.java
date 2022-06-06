package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DonationItemDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonationItemDetailsDTO.class);
        DonationItemDetailsDTO donationItemDetailsDTO1 = new DonationItemDetailsDTO();
        donationItemDetailsDTO1.setId(1L);
        DonationItemDetailsDTO donationItemDetailsDTO2 = new DonationItemDetailsDTO();
        assertThat(donationItemDetailsDTO1).isNotEqualTo(donationItemDetailsDTO2);
        donationItemDetailsDTO2.setId(donationItemDetailsDTO1.getId());
        assertThat(donationItemDetailsDTO1).isEqualTo(donationItemDetailsDTO2);
        donationItemDetailsDTO2.setId(2L);
        assertThat(donationItemDetailsDTO1).isNotEqualTo(donationItemDetailsDTO2);
        donationItemDetailsDTO1.setId(null);
        assertThat(donationItemDetailsDTO1).isNotEqualTo(donationItemDetailsDTO2);
    }
}
