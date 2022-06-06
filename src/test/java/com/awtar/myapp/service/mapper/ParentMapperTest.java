package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParentMapperTest {

    private ParentMapper parentMapper;

    @BeforeEach
    public void setUp() {
        parentMapper = new ParentMapperImpl();
    }
}
