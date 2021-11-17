package com.android.greenmarket.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.android.greenmarket.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnhNongSanDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnhNongSanDTO.class);
        AnhNongSanDTO anhNongSanDTO1 = new AnhNongSanDTO();
        anhNongSanDTO1.setId(1L);
        AnhNongSanDTO anhNongSanDTO2 = new AnhNongSanDTO();
        assertThat(anhNongSanDTO1).isNotEqualTo(anhNongSanDTO2);
        anhNongSanDTO2.setId(anhNongSanDTO1.getId());
        assertThat(anhNongSanDTO1).isEqualTo(anhNongSanDTO2);
        anhNongSanDTO2.setId(2L);
        assertThat(anhNongSanDTO1).isNotEqualTo(anhNongSanDTO2);
        anhNongSanDTO1.setId(null);
        assertThat(anhNongSanDTO1).isNotEqualTo(anhNongSanDTO2);
    }
}
