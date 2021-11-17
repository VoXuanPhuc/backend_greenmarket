package com.android.greenmarket.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HuyenQuanMapperTest {

    private HuyenQuanMapper huyenQuanMapper;

    @BeforeEach
    public void setUp() {
        huyenQuanMapper = new HuyenQuanMapperImpl();
    }
}
