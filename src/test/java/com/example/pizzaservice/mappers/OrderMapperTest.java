package com.example.pizzaservice.mappers;

import com.example.pizzaservice.db.DoughEntity;
import com.example.pizzaservice.db.IngredientEntity;
import com.example.pizzaservice.db.OrderEntity;
import com.example.pizzaservice.db.PizzaEntity;
import com.example.pizzaservice.db.SizeEntity;
import com.example.pizzaservice.model.Dough;
import com.example.pizzaservice.model.Ingredient;
import com.example.pizzaservice.model.Order;
import com.example.pizzaservice.model.Pizza;
import com.example.pizzaservice.model.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    private final OrderMapper orderMapper = new OrderMapper();
    private Order order = new Order();
    private OrderEntity entity = new OrderEntity();

    @BeforeEach
    void setup(){
        PizzaEntity pizzaEntity1 = PizzaEntity.builder()
                .id(2L)
                .ingredients(List.of(new IngredientEntity()))
                .dough(new DoughEntity())
                .size(new SizeEntity())
                .build();
        PizzaEntity pizzaEntity2 = PizzaEntity.builder()
                .id(3L)
                .ingredients(List.of(new IngredientEntity()))
                .dough(new DoughEntity())
                .size(new SizeEntity())
                .build();

        entity = OrderEntity.builder()
                .id(1L)
                .address("example address")
                .pizzas(List.of(pizzaEntity1, pizzaEntity2))
                .build();

        Pizza pizza1 = Pizza.builder()
                .id(2L)
                .ingredients(List.of(new Ingredient()))
                .dough(new Dough())
                .size(new Size())
                .build();
        Pizza pizza2 = Pizza.builder()
                .id(3L)
                .ingredients(List.of(new Ingredient()))
                .dough(new Dough())
                .size(new Size())
                .build();

        order = Order.builder()
                .id(1L)
                .address("example address")
                .pizzas(List.of(pizza1, pizza2))
                .build();

    }

    @Test
    void shouldMapEntityToOrder(){

        Optional<Order> result = orderMapper.mapEntityToOrder(entity);

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
    }

    @Test
    void shouldReturnEmptyOptionalIfNullEntity(){

        Optional<Order> result = orderMapper.mapEntityToOrder(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldMapOrderToEntity(){

        OrderEntity result = orderMapper.mapOrderToEntity(order);

        assertEquals(entity, result);

    }

}