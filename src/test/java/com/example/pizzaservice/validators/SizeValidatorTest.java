package com.example.pizzaservice.validators;

import com.example.pizzaservice.model.Size;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SizeValidatorTest {

    private final SizeValidator sizeValidator = new SizeValidator();

    @Test
    void shouldReturnEmptyStringIfSizeIsValid(){
        Size size = Size.builder()
                .diameter(10)
                .build();

        String result = sizeValidator.isValid(size);

        assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 50})
    void shouldReturnErrorMessageWhenSizeIsNotVvalid(int number){
        Size size = Size.builder()
                .diameter(number)
                .build();

        String result = sizeValidator.isValid(size);

        assertFalse(result.isEmpty());
    }
}