package com.android.greenmarket.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HoaDonMapperTest {

    private HoaDonMapper hoaDonMapper;

    @BeforeEach
    public void setUp() {
        hoaDonMapper = new HoaDonMapperImpl();
    }
}
