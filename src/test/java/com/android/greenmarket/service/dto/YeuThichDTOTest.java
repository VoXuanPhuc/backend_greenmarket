package com.android.greenmarket.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class YeuThichDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(YeuThichDTO.class);
        YeuThichDTO yeuThichDTO1 = new YeuThichDTO();
        yeuThichDTO1.setId(1L);
        YeuThichDTO yeuThichDTO2 = new YeuThichDTO();
        assertThat(yeuThichDTO1).isNotEqualTo(yeuThichDTO2);
        yeuThichDTO2.setId(yeuThichDTO1.getId());
        assertThat(yeuThichDTO1).isEqualTo(yeuThichDTO2);
        yeuThichDTO2.setId(2L);
        assertThat(yeuThichDTO1).isNotEqualTo(yeuThichDTO2);
        yeuThichDTO1.setId(null);
        assertThat(yeuThichDTO1).isNotEqualTo(yeuThichDTO2);
    }
}
