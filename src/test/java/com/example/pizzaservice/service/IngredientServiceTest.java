package com.example.pizzaservice.service;

import com.example.pizzaservice.db.IngredientEntity;
import com.example.pizzaservice.exceptions.IngredientException;
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

@ExtendWith(MockitoExtension.class)
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
        Optional<IngredientEntity> ingredientEntityOptional = Optional.of(new IngredientEntity(1L, "tomato", 1.5));

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(new IngredientEntity(1L, "tomato", 1.5)));
        when(ingredientMapper.mapEntityToIngredient(ingredientEntityOptional.get())).thenReturn(Optional.of(ingredient));

        Ingredient result = ingredientService.getIngredientById(1L);

        assertEquals(ingredient, result);
    }

    @Test
    void shouldReturnEmptyOptionalIfIngredientWithGivenIdNotPresentInDB(){

        when(ingredientRepository.findById(3L)).thenReturn(Optional.empty());

        Ingredient result = ingredientService.getIngredientById(3L);

        assertEquals(new Ingredient(), result);
    }

    @Test
    void shouldReturnSavedIngredient(){
        Ingredient ingredient = new Ingredient(1L, "tomato", 1.5);
        IngredientEntity created = new IngredientEntity(1L, "tomato", 1.5);
        when(ingredientValidator.isValid(ingredient)).thenReturn(new ArrayList<>());
        when(ingredientRepository.save(ingredientMapper.mapIngredientToEntity(ingredient))).thenReturn(created);
        when(ingredientMapper.mapEntityToIngredient(created)).thenReturn(Optional.of(ingredient));

        Ingredient result = ingredientService.save(ingredient);

        assertEquals(ingredient, result);
    }

    @Test
    void shouldThrowIngredientExceptionWhenIngredientNotValid(){
        Ingredient ingredient = Ingredient.builder()
                .price(-3.3)
                .build();

        when(ingredientValidator.isValid(ingredient)).thenReturn(List.of(
                "Ingredient should have a name.",
                "Price can not be negative."
        ));

        IngredientException exception = assertThrows(IngredientException.class, () -> ingredientService.save(ingredient));
        assertEquals("Ingredient should have a name.,Price can not be negative.", exception.getMessage());
    }


}