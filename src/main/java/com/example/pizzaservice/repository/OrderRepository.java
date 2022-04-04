package com.example.pizzaservice.repository;

import com.example.pizzaservice.db.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
}
