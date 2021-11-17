package com.android.greenmarket.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhuongThucTTMapperTest {

    private PhuongThucTTMapper phuongThucTTMapper;

    @BeforeEach
    public void setUp() {
        phuongThucTTMapper = new PhuongThucTTMapperImpl();
    }
}
