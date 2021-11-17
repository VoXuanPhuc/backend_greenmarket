package com.android.greenmarket.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnhDanhGiaMapperTest {

    private AnhDanhGiaMapper anhDanhGiaMapper;

    @BeforeEach
    public void setUp() {
        anhDanhGiaMapper = new AnhDanhGiaMapperImpl();
    }
}
