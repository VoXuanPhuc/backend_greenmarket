package com.android.greenmarket.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnhNongSanMapperTest {

    private AnhNongSanMapper anhNongSanMapper;

    @BeforeEach
    public void setUp() {
        anhNongSanMapper = new AnhNongSanMapperImpl();
    }
}
