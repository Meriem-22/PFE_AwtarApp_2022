package com.awtar.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DonationsIssuedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonationsIssued.class);
        DonationsIssued donationsIssued1 = new DonationsIssued();
        donationsIssued1.setId(1L);
        DonationsIssued donationsIssued2 = new DonationsIssued();
        donationsIssued2.setId(donationsIssued1.getId());
        assertThat(donationsIssued1).isEqualTo(donationsIssued2);
        donationsIssued2.setId(2L);
        assertThat(donationsIssued1).isNotEqualTo(donationsIssued2);
        donationsIssued1.setId(null);
        assertThat(donationsIssued1).isNotEqualTo(donationsIssued2);
    }
}
