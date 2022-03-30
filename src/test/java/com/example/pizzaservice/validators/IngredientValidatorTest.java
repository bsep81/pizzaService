package com.example.pizzaservice.validators;

import com.example.pizzaservice.model.Ingredient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngredientValidatorTest {

    private final IngredientValidator ingredientValidator = new IngredientValidator();

    @Test
    void shouldReturnEmptyListIfIngredientIsValid(){
        Ingredient ingredient = Ingredient.builder()
                .name("tomato")
                .basePrice(0.55)
                .build();

        List<String> result = ingredientValidator.isValid(ingredient);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnListOfErrorsIfIngredientIsNotValid(){
        Ingredient ingredient = Ingredient.builder()
                .id(1L)
                .build();

        List<String> errors = List.of(
                "Ingredient should have a name.",
                "Ingredient should have a price."
        );

        List<String> result = ingredientValidator.isValid(ingredient);

        assertIterableEquals(errors, result);
    }

    @Test
    void shouldReturnAdequateErrorIfPriceIsNegative(){
        Ingredient ingredient = Ingredient.builder()
                .name("tomato")
                .basePrice(-2.5)
                .build();

        List<String> errors = List.of("Price can not be negative.");

        List<String> result = ingredientValidator.isValid(ingredient);

        assertIterableEquals(errors, result);
    }

}