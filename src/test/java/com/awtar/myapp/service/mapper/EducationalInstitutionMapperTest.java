package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EducationalInstitutionMapperTest {

    private EducationalInstitutionMapper educationalInstitutionMapper;

    @BeforeEach
    public void setUp() {
        educationalInstitutionMapper = new EducationalInstitutionMapperImpl();
    }
}
