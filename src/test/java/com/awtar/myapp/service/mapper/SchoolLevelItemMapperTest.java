package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SchoolLevelItemMapperTest {

    private SchoolLevelItemMapper schoolLevelItemMapper;

    @BeforeEach
    public void setUp() {
        schoolLevelItemMapper = new SchoolLevelItemMapperImpl();
    }
}
