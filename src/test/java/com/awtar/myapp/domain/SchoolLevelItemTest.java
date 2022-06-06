package com.awtar.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SchoolLevelItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchoolLevelItem.class);
        SchoolLevelItem schoolLevelItem1 = new SchoolLevelItem();
        schoolLevelItem1.setId(1L);
        SchoolLevelItem schoolLevelItem2 = new SchoolLevelItem();
        schoolLevelItem2.setId(schoolLevelItem1.getId());
        assertThat(schoolLevelItem1).isEqualTo(schoolLevelItem2);
        schoolLevelItem2.setId(2L);
        assertThat(schoolLevelItem1).isNotEqualTo(schoolLevelItem2);
        schoolLevelItem1.setId(null);
        assertThat(schoolLevelItem1).isNotEqualTo(schoolLevelItem2);
    }
}
