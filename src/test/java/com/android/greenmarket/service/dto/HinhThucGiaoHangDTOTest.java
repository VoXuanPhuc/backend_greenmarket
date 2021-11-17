package com.android.greenmarket.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HinhThucGiaoHangDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HinhThucGiaoHangDTO.class);
        HinhThucGiaoHangDTO hinhThucGiaoHangDTO1 = new HinhThucGiaoHangDTO();
        hinhThucGiaoHangDTO1.setId(1L);
        HinhThucGiaoHangDTO hinhThucGiaoHangDTO2 = new HinhThucGiaoHangDTO();
        assertThat(hinhThucGiaoHangDTO1).isNotEqualTo(hinhThucGiaoHangDTO2);
        hinhThucGiaoHangDTO2.setId(hinhThucGiaoHangDTO1.getId());
        assertThat(hinhThucGiaoHangDTO1).isEqualTo(hinhThucGiaoHangDTO2);
        hinhThucGiaoHangDTO2.setId(2L);
        assertThat(hinhThucGiaoHangDTO1).isNotEqualTo(hinhThucGiaoHangDTO2);
        hinhThucGiaoHangDTO1.setId(null);
        assertThat(hinhThucGiaoHangDTO1).isNotEqualTo(hinhThucGiaoHangDTO2);
    }
}
