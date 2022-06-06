package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HealthStatusCategoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HealthStatusCategoryDTO.class);
        HealthStatusCategoryDTO healthStatusCategoryDTO1 = new HealthStatusCategoryDTO();
        healthStatusCategoryDTO1.setId(1L);
        HealthStatusCategoryDTO healthStatusCategoryDTO2 = new HealthStatusCategoryDTO();
        assertThat(healthStatusCategoryDTO1).isNotEqualTo(healthStatusCategoryDTO2);
        healthStatusCategoryDTO2.setId(healthStatusCategoryDTO1.getId());
        assertThat(healthStatusCategoryDTO1).isEqualTo(healthStatusCategoryDTO2);
        healthStatusCategoryDTO2.setId(2L);
        assertThat(healthStatusCategoryDTO1).isNotEqualTo(healthStatusCategoryDTO2);
        healthStatusCategoryDTO1.setId(null);
        assertThat(healthStatusCategoryDTO1).isNotEqualTo(healthStatusCategoryDTO2);
    }
}
