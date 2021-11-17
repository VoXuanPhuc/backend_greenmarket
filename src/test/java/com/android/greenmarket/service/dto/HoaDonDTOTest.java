package com.android.greenmarket.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HoaDonDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HoaDonDTO.class);
        HoaDonDTO hoaDonDTO1 = new HoaDonDTO();
        hoaDonDTO1.setId(1L);
        HoaDonDTO hoaDonDTO2 = new HoaDonDTO();
        assertThat(hoaDonDTO1).isNotEqualTo(hoaDonDTO2);
        hoaDonDTO2.setId(hoaDonDTO1.getId());
        assertThat(hoaDonDTO1).isEqualTo(hoaDonDTO2);
        hoaDonDTO2.setId(2L);
        assertThat(hoaDonDTO1).isNotEqualTo(hoaDonDTO2);
        hoaDonDTO1.setId(null);
        assertThat(hoaDonDTO1).isNotEqualTo(hoaDonDTO2);
    }
}
