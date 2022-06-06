package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SchoolLevelItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchoolLevelItemDTO.class);
        SchoolLevelItemDTO schoolLevelItemDTO1 = new SchoolLevelItemDTO();
        schoolLevelItemDTO1.setId(1L);
        SchoolLevelItemDTO schoolLevelItemDTO2 = new SchoolLevelItemDTO();
        assertThat(schoolLevelItemDTO1).isNotEqualTo(schoolLevelItemDTO2);
        schoolLevelItemDTO2.setId(schoolLevelItemDTO1.getId());
        assertThat(schoolLevelItemDTO1).isEqualTo(schoolLevelItemDTO2);
        schoolLevelItemDTO2.setId(2L);
        assertThat(schoolLevelItemDTO1).isNotEqualTo(schoolLevelItemDTO2);
        schoolLevelItemDTO1.setId(null);
        assertThat(schoolLevelItemDTO1).isNotEqualTo(schoolLevelItemDTO2);
    }
}
