package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthorizingOfficerMapperTest {

    private AuthorizingOfficerMapper authorizingOfficerMapper;

    @BeforeEach
    public void setUp() {
        authorizingOfficerMapper = new AuthorizingOfficerMapperImpl();
    }
}
