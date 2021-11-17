package com.android.greenmarket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhuongThucTTTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhuongThucTT.class);
        PhuongThucTT phuongThucTT1 = new PhuongThucTT();
        phuongThucTT1.setId(1L);
        PhuongThucTT phuongThucTT2 = new PhuongThucTT();
        phuongThucTT2.setId(phuongThucTT1.getId());
        assertThat(phuongThucTT1).isEqualTo(phuongThucTT2);
        phuongThucTT2.setId(2L);
        assertThat(phuongThucTT1).isNotEqualTo(phuongThucTT2);
        phuongThucTT1.setId(null);
        assertThat(phuongThucTT1).isNotEqualTo(phuongThucTT2);
    }
}
