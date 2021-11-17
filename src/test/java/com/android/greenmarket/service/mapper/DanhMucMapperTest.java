package com.android.greenmarket.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DanhMucMapperTest {

    private DanhMucMapper danhMucMapper;

    @BeforeEach
    public void setUp() {
        danhMucMapper = new DanhMucMapperImpl();
    }
}
