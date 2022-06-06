package com.awtar.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DonationDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonationDetails.class);
        DonationDetails donationDetails1 = new DonationDetails();
        donationDetails1.setId(1L);
        DonationDetails donationDetails2 = new DonationDetails();
        donationDetails2.setId(donationDetails1.getId());
        assertThat(donationDetails1).isEqualTo(donationDetails2);
        donationDetails2.setId(2L);
        assertThat(donationDetails1).isNotEqualTo(donationDetails2);
        donationDetails1.setId(null);
        assertThat(donationDetails1).isNotEqualTo(donationDetails2);
    }
}
