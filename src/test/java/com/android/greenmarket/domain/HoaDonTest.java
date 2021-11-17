package com.android.greenmarket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HoaDonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HoaDon.class);
        HoaDon hoaDon1 = new HoaDon();
        hoaDon1.setId(1L);
        HoaDon hoaDon2 = new HoaDon();
        hoaDon2.setId(hoaDon1.getId());
        assertThat(hoaDon1).isEqualTo(hoaDon2);
        hoaDon2.setId(2L);
        assertThat(hoaDon1).isNotEqualTo(hoaDon2);
        hoaDon1.setId(null);
        assertThat(hoaDon1).isNotEqualTo(hoaDon2);
    }
}
