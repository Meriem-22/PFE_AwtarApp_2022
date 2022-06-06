package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstablishmentTypeMapperTest {

    private EstablishmentTypeMapper establishmentTypeMapper;

    @BeforeEach
    public void setUp() {
        establishmentTypeMapper = new EstablishmentTypeMapperImpl();
    }
}
