package com.awtar.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompositeItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompositeItem.class);
        CompositeItem compositeItem1 = new CompositeItem();
        compositeItem1.setId(1L);
        CompositeItem compositeItem2 = new CompositeItem();
        compositeItem2.setId(compositeItem1.getId());
        assertThat(compositeItem1).isEqualTo(compositeItem2);
        compositeItem2.setId(2L);
        assertThat(compositeItem1).isNotEqualTo(compositeItem2);
        compositeItem1.setId(null);
        assertThat(compositeItem1).isNotEqualTo(compositeItem2);
    }
}
