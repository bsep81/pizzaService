package com.example.pizzaservice.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {


    @Test
    void shouldReturnOrderPrice(){
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

        Double price = 17.14;

        Double result = order.getPrice();

        assertEquals(price, result);
    }


}
