package com.android.greenmarket.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class YeuThichMapperTest {

    private YeuThichMapper yeuThichMapper;

    @BeforeEach
    public void setUp() {
        yeuThichMapper = new YeuThichMapperImpl();
    }
}
