package com.android.greenmarket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DanhGiaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DanhGia.class);
        DanhGia danhGia1 = new DanhGia();
        danhGia1.setId(1L);
        DanhGia danhGia2 = new DanhGia();
        danhGia2.setId(danhGia1.getId());
        assertThat(danhGia1).isEqualTo(danhGia2);
        danhGia2.setId(2L);
        assertThat(danhGia1).isNotEqualTo(danhGia2);
        danhGia1.setId(null);
        assertThat(danhGia1).isNotEqualTo(danhGia2);
    }
}
