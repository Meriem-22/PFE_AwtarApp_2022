package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChildStatusItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChildStatusItemDTO.class);
        ChildStatusItemDTO childStatusItemDTO1 = new ChildStatusItemDTO();
        childStatusItemDTO1.setId(1L);
        ChildStatusItemDTO childStatusItemDTO2 = new ChildStatusItemDTO();
        assertThat(childStatusItemDTO1).isNotEqualTo(childStatusItemDTO2);
        childStatusItemDTO2.setId(childStatusItemDTO1.getId());
        assertThat(childStatusItemDTO1).isEqualTo(childStatusItemDTO2);
        childStatusItemDTO2.setId(2L);
        assertThat(childStatusItemDTO1).isNotEqualTo(childStatusItemDTO2);
        childStatusItemDTO1.setId(null);
        assertThat(childStatusItemDTO1).isNotEqualTo(childStatusItemDTO2);
    }
}
