package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DonationDetailsMapperTest {

    private DonationDetailsMapper donationDetailsMapper;

    @BeforeEach
    public void setUp() {
        donationDetailsMapper = new DonationDetailsMapperImpl();
    }
}
