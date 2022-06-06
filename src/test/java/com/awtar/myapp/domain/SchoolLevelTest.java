package com.awtar.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SchoolLevelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchoolLevel.class);
        SchoolLevel schoolLevel1 = new SchoolLevel();
        schoolLevel1.setId(1L);
        SchoolLevel schoolLevel2 = new SchoolLevel();
        schoolLevel2.setId(schoolLevel1.getId());
        assertThat(schoolLevel1).isEqualTo(schoolLevel2);
        schoolLevel2.setId(2L);
        assertThat(schoolLevel1).isNotEqualTo(schoolLevel2);
        schoolLevel1.setId(null);
        assertThat(schoolLevel1).isNotEqualTo(schoolLevel2);
    }
}
