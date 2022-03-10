package com.example.pizzaservice.service;

import com.example.pizzaservice.db.IngredientEntity;
import com.example.pizzaservice.mappers.IngredientMapper;
import com.example.pizzaservice.model.Ingredient;
import com.example.pizzaservice.repository.IngredientRepository;
import com.example.pizzaservice.validators.IngredientValidator;
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

@ExtendWith({MockitoExtension.class})
class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private IngredientValidator ingredientValidator;
    @Mock
    private IngredientMapper ingredientMapper;

    @InjectMocks
    private IngredientService ingredientService;

    @Test
    void shouldReturnListOfIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1L, "tomato", 4.6));
        ingredients.add(new Ingredient(2L, "mushroom", 2.1));
        ingredients.add(new Ingredient(3L, "ham", 5.5));

        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(
                IngredientEntity.builder().id(1L).build(),
                IngredientEntity.builder().id(2L).build(),
                IngredientEntity.builder().id(3L).build()
        ));

        when(ingredientMapper.mapEntityToIngredient(IngredientEntity.builder().id(1L).build())).thenReturn(Optional.of(new Ingredient(1L, "tomato", 4.6)));
        when(ingredientMapper.mapEntityToIngredient(IngredientEntity.builder().id(2L).build())).thenReturn(Optional.of(new Ingredient(2L, "mushroom", 2.1)));
        when(ingredientMapper.mapEntityToIngredient(IngredientEntity.builder().id(3L).build())).thenReturn(Optional.of(new Ingredient(3L, "ham", 5.5)));

        List<Ingredient> result = ingredientService.getList();

        assertIterableEquals(ingredients, result);
    }

    @Test
    void shouldReturnEmptyListIfNoIngredientsInDB() {
        when(ingredientRepository.findAll()).thenReturn(new ArrayList<>());

        List<Ingredient> result = ingredientService.getList();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnIngredientWithGivenId() {

        Ingredient ingredient = new Ingredient(1L, "tomato", 1.5);
        IngredientEntity entity = new IngredientEntity(1L, "tomato", 1.5);

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(entity));

        Optional<Ingredient> result = ingredientService.getIngredientById(1L);

        assertTrue(result.isPresent());
        assertEquals(ingredient, result.get());
    }

    @Test
    void shouldReturnEmptyOptionalIfIngredientWithGivenIdNotPresentInDB(){

        when(ingredientRepository.findById(3L)).thenReturn(Optional.empty());

        Optional<Ingredient> result = ingredientService.getIngredientById(3L);

        assertTrue(result.isEmpty());
    }


}