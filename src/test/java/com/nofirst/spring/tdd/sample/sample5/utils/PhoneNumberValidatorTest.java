package com.nofirst.spring.tdd.sample.sample5.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneNumberValidatorTest {

    private PhoneNumberValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PhoneNumberValidator();
    }

    // 参数化测试
    @ParameterizedTest
    // 按行读取 csv 资源
    @CsvSource({
            "+447000000000,true",
            "+44700000000088878, false",
            "447000000000, false"
    })
    void it_should_validate_phone_number(String phoneNumber, boolean expected) {
        // When
        boolean isValid = validator.test(phoneNumber);

        // Then
        assertThat(isValid).isEqualTo(expected);
    }

}
