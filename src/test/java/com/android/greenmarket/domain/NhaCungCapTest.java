package com.android.greenmarket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NhaCungCapTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhaCungCap.class);
        NhaCungCap nhaCungCap1 = new NhaCungCap();
        nhaCungCap1.setId(1L);
        NhaCungCap nhaCungCap2 = new NhaCungCap();
        nhaCungCap2.setId(nhaCungCap1.getId());
        assertThat(nhaCungCap1).isEqualTo(nhaCungCap2);
        nhaCungCap2.setId(2L);
        assertThat(nhaCungCap1).isNotEqualTo(nhaCungCap2);
        nhaCungCap1.setId(null);
        assertThat(nhaCungCap1).isNotEqualTo(nhaCungCap2);
    }
}
