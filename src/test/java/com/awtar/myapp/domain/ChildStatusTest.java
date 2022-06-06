package com.awtar.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChildStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChildStatus.class);
        ChildStatus childStatus1 = new ChildStatus();
        childStatus1.setId(1L);
        ChildStatus childStatus2 = new ChildStatus();
        childStatus2.setId(childStatus1.getId());
        assertThat(childStatus1).isEqualTo(childStatus2);
        childStatus2.setId(2L);
        assertThat(childStatus1).isNotEqualTo(childStatus2);
        childStatus1.setId(null);
        assertThat(childStatus1).isNotEqualTo(childStatus2);
    }
}
