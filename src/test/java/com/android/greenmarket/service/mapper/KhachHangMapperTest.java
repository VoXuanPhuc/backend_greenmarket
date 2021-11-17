package com.android.greenmarket.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KhachHangMapperTest {

    private KhachHangMapper khachHangMapper;

    @BeforeEach
    public void setUp() {
        khachHangMapper = new KhachHangMapperImpl();
    }
}
