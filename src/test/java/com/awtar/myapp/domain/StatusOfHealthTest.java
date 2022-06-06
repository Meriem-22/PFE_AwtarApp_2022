package com.awtar.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatusOfHealthTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatusOfHealth.class);
        StatusOfHealth statusOfHealth1 = new StatusOfHealth();
        statusOfHealth1.setId(1L);
        StatusOfHealth statusOfHealth2 = new StatusOfHealth();
        statusOfHealth2.setId(statusOfHealth1.getId());
        assertThat(statusOfHealth1).isEqualTo(statusOfHealth2);
        statusOfHealth2.setId(2L);
        assertThat(statusOfHealth1).isNotEqualTo(statusOfHealth2);
        statusOfHealth1.setId(null);
        assertThat(statusOfHealth1).isNotEqualTo(statusOfHealth2);
    }
}
