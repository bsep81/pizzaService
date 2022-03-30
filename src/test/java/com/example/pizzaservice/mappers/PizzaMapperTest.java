package com.example.pizzaservice.mappers;

import com.example.pizzaservice.db.DoughEntity;
import com.example.pizzaservice.db.IngredientEntity;
import com.example.pizzaservice.db.PizzaEntity;
import com.example.pizzaservice.db.SizeEntity;
import com.example.pizzaservice.model.Dough;
import com.example.pizzaservice.model.Ingredient;
import com.example.pizzaservice.model.Pizza;
import com.example.pizzaservice.model.Size;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PizzaMapperTest {


    private final DoughMapper doughMapper = new DoughMapper();
    private final SizeMapper sizeMapper = new SizeMapper();
    private final IngredientMapper ingredientMapper = new IngredientMapper();
    private final PizzaMapper pizzaMapper = new PizzaMapper(doughMapper, sizeMapper, ingredientMapper);




    @Test
    void shouldMapEntityToPizza(){

        PizzaEntity entity = PizzaEntity.builder()
                .id(1L)
                .dough(new DoughEntity(2L, "thin", 2.2))
                .size(new SizeEntity(3L, 15))
                .ingredients(List.of(new IngredientEntity(4L, "ham", 0.34),
                        new IngredientEntity(5L, "mushroom", 0.23)))
                .build();

        Pizza pizza = Pizza.builder()
                .id(1L)
                .dough(new Dough(2L, "thin", 2.2))
                .size(new Size(3L, 15))
                .ingredients(List.of(new Ingredient(4L, "ham", 0.34),
                        new Ingredient(5L, "mushroom", 0.23)))
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

    @Test
    void shouldMapPizzaToEntity(){

        PizzaEntity entity = PizzaEntity.builder()
                .id(1L)
                .dough(new DoughEntity(2L, "thin", 2.2))
                .size(new SizeEntity(3L, 15))
                .ingredients(List.of(new IngredientEntity(4L, "ham", 0.34),
                        new IngredientEntity(5L, "mushroom", 0.23)))
                .build();

        Pizza pizza = Pizza.builder()
                .id(1L)
                .dough(new Dough(2L, "thin", 2.2))
                .size(new Size(3L, 15))
                .ingredients(List.of(new Ingredient(4L, "ham", 0.34),
                        new Ingredient(5L, "mushroom", 0.23)))
                .build();

        PizzaEntity result = pizzaMapper.mapPizzaToEntity(pizza);

        assertEquals(entity, result);

    }

}