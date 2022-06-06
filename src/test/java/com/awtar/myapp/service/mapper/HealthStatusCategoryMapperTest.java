package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HealthStatusCategoryMapperTest {

    private HealthStatusCategoryMapper healthStatusCategoryMapper;

    @BeforeEach
    public void setUp() {
        healthStatusCategoryMapper = new HealthStatusCategoryMapperImpl();
    }
}
