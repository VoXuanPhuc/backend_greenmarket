package com.android.greenmarket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HinhThucGiaoHangTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HinhThucGiaoHang.class);
        HinhThucGiaoHang hinhThucGiaoHang1 = new HinhThucGiaoHang();
        hinhThucGiaoHang1.setId(1L);
        HinhThucGiaoHang hinhThucGiaoHang2 = new HinhThucGiaoHang();
        hinhThucGiaoHang2.setId(hinhThucGiaoHang1.getId());
        assertThat(hinhThucGiaoHang1).isEqualTo(hinhThucGiaoHang2);
        hinhThucGiaoHang2.setId(2L);
        assertThat(hinhThucGiaoHang1).isNotEqualTo(hinhThucGiaoHang2);
        hinhThucGiaoHang1.setId(null);
        assertThat(hinhThucGiaoHang1).isNotEqualTo(hinhThucGiaoHang2);
    }
}
