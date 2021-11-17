package com.android.greenmarket.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NongSanMapperTest {

    private NongSanMapper nongSanMapper;

    @BeforeEach
    public void setUp() {
        nongSanMapper = new NongSanMapperImpl();
    }
}
