package com.example.pizzaservice.repository;

import com.example.pizzaservice.db.IngredientEntity;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<IngredientEntity, Long> {
}
