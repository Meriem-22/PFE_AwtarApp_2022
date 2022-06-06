package com.awtar.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TeachingCurriculumTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeachingCurriculum.class);
        TeachingCurriculum teachingCurriculum1 = new TeachingCurriculum();
        teachingCurriculum1.setId(1L);
        TeachingCurriculum teachingCurriculum2 = new TeachingCurriculum();
        teachingCurriculum2.setId(teachingCurriculum1.getId());
        assertThat(teachingCurriculum1).isEqualTo(teachingCurriculum2);
        teachingCurriculum2.setId(2L);
        assertThat(teachingCurriculum1).isNotEqualTo(teachingCurriculum2);
        teachingCurriculum1.setId(null);
        assertThat(teachingCurriculum1).isNotEqualTo(teachingCurriculum2);
    }
}
