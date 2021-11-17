package com.android.greenmarket.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class XaPhuongMapperTest {

    private XaPhuongMapper xaPhuongMapper;

    @BeforeEach
    public void setUp() {
        xaPhuongMapper = new XaPhuongMapperImpl();
    }
}
