package com.awtar.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EducationalInstitutionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducationalInstitution.class);
        EducationalInstitution educationalInstitution1 = new EducationalInstitution();
        educationalInstitution1.setId(1L);
        EducationalInstitution educationalInstitution2 = new EducationalInstitution();
        educationalInstitution2.setId(educationalInstitution1.getId());
        assertThat(educationalInstitution1).isEqualTo(educationalInstitution2);
        educationalInstitution2.setId(2L);
        assertThat(educationalInstitution1).isNotEqualTo(educationalInstitution2);
        educationalInstitution1.setId(null);
        assertThat(educationalInstitution1).isNotEqualTo(educationalInstitution2);
    }
}
