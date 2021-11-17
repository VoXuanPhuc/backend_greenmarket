package com.android.greenmarket.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TinhTPMapperTest {

    private TinhTPMapper tinhTPMapper;

    @BeforeEach
    public void setUp() {
        tinhTPMapper = new TinhTPMapperImpl();
    }
}
