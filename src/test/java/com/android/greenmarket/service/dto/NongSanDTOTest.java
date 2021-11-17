package com.android.greenmarket.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NongSanDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NongSanDTO.class);
        NongSanDTO nongSanDTO1 = new NongSanDTO();
        nongSanDTO1.setId(1L);
        NongSanDTO nongSanDTO2 = new NongSanDTO();
        assertThat(nongSanDTO1).isNotEqualTo(nongSanDTO2);
        nongSanDTO2.setId(nongSanDTO1.getId());
        assertThat(nongSanDTO1).isEqualTo(nongSanDTO2);
        nongSanDTO2.setId(2L);
        assertThat(nongSanDTO1).isNotEqualTo(nongSanDTO2);
        nongSanDTO1.setId(null);
        assertThat(nongSanDTO1).isNotEqualTo(nongSanDTO2);
    }
}
