package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatusOfHealthDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusOfHealthDTO.class);
        StatusOfHealthDTO statusOfHealthDTO1 = new StatusOfHealthDTO();
        statusOfHealthDTO1.setId(1L);
        StatusOfHealthDTO statusOfHealthDTO2 = new StatusOfHealthDTO();
        assertThat(statusOfHealthDTO1).isNotEqualTo(statusOfHealthDTO2);
        statusOfHealthDTO2.setId(statusOfHealthDTO1.getId());
        assertThat(statusOfHealthDTO1).isEqualTo(statusOfHealthDTO2);
        statusOfHealthDTO2.setId(2L);
        assertThat(statusOfHealthDTO1).isNotEqualTo(statusOfHealthDTO2);
        statusOfHealthDTO1.setId(null);
        assertThat(statusOfHealthDTO1).isNotEqualTo(statusOfHealthDTO2);
    }
}
