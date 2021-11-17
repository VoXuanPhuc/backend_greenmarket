package com.android.greenmarket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChiTietHoaDonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChiTietHoaDon.class);
        ChiTietHoaDon chiTietHoaDon1 = new ChiTietHoaDon();
        chiTietHoaDon1.setId(1L);
        ChiTietHoaDon chiTietHoaDon2 = new ChiTietHoaDon();
        chiTietHoaDon2.setId(chiTietHoaDon1.getId());
        assertThat(chiTietHoaDon1).isEqualTo(chiTietHoaDon2);
        chiTietHoaDon2.setId(2L);
        assertThat(chiTietHoaDon1).isNotEqualTo(chiTietHoaDon2);
        chiTietHoaDon1.setId(null);
        assertThat(chiTietHoaDon1).isNotEqualTo(chiTietHoaDon2);
    }
}
