package com.android.greenmarket.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhuongThucTTDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhuongThucTTDTO.class);
        PhuongThucTTDTO phuongThucTTDTO1 = new PhuongThucTTDTO();
        phuongThucTTDTO1.setId(1L);
        PhuongThucTTDTO phuongThucTTDTO2 = new PhuongThucTTDTO();
        assertThat(phuongThucTTDTO1).isNotEqualTo(phuongThucTTDTO2);
        phuongThucTTDTO2.setId(phuongThucTTDTO1.getId());
        assertThat(phuongThucTTDTO1).isEqualTo(phuongThucTTDTO2);
        phuongThucTTDTO2.setId(2L);
        assertThat(phuongThucTTDTO1).isNotEqualTo(phuongThucTTDTO2);
        phuongThucTTDTO1.setId(null);
        assertThat(phuongThucTTDTO1).isNotEqualTo(phuongThucTTDTO2);
    }
}
