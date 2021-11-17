package com.android.greenmarket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DanhMucTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DanhMuc.class);
        DanhMuc danhMuc1 = new DanhMuc();
        danhMuc1.setId(1L);
        DanhMuc danhMuc2 = new DanhMuc();
        danhMuc2.setId(danhMuc1.getId());
        assertThat(danhMuc1).isEqualTo(danhMuc2);
        danhMuc2.setId(2L);
        assertThat(danhMuc1).isNotEqualTo(danhMuc2);
        danhMuc1.setId(null);
        assertThat(danhMuc1).isNotEqualTo(danhMuc2);
    }
}
