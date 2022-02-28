package com.example.pizzaservice.mappers;

import com.example.pizzaservice.db.IngredientEntity;
import com.example.pizzaservice.model.Ingredient;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class IngredientMapperTest {

    private final IngredientMapper ingredientMapper = new IngredientMapper();

    @Test
    void shouldMapEntityToIngredient(){
        IngredientEntity entity =  new IngredientEntity(1L, "tomato", 0.59);
        Ingredient ingredient = new Ingredient(1L, "tomato", 0.59);

        Optional<Ingredient> result = ingredientMapper.mapEntityToIngredient(entity);
        assertTrue(result.isPresent());
        assertEquals(ingredient, result.get());
    }

    @Test
    void shouldReturnEmptyOptionalIfNullEntity(){

        Optional<Ingredient> result = ingredientMapper.mapEntityToIngredient(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldMapIngredientToEntity(){
        Ingredient ingredient = new Ingredient(2L, "mushroom", 0.45);
        IngredientEntity ingredientEntity = new IngredientEntity(2L, "mushroom", 0.45);

        IngredientEntity result = ingredientMapper.mapIngredientToEntity(ingredient);

        assertEquals(ingredientEntity, result);
    }



}