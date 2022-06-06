package com.awtar.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DonationItemDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonationItemDetails.class);
        DonationItemDetails donationItemDetails1 = new DonationItemDetails();
        donationItemDetails1.setId(1L);
        DonationItemDetails donationItemDetails2 = new DonationItemDetails();
        donationItemDetails2.setId(donationItemDetails1.getId());
        assertThat(donationItemDetails1).isEqualTo(donationItemDetails2);
        donationItemDetails2.setId(2L);
        assertThat(donationItemDetails1).isNotEqualTo(donationItemDetails2);
        donationItemDetails1.setId(null);
        assertThat(donationItemDetails1).isNotEqualTo(donationItemDetails2);
    }
}
