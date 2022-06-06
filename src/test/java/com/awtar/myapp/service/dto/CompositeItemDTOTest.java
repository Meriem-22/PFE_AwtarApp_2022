package com.awtar.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.awtar.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompositeItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompositeItemDTO.class);
        CompositeItemDTO compositeItemDTO1 = new CompositeItemDTO();
        compositeItemDTO1.setId(1L);
        CompositeItemDTO compositeItemDTO2 = new CompositeItemDTO();
        assertThat(compositeItemDTO1).isNotEqualTo(compositeItemDTO2);
        compositeItemDTO2.setId(compositeItemDTO1.getId());
        assertThat(compositeItemDTO1).isEqualTo(compositeItemDTO2);
        compositeItemDTO2.setId(2L);
        assertThat(compositeItemDTO1).isNotEqualTo(compositeItemDTO2);
        compositeItemDTO1.setId(null);
        assertThat(compositeItemDTO1).isNotEqualTo(compositeItemDTO2);
    }
}
