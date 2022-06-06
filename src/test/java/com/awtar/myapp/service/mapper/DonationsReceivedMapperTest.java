package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DonationsReceivedMapperTest {

    private DonationsReceivedMapper donationsReceivedMapper;

    @BeforeEach
    public void setUp() {
        donationsReceivedMapper = new DonationsReceivedMapperImpl();
    }
}
