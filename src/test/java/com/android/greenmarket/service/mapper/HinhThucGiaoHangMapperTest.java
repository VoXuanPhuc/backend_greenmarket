package com.android.greenmarket.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HinhThucGiaoHangMapperTest {

    private HinhThucGiaoHangMapper hinhThucGiaoHangMapper;

    @BeforeEach
    public void setUp() {
        hinhThucGiaoHangMapper = new HinhThucGiaoHangMapperImpl();
    }
}
