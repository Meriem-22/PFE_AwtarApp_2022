package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChildStatusMapperTest {

    private ChildStatusMapper childStatusMapper;

    @BeforeEach
    public void setUp() {
        childStatusMapper = new ChildStatusMapperImpl();
    }
}
