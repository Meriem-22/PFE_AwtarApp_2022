package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ItemValueDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemValueDTO.class);
        ItemValueDTO itemValueDTO1 = new ItemValueDTO();
        itemValueDTO1.setId(1L);
        ItemValueDTO itemValueDTO2 = new ItemValueDTO();
        assertThat(itemValueDTO1).isNotEqualTo(itemValueDTO2);
        itemValueDTO2.setId(itemValueDTO1.getId());
        assertThat(itemValueDTO1).isEqualTo(itemValueDTO2);
        itemValueDTO2.setId(2L);
        assertThat(itemValueDTO1).isNotEqualTo(itemValueDTO2);
        itemValueDTO1.setId(null);
        assertThat(itemValueDTO1).isNotEqualTo(itemValueDTO2);
    }
}
