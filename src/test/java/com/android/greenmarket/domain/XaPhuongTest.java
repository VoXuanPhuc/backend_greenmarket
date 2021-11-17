package com.android.greenmarket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class XaPhuongTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(XaPhuong.class);
        XaPhuong xaPhuong1 = new XaPhuong();
        xaPhuong1.setId(1L);
        XaPhuong xaPhuong2 = new XaPhuong();
        xaPhuong2.setId(xaPhuong1.getId());
        assertThat(xaPhuong1).isEqualTo(xaPhuong2);
        xaPhuong2.setId(2L);
        assertThat(xaPhuong1).isNotEqualTo(xaPhuong2);
        xaPhuong1.setId(null);
        assertThat(xaPhuong1).isNotEqualTo(xaPhuong2);
    }
}
