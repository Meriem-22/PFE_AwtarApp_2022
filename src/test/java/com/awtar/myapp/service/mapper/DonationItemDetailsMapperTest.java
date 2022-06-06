package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DonationItemDetailsMapperTest {

    private DonationItemDetailsMapper donationItemDetailsMapper;

    @BeforeEach
    public void setUp() {
        donationItemDetailsMapper = new DonationItemDetailsMapperImpl();
    }
}
