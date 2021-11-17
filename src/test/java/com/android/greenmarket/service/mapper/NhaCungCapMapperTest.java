package com.android.greenmarket.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NhaCungCapMapperTest {

    private NhaCungCapMapper nhaCungCapMapper;

    @BeforeEach
    public void setUp() {
        nhaCungCapMapper = new NhaCungCapMapperImpl();
    }
}
