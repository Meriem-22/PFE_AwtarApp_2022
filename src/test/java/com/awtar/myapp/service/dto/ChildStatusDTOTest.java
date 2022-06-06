package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChildStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChildStatusDTO.class);
        ChildStatusDTO childStatusDTO1 = new ChildStatusDTO();
        childStatusDTO1.setId(1L);
        ChildStatusDTO childStatusDTO2 = new ChildStatusDTO();
        assertThat(childStatusDTO1).isNotEqualTo(childStatusDTO2);
        childStatusDTO2.setId(childStatusDTO1.getId());
        assertThat(childStatusDTO1).isEqualTo(childStatusDTO2);
        childStatusDTO2.setId(2L);
        assertThat(childStatusDTO1).isNotEqualTo(childStatusDTO2);
        childStatusDTO1.setId(null);
        assertThat(childStatusDTO1).isNotEqualTo(childStatusDTO2);
    }
}
