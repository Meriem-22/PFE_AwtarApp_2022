package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstablishmentTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstablishmentTypeDTO.class);
        EstablishmentTypeDTO establishmentTypeDTO1 = new EstablishmentTypeDTO();
        establishmentTypeDTO1.setId(1L);
        EstablishmentTypeDTO establishmentTypeDTO2 = new EstablishmentTypeDTO();
        assertThat(establishmentTypeDTO1).isNotEqualTo(establishmentTypeDTO2);
        establishmentTypeDTO2.setId(establishmentTypeDTO1.getId());
        assertThat(establishmentTypeDTO1).isEqualTo(establishmentTypeDTO2);
        establishmentTypeDTO2.setId(2L);
        assertThat(establishmentTypeDTO1).isNotEqualTo(establishmentTypeDTO2);
        establishmentTypeDTO1.setId(null);
        assertThat(establishmentTypeDTO1).isNotEqualTo(establishmentTypeDTO2);
    }
}
