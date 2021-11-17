package com.android.greenmarket.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChiTietHoaDonMapperTest {

    private ChiTietHoaDonMapper chiTietHoaDonMapper;

    @BeforeEach
    public void setUp() {
        chiTietHoaDonMapper = new ChiTietHoaDonMapperImpl();
    }
}
