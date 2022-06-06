package com.awtar.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstablishmentTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstablishmentType.class);
        EstablishmentType establishmentType1 = new EstablishmentType();
        establishmentType1.setId(1L);
        EstablishmentType establishmentType2 = new EstablishmentType();
        establishmentType2.setId(establishmentType1.getId());
        assertThat(establishmentType1).isEqualTo(establishmentType2);
        establishmentType2.setId(2L);
        assertThat(establishmentType1).isNotEqualTo(establishmentType2);
        establishmentType1.setId(null);
        assertThat(establishmentType1).isNotEqualTo(establishmentType2);
    }
}
