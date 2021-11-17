package com.android.greenmarket.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DanhMucDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DanhMucDTO.class);
        DanhMucDTO danhMucDTO1 = new DanhMucDTO();
        danhMucDTO1.setId(1L);
        DanhMucDTO danhMucDTO2 = new DanhMucDTO();
        assertThat(danhMucDTO1).isNotEqualTo(danhMucDTO2);
        danhMucDTO2.setId(danhMucDTO1.getId());
        assertThat(danhMucDTO1).isEqualTo(danhMucDTO2);
        danhMucDTO2.setId(2L);
        assertThat(danhMucDTO1).isNotEqualTo(danhMucDTO2);
        danhMucDTO1.setId(null);
        assertThat(danhMucDTO1).isNotEqualTo(danhMucDTO2);
    }
}
