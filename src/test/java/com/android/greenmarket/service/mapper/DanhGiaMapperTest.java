package com.android.greenmarket.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DanhGiaMapperTest {

    private DanhGiaMapper danhGiaMapper;

    @BeforeEach
    public void setUp() {
        danhGiaMapper = new DanhGiaMapperImpl();
    }
}
