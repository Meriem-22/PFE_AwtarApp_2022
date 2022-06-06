package com.awtar.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChildStatusItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChildStatusItem.class);
        ChildStatusItem childStatusItem1 = new ChildStatusItem();
        childStatusItem1.setId(1L);
        ChildStatusItem childStatusItem2 = new ChildStatusItem();
        childStatusItem2.setId(childStatusItem1.getId());
        assertThat(childStatusItem1).isEqualTo(childStatusItem2);
        childStatusItem2.setId(2L);
        assertThat(childStatusItem1).isNotEqualTo(childStatusItem2);
        childStatusItem1.setId(null);
        assertThat(childStatusItem1).isNotEqualTo(childStatusItem2);
    }
}
