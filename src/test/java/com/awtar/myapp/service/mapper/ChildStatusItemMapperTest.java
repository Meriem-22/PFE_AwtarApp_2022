package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChildStatusItemMapperTest {

    private ChildStatusItemMapper childStatusItemMapper;

    @BeforeEach
    public void setUp() {
        childStatusItemMapper = new ChildStatusItemMapperImpl();
    }
}
