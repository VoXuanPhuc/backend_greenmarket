package com.android.greenmarket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NongSanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NongSan.class);
        NongSan nongSan1 = new NongSan();
        nongSan1.setId(1L);
        NongSan nongSan2 = new NongSan();
        nongSan2.setId(nongSan1.getId());
        assertThat(nongSan1).isEqualTo(nongSan2);
        nongSan2.setId(2L);
        assertThat(nongSan1).isNotEqualTo(nongSan2);
        nongSan1.setId(null);
        assertThat(nongSan1).isNotEqualTo(nongSan2);
    }
}
