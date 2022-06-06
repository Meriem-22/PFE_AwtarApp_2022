package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DonationDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonationDetailsDTO.class);
        DonationDetailsDTO donationDetailsDTO1 = new DonationDetailsDTO();
        donationDetailsDTO1.setId(1L);
        DonationDetailsDTO donationDetailsDTO2 = new DonationDetailsDTO();
        assertThat(donationDetailsDTO1).isNotEqualTo(donationDetailsDTO2);
        donationDetailsDTO2.setId(donationDetailsDTO1.getId());
        assertThat(donationDetailsDTO1).isEqualTo(donationDetailsDTO2);
        donationDetailsDTO2.setId(2L);
        assertThat(donationDetailsDTO1).isNotEqualTo(donationDetailsDTO2);
        donationDetailsDTO1.setId(null);
        assertThat(donationDetailsDTO1).isNotEqualTo(donationDetailsDTO2);
    }
}
