package com.example.pizzaservice.validators;

import com.example.pizzaservice.model.Dough;
import com.example.pizzaservice.model.Ingredient;
import com.example.pizzaservice.model.Pizza;
import com.example.pizzaservice.model.Size;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PizzaValidatorTest {

    @Mock
    private IngredientValidator ingredientValidator;
    @Mock
    private SizeValidator sizeValidator;
    @Mock
    private DoughValidator doughValidator;

    @InjectMocks
    private PizzaValidator pizzaValidator;

    @Test
    void shouldReturnEmptyListWhenPizzaIsValid() {
        Pizza pizza = new Pizza(10L,
                new Dough(1L, "thin"),
                new Size(2L, 14),
                List.of(
                        new Ingredient(3L, "tomato", 2.3),
                        new Ingredient(4L, "mushroom", 1.5)
                ));


        when(doughValidator.isValid(pizza.getDough())).thenReturn("");
        when(sizeValidator.isValid(pizza.getSize())).thenReturn("");
        when(ingredientValidator.isValid(pizza.getIngredients().get(0))).thenReturn(new ArrayList<>());
        when(ingredientValidator.isValid(pizza.getIngredients().get(1))).thenReturn(new ArrayList<>());

        List<String> result = pizzaValidator.isValid(pizza);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnListOfThreeErrorsWhenPizzaIsNull(){
        Pizza pizza = new Pizza();
        List<String> errors = List.of(
                "No dough chosen.",
                "No size chosen.",
                "No ingredients chosen."
        );

        List<String> result = pizzaValidator.isValid(pizza);

        assertIterableEquals(errors,result);
    }

    @Test
    void shouldReturnListOfErrorsWhenFieldsOfPizzaNotValid(){
        Pizza pizza = new Pizza(10L,
                new Dough(1L, ""),
                new Size(2L, 2),
                List.of(
                        new Ingredient(3L, "", -2.3)
                ));

        List<String> errors = List.of(
                "Dough should have a name.",
                "Pizza to small.",
                "Not enough ingredients.",
                "Ingredient should have a name.",
                "Price can not be negative."
        );

        when(doughValidator.isValid(pizza.getDough())).thenReturn("Dough should have a name.");
        when(sizeValidator.isValid(pizza.getSize())).thenReturn("Pizza to small.");
        when(ingredientValidator.isValid(pizza.getIngredients().get(0))).thenReturn(List.of(
                "Ingredient should have a name.",
                "Price can not be negative."
        ));

        List<String> result = pizzaValidator.isValid(pizza);

        assertIterableEquals(errors,result);
    }

}