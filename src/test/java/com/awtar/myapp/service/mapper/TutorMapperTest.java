package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TutorMapperTest {

    private TutorMapper tutorMapper;

    @BeforeEach
    public void setUp() {
        tutorMapper = new TutorMapperImpl();
    }
}
