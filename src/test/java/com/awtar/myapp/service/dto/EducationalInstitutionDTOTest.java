package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EducationalInstitutionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducationalInstitutionDTO.class);
        EducationalInstitutionDTO educationalInstitutionDTO1 = new EducationalInstitutionDTO();
        educationalInstitutionDTO1.setId(1L);
        EducationalInstitutionDTO educationalInstitutionDTO2 = new EducationalInstitutionDTO();
        assertThat(educationalInstitutionDTO1).isNotEqualTo(educationalInstitutionDTO2);
        educationalInstitutionDTO2.setId(educationalInstitutionDTO1.getId());
        assertThat(educationalInstitutionDTO1).isEqualTo(educationalInstitutionDTO2);
        educationalInstitutionDTO2.setId(2L);
        assertThat(educationalInstitutionDTO1).isNotEqualTo(educationalInstitutionDTO2);
        educationalInstitutionDTO1.setId(null);
        assertThat(educationalInstitutionDTO1).isNotEqualTo(educationalInstitutionDTO2);
    }
}
