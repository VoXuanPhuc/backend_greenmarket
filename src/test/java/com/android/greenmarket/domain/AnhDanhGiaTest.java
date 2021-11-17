package com.android.greenmarket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnhDanhGiaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnhDanhGia.class);
        AnhDanhGia anhDanhGia1 = new AnhDanhGia();
        anhDanhGia1.setId(1L);
        AnhDanhGia anhDanhGia2 = new AnhDanhGia();
        anhDanhGia2.setId(anhDanhGia1.getId());
        assertThat(anhDanhGia1).isEqualTo(anhDanhGia2);
        anhDanhGia2.setId(2L);
        assertThat(anhDanhGia1).isNotEqualTo(anhDanhGia2);
        anhDanhGia1.setId(null);
        assertThat(anhDanhGia1).isNotEqualTo(anhDanhGia2);
    }
}
