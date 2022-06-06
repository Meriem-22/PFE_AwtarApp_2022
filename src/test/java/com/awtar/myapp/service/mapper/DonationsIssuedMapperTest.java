package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DonationsIssuedMapperTest {

    private DonationsIssuedMapper donationsIssuedMapper;

    @BeforeEach
    public void setUp() {
        donationsIssuedMapper = new DonationsIssuedMapperImpl();
    }
}
