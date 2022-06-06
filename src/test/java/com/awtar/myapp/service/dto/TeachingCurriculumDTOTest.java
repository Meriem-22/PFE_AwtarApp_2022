package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeachingCurriculumDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeachingCurriculumDTO.class);
        TeachingCurriculumDTO teachingCurriculumDTO1 = new TeachingCurriculumDTO();
        teachingCurriculumDTO1.setId(1L);
        TeachingCurriculumDTO teachingCurriculumDTO2 = new TeachingCurriculumDTO();
        assertThat(teachingCurriculumDTO1).isNotEqualTo(teachingCurriculumDTO2);
        teachingCurriculumDTO2.setId(teachingCurriculumDTO1.getId());
        assertThat(teachingCurriculumDTO1).isEqualTo(teachingCurriculumDTO2);
        teachingCurriculumDTO2.setId(2L);
        assertThat(teachingCurriculumDTO1).isNotEqualTo(teachingCurriculumDTO2);
        teachingCurriculumDTO1.setId(null);
        assertThat(teachingCurriculumDTO1).isNotEqualTo(teachingCurriculumDTO2);
    }
}
