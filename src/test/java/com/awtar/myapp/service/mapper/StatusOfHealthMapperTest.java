package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StatusOfHealthMapperTest {

    private StatusOfHealthMapper statusOfHealthMapper;

    @BeforeEach
    public void setUp() {
        statusOfHealthMapper = new StatusOfHealthMapperImpl();
    }
}
