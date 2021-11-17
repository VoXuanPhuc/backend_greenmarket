package com.android.greenmarket.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HuyenQuanDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HuyenQuanDTO.class);
        HuyenQuanDTO huyenQuanDTO1 = new HuyenQuanDTO();
        huyenQuanDTO1.setId(1L);
        HuyenQuanDTO huyenQuanDTO2 = new HuyenQuanDTO();
        assertThat(huyenQuanDTO1).isNotEqualTo(huyenQuanDTO2);
        huyenQuanDTO2.setId(huyenQuanDTO1.getId());
        assertThat(huyenQuanDTO1).isEqualTo(huyenQuanDTO2);
        huyenQuanDTO2.setId(2L);
        assertThat(huyenQuanDTO1).isNotEqualTo(huyenQuanDTO2);
        huyenQuanDTO1.setId(null);
        assertThat(huyenQuanDTO1).isNotEqualTo(huyenQuanDTO2);
    }
}
