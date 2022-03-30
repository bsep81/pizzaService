package com.example.pizzaservice.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PizzaTest {

    @Test
    void shouldReturnPizzaPrice(){
        Pizza pizza = Pizza.builder()
                .id(1L)
                .size(new Size(2L, 12))
                .dough(new Dough(3L, "thin", 1.5))
                .name("marerita")
                .ingredients(List.of(new Ingredient(4L, "tomato", 1.2),
                        new Ingredient(5L, "salami", 2.4)))
                .build();

        Double price = 7.34;

        Double result = pizza.getPrice();

        assertEquals(price, result);
    }

}