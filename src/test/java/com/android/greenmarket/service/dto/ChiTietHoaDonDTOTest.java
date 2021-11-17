package com.android.greenmarket.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChiTietHoaDonDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChiTietHoaDonDTO.class);
        ChiTietHoaDonDTO chiTietHoaDonDTO1 = new ChiTietHoaDonDTO();
        chiTietHoaDonDTO1.setId(1L);
        ChiTietHoaDonDTO chiTietHoaDonDTO2 = new ChiTietHoaDonDTO();
        assertThat(chiTietHoaDonDTO1).isNotEqualTo(chiTietHoaDonDTO2);
        chiTietHoaDonDTO2.setId(chiTietHoaDonDTO1.getId());
        assertThat(chiTietHoaDonDTO1).isEqualTo(chiTietHoaDonDTO2);
        chiTietHoaDonDTO2.setId(2L);
        assertThat(chiTietHoaDonDTO1).isNotEqualTo(chiTietHoaDonDTO2);
        chiTietHoaDonDTO1.setId(null);
        assertThat(chiTietHoaDonDTO1).isNotEqualTo(chiTietHoaDonDTO2);
    }
}
