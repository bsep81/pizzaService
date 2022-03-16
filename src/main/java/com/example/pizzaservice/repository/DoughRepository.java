package com.example.pizzaservice.repository;

import com.example.pizzaservice.db.DoughEntity;
import org.springframework.data.repository.CrudRepository;

public interface DoughRepository extends CrudRepository<DoughEntity, Long> {
}
