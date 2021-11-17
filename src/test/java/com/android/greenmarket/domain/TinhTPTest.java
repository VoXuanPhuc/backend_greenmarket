package com.android.greenmarket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TinhTPTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TinhTP.class);
        TinhTP tinhTP1 = new TinhTP();
        tinhTP1.setId(1L);
        TinhTP tinhTP2 = new TinhTP();
        tinhTP2.setId(tinhTP1.getId());
        assertThat(tinhTP1).isEqualTo(tinhTP2);
        tinhTP2.setId(2L);
        assertThat(tinhTP1).isNotEqualTo(tinhTP2);
        tinhTP1.setId(null);
        assertThat(tinhTP1).isNotEqualTo(tinhTP2);
    }
}
