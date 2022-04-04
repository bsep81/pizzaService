package com.example.pizzaservice.service;

import com.example.pizzaservice.db.DoughEntity;
import com.example.pizzaservice.db.IngredientEntity;
import com.example.pizzaservice.db.OrderEntity;
import com.example.pizzaservice.db.PizzaEntity;
import com.example.pizzaservice.db.SizeEntity;
import com.example.pizzaservice.mappers.OrderMapper;
import com.example.pizzaservice.model.Dough;
import com.example.pizzaservice.model.Ingredient;
import com.example.pizzaservice.model.Order;
import com.example.pizzaservice.model.Pizza;
import com.example.pizzaservice.model.Size;
import com.example.pizzaservice.repository.OrderRepository;
import com.example.pizzaservice.validators.OrderValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderValidator orderValidator;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldReturnListOfOrders() {
        Pizza pizza1 = Pizza.builder()
                .id(5L)
                .dough(new Dough(1L, "thin", 2.2))
                .size(new Size(2L, 12))
                .ingredients(List.of(new Ingredient(3L, "tomato", 2.2),
                        new Ingredient(4L, "mushroom", 1.1)))
                .build();

        Pizza pizza2 = Pizza.builder()
                .id(6L)
                .dough(new Dough(7L, "thick", 2.2))
                .size(new Size(8L, 14))
                .ingredients(List.of(new Ingredient(3L, "tomato", 2.2),
                        new Ingredient(9L, "salami", 3.1),
                        new Ingredient(10L, "olive", 2.4)))
                .build();

        Order order1 = Order.builder()
                .id(11L)
                .address("address 1")
                .pizzas(List.of(pizza1, pizza2))
                .build();

        Order order2 = Order.builder()
                .id(12L)
                .address("address 2")
                .pizzas(List.of(pizza2, pizza1))
                .build();

        List<Order> orders = List.of(order1, order2);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(
                OrderEntity.builder().id(1L).build(),
                OrderEntity.builder().id(2L).build()
        ));

        when(orderMapper.mapEntityToOrder(OrderEntity.builder().id(1L).build())).thenReturn(Optional.of(order1));
        when(orderMapper.mapEntityToOrder(OrderEntity.builder().id(2L).build())).thenReturn(Optional.of(order2));

        List<Order> result = orderService.getList();

        assertIterableEquals(orders, result);
    }

    @Test
    void shouldReturnEmptyListWhenNoOrdersInDB() {
        when(orderService.getList()).thenReturn(new ArrayList<>());

        List<Order> result = orderService.getList();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnOrderWithGivenId() {
        Pizza pizza = Pizza.builder()
                .id(6L)
                .dough(new Dough(7L, "thick", 2.2))
                .size(new Size(8L, 14))
                .ingredients(List.of(new Ingredient(3L, "tomato", 2.2),
                        new Ingredient(9L, "salami", 3.1),
                        new Ingredient(10L, "olive", 2.4)))
                .build();

        PizzaEntity pizzaEntity = PizzaEntity.builder()
                .id(6L)
                .dough(new DoughEntity(7L, "thick", 2.2))
                .size(new SizeEntity(8L, 14))
                .ingredients(List.of(new IngredientEntity(3L, "tomato", 2.2),
                        new IngredientEntity(9L, "salami", 3.1),
                        new IngredientEntity(10L, "olive", 2.4)))
                .build();

        Order order = Order.builder()
                .id(11L)
                .address("address 1")
                .pizzas(List.of(pizza))
                .build();

        Optional<OrderEntity> orderEntityOptional = Optional.of(OrderEntity.builder()
                .id(11L)
                .address("address 1")
                .pizzas(List.of(pizzaEntity))
                .build());

        when(orderRepository.findById(11L)).thenReturn(orderEntityOptional);
        when(orderMapper.mapEntityToOrder(orderEntityOptional.get())).thenReturn(Optional.ofNullable(order));

        Order result = orderService.findById(11L);

        assertEquals(order, result);
    }

    @Test
    void shouldReturnEmptyOptionalWhenOrderWithGivenIdNotInDB(){
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        Order result = orderService.findById(1L);

        assertEquals(new Order(), result);
    }

    @Test
    void shouldReturnSavedOrder(){

        Pizza pizza = Pizza.builder()
                .id(6L)
                .dough(new Dough(7L, "thick", 2.2))
                .size(new Size(8L, 14))
                .ingredients(List.of(new Ingredient(3L, "tomato", 2.2),
                        new Ingredient(9L, "salami", 3.1),
                        new Ingredient(10L, "olive", 2.4)))
                .build();

        PizzaEntity pizzaEntity = PizzaEntity.builder()
                .id(6L)
                .dough(new DoughEntity(7L, "thick", 2.2))
                .size(new SizeEntity(8L, 14))
                .ingredients(List.of(new IngredientEntity(3L, "tomato", 2.2),
                        new IngredientEntity(9L, "salami", 3.1),
                        new IngredientEntity(10L, "olive", 2.4)))
                .build();

        Order order = Order.builder()
                .id(11L)
                .address("address 1")
                .pizzas(List.of(pizza))
                .build();

        OrderEntity created = OrderEntity.builder()
                .id(11L)
                .address("address 1")
                .pizzas(List.of(pizzaEntity))
                .build();

        when(orderValidator.isValid(order)).thenReturn(new ArrayList<>());
        when(orderRepository.save(orderMapper.mapOrderToEntity(order))).thenReturn(created);
        when(orderMapper.mapEntityToOrder(created)).thenReturn(Optional.of(order));

        Order result = orderService.save(order);

        assertEquals(order, result);
    }

}