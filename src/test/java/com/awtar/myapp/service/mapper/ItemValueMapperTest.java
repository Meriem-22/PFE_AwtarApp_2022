package com.awtar.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItemValueMapperTest {

    private ItemValueMapper itemValueMapper;

    @BeforeEach
    public void setUp() {
        itemValueMapper = new ItemValueMapperImpl();
    }
}
