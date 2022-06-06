package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SchoolLevelMapperTest {

    private SchoolLevelMapper schoolLevelMapper;

    @BeforeEach
    public void setUp() {
        schoolLevelMapper = new SchoolLevelMapperImpl();
    }
}
