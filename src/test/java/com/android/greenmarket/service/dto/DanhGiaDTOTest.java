package com.android.greenmarket.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DanhGiaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DanhGiaDTO.class);
        DanhGiaDTO danhGiaDTO1 = new DanhGiaDTO();
        danhGiaDTO1.setId(1L);
        DanhGiaDTO danhGiaDTO2 = new DanhGiaDTO();
        assertThat(danhGiaDTO1).isNotEqualTo(danhGiaDTO2);
        danhGiaDTO2.setId(danhGiaDTO1.getId());
        assertThat(danhGiaDTO1).isEqualTo(danhGiaDTO2);
        danhGiaDTO2.setId(2L);
        assertThat(danhGiaDTO1).isNotEqualTo(danhGiaDTO2);
        danhGiaDTO1.setId(null);
        assertThat(danhGiaDTO1).isNotEqualTo(danhGiaDTO2);
    }
}
