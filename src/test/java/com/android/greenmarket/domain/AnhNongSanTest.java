package com.android.greenmarket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnhNongSanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnhNongSan.class);
        AnhNongSan anhNongSan1 = new AnhNongSan();
        anhNongSan1.setId(1L);
        AnhNongSan anhNongSan2 = new AnhNongSan();
        anhNongSan2.setId(anhNongSan1.getId());
        assertThat(anhNongSan1).isEqualTo(anhNongSan2);
        anhNongSan2.setId(2L);
        assertThat(anhNongSan1).isNotEqualTo(anhNongSan2);
        anhNongSan1.setId(null);
        assertThat(anhNongSan1).isNotEqualTo(anhNongSan2);
    }
}
