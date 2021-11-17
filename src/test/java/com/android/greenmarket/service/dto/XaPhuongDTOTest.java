package com.android.greenmarket.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class XaPhuongDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(XaPhuongDTO.class);
        XaPhuongDTO xaPhuongDTO1 = new XaPhuongDTO();
        xaPhuongDTO1.setId(1L);
        XaPhuongDTO xaPhuongDTO2 = new XaPhuongDTO();
        assertThat(xaPhuongDTO1).isNotEqualTo(xaPhuongDTO2);
        xaPhuongDTO2.setId(xaPhuongDTO1.getId());
        assertThat(xaPhuongDTO1).isEqualTo(xaPhuongDTO2);
        xaPhuongDTO2.setId(2L);
        assertThat(xaPhuongDTO1).isNotEqualTo(xaPhuongDTO2);
        xaPhuongDTO1.setId(null);
        assertThat(xaPhuongDTO1).isNotEqualTo(xaPhuongDTO2);
    }
}
