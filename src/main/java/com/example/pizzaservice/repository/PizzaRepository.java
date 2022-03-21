package com.example.pizzaservice.repository;

import com.example.pizzaservice.db.PizzaEntity;
import org.springframework.data.repository.CrudRepository;

public interface PizzaRepository extends CrudRepository<PizzaEntity, Long> {
}
