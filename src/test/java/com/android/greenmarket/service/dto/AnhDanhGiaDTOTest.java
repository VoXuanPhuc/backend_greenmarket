package com.android.greenmarket.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnhDanhGiaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnhDanhGiaDTO.class);
        AnhDanhGiaDTO anhDanhGiaDTO1 = new AnhDanhGiaDTO();
        anhDanhGiaDTO1.setId(1L);
        AnhDanhGiaDTO anhDanhGiaDTO2 = new AnhDanhGiaDTO();
        assertThat(anhDanhGiaDTO1).isNotEqualTo(anhDanhGiaDTO2);
        anhDanhGiaDTO2.setId(anhDanhGiaDTO1.getId());
        assertThat(anhDanhGiaDTO1).isEqualTo(anhDanhGiaDTO2);
        anhDanhGiaDTO2.setId(2L);
        assertThat(anhDanhGiaDTO1).isNotEqualTo(anhDanhGiaDTO2);
        anhDanhGiaDTO1.setId(null);
        assertThat(anhDanhGiaDTO1).isNotEqualTo(anhDanhGiaDTO2);
    }
}
