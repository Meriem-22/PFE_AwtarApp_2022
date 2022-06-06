package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DonationsReceivedItemMapperTest {

    private DonationsReceivedItemMapper donationsReceivedItemMapper;

    @BeforeEach
    public void setUp() {
        donationsReceivedItemMapper = new DonationsReceivedItemMapperImpl();
    }
}
