package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DonationsIssuedDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DonationsIssuedDTO.class);
        DonationsIssuedDTO donationsIssuedDTO1 = new DonationsIssuedDTO();
        donationsIssuedDTO1.setId(1L);
        DonationsIssuedDTO donationsIssuedDTO2 = new DonationsIssuedDTO();
        assertThat(donationsIssuedDTO1).isNotEqualTo(donationsIssuedDTO2);
        donationsIssuedDTO2.setId(donationsIssuedDTO1.getId());
        assertThat(donationsIssuedDTO1).isEqualTo(donationsIssuedDTO2);
        donationsIssuedDTO2.setId(2L);
        assertThat(donationsIssuedDTO1).isNotEqualTo(donationsIssuedDTO2);
        donationsIssuedDTO1.setId(null);
        assertThat(donationsIssuedDTO1).isNotEqualTo(donationsIssuedDTO2);
    }
}
