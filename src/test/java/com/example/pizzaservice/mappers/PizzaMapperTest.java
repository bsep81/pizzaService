package com.example.pizzaservice.mappers;

import com.example.pizzaservice.db.DoughEntity;
import com.example.pizzaservice.db.IngridientEntity;
import com.example.pizzaservice.db.PizzaEntity;
import com.example.pizzaservice.db.SizeEntity;
import com.example.pizzaservice.model.Dough;
import com.example.pizzaservice.model.Ingridient;
import com.example.pizzaservice.model.Pizza;
import com.example.pizzaservice.model.Size;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PizzaMapperTest {

    PizzaMapper pizzaMapper = new PizzaMapper();

    @Test
    void shouldMapEntityToPizza(){

        PizzaEntity entity = PizzaEntity.builder()
                .Id(1L)
                .dough(new DoughEntity(2L, "thin"))
                .size(new SizeEntity(3L, 15))
                .ingridients(List.of(new IngridientEntity(4L, "ham", 0.34),
                        new IngridientEntity(5L, "mushroom", 0.23)))
                .build();

        Pizza pizza = Pizza.builder()
                .Id(1L)
                .dough(new Dough(2L, "thin"))
                .size(new Size(3L, 15))
                .ingridients(List.of(new Ingridient(4L, "ham", 0.34),
                        new Ingridient(5L, "mushroom", 0.23)))
                .build();

        Optional<Pizza> result = pizzaMapper.mapEntityToPizza(entity);

        assertTrue(result.isPresent());
        assertEquals(pizza, result.get());
    }

    @Test
    void shouldReturnEmtyOptionalIfNullEntity(){

        Optional<Pizza> result = pizzaMapper.mapEntityToPizza(null);

        assertTrue(result.isEmpty());
    }

}