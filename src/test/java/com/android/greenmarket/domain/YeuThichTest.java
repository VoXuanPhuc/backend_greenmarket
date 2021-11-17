package com.android.greenmarket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class YeuThichTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(YeuThich.class);
        YeuThich yeuThich1 = new YeuThich();
        yeuThich1.setId(1L);
        YeuThich yeuThich2 = new YeuThich();
        yeuThich2.setId(yeuThich1.getId());
        assertThat(yeuThich1).isEqualTo(yeuThich2);
        yeuThich2.setId(2L);
        assertThat(yeuThich1).isNotEqualTo(yeuThich2);
        yeuThich1.setId(null);
        assertThat(yeuThich1).isNotEqualTo(yeuThich2);
    }
}
