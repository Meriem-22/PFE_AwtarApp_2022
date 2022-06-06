package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SchoolLevelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchoolLevelDTO.class);
        SchoolLevelDTO schoolLevelDTO1 = new SchoolLevelDTO();
        schoolLevelDTO1.setId(1L);
        SchoolLevelDTO schoolLevelDTO2 = new SchoolLevelDTO();
        assertThat(schoolLevelDTO1).isNotEqualTo(schoolLevelDTO2);
        schoolLevelDTO2.setId(schoolLevelDTO1.getId());
        assertThat(schoolLevelDTO1).isEqualTo(schoolLevelDTO2);
        schoolLevelDTO2.setId(2L);
        assertThat(schoolLevelDTO1).isNotEqualTo(schoolLevelDTO2);
        schoolLevelDTO1.setId(null);
        assertThat(schoolLevelDTO1).isNotEqualTo(schoolLevelDTO2);
    }
}
