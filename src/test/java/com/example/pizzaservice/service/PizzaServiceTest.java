package com.example.pizzaservice.service;

import com.example.pizzaservice.db.DoughEntity;
import com.example.pizzaservice.db.IngredientEntity;
import com.example.pizzaservice.db.PizzaEntity;
import com.example.pizzaservice.db.SizeEntity;
import com.example.pizzaservice.mappers.PizzaMapper;
import com.example.pizzaservice.model.Dough;
import com.example.pizzaservice.model.Ingredient;
import com.example.pizzaservice.model.Pizza;
import com.example.pizzaservice.model.Size;
import com.example.pizzaservice.repository.PizzaRepository;
import com.example.pizzaservice.validators.PizzaValidator;
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
class PizzaServiceTest {

    @Mock
    private PizzaRepository pizzaRepository;
    @Mock
    private PizzaMapper pizzaMapper;
    @Mock
    private PizzaValidator pizzaValidator;

    @InjectMocks
    private PizzaService pizzaService;

    @Test
    void shouldReturnListOfPizzas(){
        Pizza pizza1 = Pizza.builder()
                .id(5L)
                .dough(new Dough(1L, "thin", 2.2))
                .size(new Size(2L, 12))
                .ingredients(List.of(new Ingredient(3L, "tomato", 2.2),
                        new Ingredient(4L, "mushroom", 1.1)))
                .build();

        Pizza pizza2 = Pizza.builder()
                .id(6L)
                .dough(new Dough(7L, "thick", 2.2))
                .size(new Size(8L, 14))
                .ingredients(List.of(new Ingredient(3L, "tomato", 2.2),
                        new Ingredient(9L, "salami", 3.1),
                        new Ingredient(10L, "olive", 2.4)))
                .build();

        List<Pizza> pizzas = List.of(pizza1, pizza2);

        when(pizzaRepository.findAll()).thenReturn(Arrays.asList(
                PizzaEntity.builder().id(1L).build(),
                PizzaEntity.builder().id(2L).build()
        ));

        when(pizzaMapper.mapEntityToPizza(PizzaEntity.builder().id(1L).build())).thenReturn(Optional.of(pizza1));
        when(pizzaMapper.mapEntityToPizza(PizzaEntity.builder().id(2L).build())).thenReturn(Optional.of(pizza2));

        List<Pizza> result = pizzaService.getList();

        assertIterableEquals(pizzas, result);
    }

    @Test
    void shouldReturnEmptyListWhenNoPizzasInDB(){
        when(pizzaService.getList()).thenReturn(new ArrayList<>());

        List<Pizza> result = pizzaService.getList();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnPizzaWithGivenId(){
        Pizza pizza = Pizza.builder()
                .id(5L)
                .dough(new Dough(1L, "thin", 2.2))
                .size(new Size(2L, 12))
                .ingredients(List.of(new Ingredient(3L, "tomato", 2.2),
                        new Ingredient(4L, "mushroom", 1.1)))
                .build();

        Optional<PizzaEntity> pizzaEntityOptional = Optional.of(PizzaEntity.builder()
                .id(5L)
                .dough(new DoughEntity(1L, "thin", 2.2))
                .size(new SizeEntity(2L, 12))
                .ingredients(List.of(new IngredientEntity(3L, "tomato", 2.2),
                        new IngredientEntity(4L, "mushroom", 1.1)))
                .build());

        when(pizzaRepository.findById(5L)).thenReturn(Optional.of(PizzaEntity.builder()
                .id(5L)
                .dough(new DoughEntity(1L, "thin", 2.2))
                .size(new SizeEntity(2L, 12))
                .ingredients(List.of(new IngredientEntity(3L, "tomato", 2.2),
                        new IngredientEntity(4L, "mushroom", 1.1)))
                .build()));
        when(pizzaMapper.mapEntityToPizza(pizzaEntityOptional.get())).thenReturn(Optional.ofNullable(pizza));

        Pizza result = pizzaService.findById(5L);

        assertEquals(pizza, result);
    }

    @Test
    void shouldReturnEmtyOptionalWhenPizzaWithGivenIdNotInDB(){
        when(pizzaRepository.findById(10L)).thenReturn(Optional.empty());

        Pizza result = pizzaService.findById(10L);

        assertEquals(new Pizza(), result);
    }

    @Test
    void shouldReturnSavedPizza(){
        Pizza pizza = Pizza.builder()
                .id(5L)
                .dough(new Dough(1L, "thin", 2.2))
                .size(new Size(2L, 12))
                .ingredients(List.of(new Ingredient(3L, "tomato", 2.2),
                        new Ingredient(4L, "mushroom", 1.1)))
                .build();

        PizzaEntity created = PizzaEntity.builder()
                .id(5L)
                .dough(new DoughEntity(1L, "thin", 2.2))
                .size(new SizeEntity(2L, 12))
                .ingredients(List.of(new IngredientEntity(3L, "tomato", 2.2),
                        new IngredientEntity(4L, "mushroom", 1.1)))
                .build();

        when(pizzaValidator.isValid(pizza)).thenReturn(new ArrayList<>());
        when(pizzaRepository.save(pizzaMapper.mapPizzaToEntity(pizza))).thenReturn(created);
        when(pizzaMapper.mapEntityToPizza(created)).thenReturn(Optional.of(pizza));

        Pizza result = pizzaService.save(pizza);

        assertEquals(pizza, result);
    }



}