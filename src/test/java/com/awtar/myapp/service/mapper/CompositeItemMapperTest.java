package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompositeItemMapperTest {

    private CompositeItemMapper compositeItemMapper;

    @BeforeEach
    public void setUp() {
        compositeItemMapper = new CompositeItemMapperImpl();
    }
}
