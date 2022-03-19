package com.example.pizzaservice.repository;

import com.example.pizzaservice.db.SizeEntity;
import org.springframework.data.repository.CrudRepository;

public interface SizeRepository extends CrudRepository<SizeEntity, Long> {
}
