package com.jvboot.pp.web.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class DataDtoTest {
    @Test
    public void dataDtoShouldContainInput() {
        DataDto<?> data1 = new DataDto<>(null);
        assertNull(data1.getData());

        Double input = Math.random();
        DataDto<Double> data2 = new DataDto<>(input);
        assertSame(data2.getData(), input);
    }
}