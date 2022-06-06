package com.awtar.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HealthStatusCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HealthStatusCategory.class);
        HealthStatusCategory healthStatusCategory1 = new HealthStatusCategory();
        healthStatusCategory1.setId(1L);
        HealthStatusCategory healthStatusCategory2 = new HealthStatusCategory();
        healthStatusCategory2.setId(healthStatusCategory1.getId());
        assertThat(healthStatusCategory1).isEqualTo(healthStatusCategory2);
        healthStatusCategory2.setId(2L);
        assertThat(healthStatusCategory1).isNotEqualTo(healthStatusCategory2);
        healthStatusCategory1.setId(null);
        assertThat(healthStatusCategory1).isNotEqualTo(healthStatusCategory2);
    }
}
