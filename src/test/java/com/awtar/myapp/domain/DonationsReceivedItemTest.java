package com.awtar.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DonationsReceivedItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonationsReceivedItem.class);
        DonationsReceivedItem donationsReceivedItem1 = new DonationsReceivedItem();
        donationsReceivedItem1.setId(1L);
        DonationsReceivedItem donationsReceivedItem2 = new DonationsReceivedItem();
        donationsReceivedItem2.setId(donationsReceivedItem1.getId());
        assertThat(donationsReceivedItem1).isEqualTo(donationsReceivedItem2);
        donationsReceivedItem2.setId(2L);
        assertThat(donationsReceivedItem1).isNotEqualTo(donationsReceivedItem2);
        donationsReceivedItem1.setId(null);
        assertThat(donationsReceivedItem1).isNotEqualTo(donationsReceivedItem2);
    }
}
