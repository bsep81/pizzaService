package com.example.pizzaservice.mappers;
import com.example.pizzaservice.db.OrderEntity;
import com.example.pizzaservice.db.PizzaEntity;
import com.example.pizzaservice.model.Order;
import com.example.pizzaservice.model.Pizza;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final PizzaMapper pizzaMapper;

    public OrderMapper(PizzaMapper pizzaMapper) {
        this.pizzaMapper = pizzaMapper;
    }

    public Optional<Order> mapEntityToOrder(OrderEntity entity) {



        if(entity == null){
            return Optional.empty();
        }

        List<Pizza> pizzas = new ArrayList<>();
        if(!entity.getPizzas().isEmpty()){
            pizzas = entity.getPizzas().stream()
                    .map(p -> pizzaMapper.mapEntityToPizza(p).get())
                    .collect(Collectors.toList());
        }

        Order order = Order.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .pizzas(pizzas)
                .build();

        return Optional.of(order);
    }

    public OrderEntity mapOrderToEntity(Order order) {

        List<PizzaEntity> pizzas = new ArrayList<>();

        if(!order.getPizzas().isEmpty()){
            pizzas = order.getPizzas().stream()
                    .map(pizzaMapper::mapPizzaToEntity)
                    .collect(Collectors.toList());
        }

        return OrderEntity.builder()
                .id(order.getId())
                .address(order.getAddress())
                .pizzas(pizzas)
                .build();
    }
}
