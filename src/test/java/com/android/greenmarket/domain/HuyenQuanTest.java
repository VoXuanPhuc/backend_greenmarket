package com.android.greenmarket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HuyenQuanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HuyenQuan.class);
        HuyenQuan huyenQuan1 = new HuyenQuan();
        huyenQuan1.setId(1L);
        HuyenQuan huyenQuan2 = new HuyenQuan();
        huyenQuan2.setId(huyenQuan1.getId());
        assertThat(huyenQuan1).isEqualTo(huyenQuan2);
        huyenQuan2.setId(2L);
        assertThat(huyenQuan1).isNotEqualTo(huyenQuan2);
        huyenQuan1.setId(null);
        assertThat(huyenQuan1).isNotEqualTo(huyenQuan2);
    }
}
