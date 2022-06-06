package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeachingCurriculumMapperTest {

    private TeachingCurriculumMapper teachingCurriculumMapper;

    @BeforeEach
    public void setUp() {
        teachingCurriculumMapper = new TeachingCurriculumMapperImpl();
    }
}
