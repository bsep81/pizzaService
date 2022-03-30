package com.example.pizzaservice.validators;

import com.example.pizzaservice.model.Dough;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DoughValidatorTest {

    private final DoughValidator doughValidator = new DoughValidator();

    @Test
    void shouldReturnEmptyStringIfDoughIsValid(){
        Dough dough = Dough.builder()
                .name("thin")
                .basePrice(2.2)
                .build();

        List<String> result = doughValidator.isValid(dough);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnStringWithErrorMessageIfDoughIsNotVAlid(){
        Dough dough = new Dough();

        List<String> errors = List.of("Dough should have a name.",
                "Dough should have a price");

        List<String> result = doughValidator.isValid(dough);

        assertIterableEquals(errors, result);
    }

}