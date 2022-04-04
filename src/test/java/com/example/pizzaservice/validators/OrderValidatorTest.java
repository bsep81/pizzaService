package com.example.pizzaservice.validators;

import com.example.pizzaservice.model.Dough;
import com.example.pizzaservice.model.Ingredient;
import com.example.pizzaservice.model.Order;
import com.example.pizzaservice.model.Pizza;
import com.example.pizzaservice.model.Size;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderValidatorTest {

    private final OrderValidator orderValidator = new OrderValidator();

    @Test
    void shouldReturnEmtyListIfOrderIssValid(){
        Pizza pizza1 = Pizza.builder()
                .id(1L)
                .size(new Size(2L, 12))
                .dough(new Dough(3L, "thin", 1.5))
                .name("marerita")
                .ingredients(List.of(new Ingredient(4L, "tomato", 1.2),
                        new Ingredient(5L, "salami", 2.4)))
                .build();

        Pizza pizza2 = Pizza.builder()
                .id(1L)
                .size(new Size(2L, 11))
                .dough(new Dough(3L, "thin", 2.5))
                .name("marerita")
                .ingredients(List.of(new Ingredient(4L, "tomato", 2.2),
                        new Ingredient(5L, "salami", 3.4)))
                .build();

        Order order = Order.builder()
                .id(5L)
                .address("some address")
                .pizzas(List.of(pizza1, pizza2))
                .build();

        List<String> result = orderValidator.isValid(order);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnListOfErrorsIfOrderIsNotValid(){
        Order order = Order.builder()
                .pizzas(new ArrayList<>())
                .build();

        List<String> errors = List.of("Address not valid.", "No pizzas in order.");

        List<String> result = orderValidator.isValid(order);

        assertIterableEquals(errors, result);
    }

}