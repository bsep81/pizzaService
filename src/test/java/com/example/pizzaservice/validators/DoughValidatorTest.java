package com.example.pizzaservice.validators;

import com.example.pizzaservice.model.Dough;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoughValidatorTest {

    private final DoughValidator doughValidator = new DoughValidator();

    @Test
    void shouldReturnEmptyStringIfDoughIsValid(){
        Dough dough = Dough.builder()
                .name("thin")
                .build();

        String result = doughValidator.isValid(dough);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnStringWithErrorMessageIfDoughIsNotVAlid(){
        Dough dough = new Dough();

        String error = "Dough should have a name.";

        String result = doughValidator.isValid(dough);

        assertEquals(error, result);
    }

}