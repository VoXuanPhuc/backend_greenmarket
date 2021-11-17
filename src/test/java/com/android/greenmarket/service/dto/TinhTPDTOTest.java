package com.android.greenmarket.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TinhTPDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TinhTPDTO.class);
        TinhTPDTO tinhTPDTO1 = new TinhTPDTO();
        tinhTPDTO1.setId(1L);
        TinhTPDTO tinhTPDTO2 = new TinhTPDTO();
        assertThat(tinhTPDTO1).isNotEqualTo(tinhTPDTO2);
        tinhTPDTO2.setId(tinhTPDTO1.getId());
        assertThat(tinhTPDTO1).isEqualTo(tinhTPDTO2);
        tinhTPDTO2.setId(2L);
        assertThat(tinhTPDTO1).isNotEqualTo(tinhTPDTO2);
        tinhTPDTO1.setId(null);
        assertThat(tinhTPDTO1).isNotEqualTo(tinhTPDTO2);
    }
}
