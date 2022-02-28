package com.example.pizzaservice.mappers;

import com.example.pizzaservice.db.IngredientEntity;
import com.example.pizzaservice.db.PizzaEntity;
import com.example.pizzaservice.model.Dough;
import com.example.pizzaservice.model.Ingredient;
import com.example.pizzaservice.model.Pizza;
import com.example.pizzaservice.model.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PizzaMapper {

    DoughMapper doughMapper = new DoughMapper();
    SizeMapper sizeMapper = new SizeMapper();
    IngredientMapper ingredientMapper = new IngredientMapper();

    public Optional<Pizza> mapEntityToPizza(PizzaEntity entity) {
        if (entity == null) {
            return Optional.empty();
        }

        List<Ingredient> ingredients = new ArrayList<>();

        if(!entity.getIngridients().isEmpty()) {
            ingredients = entity.getIngridients().stream()
                    .map(i -> ingredientMapper.mapEntityToIngredient(i).get())
                    .collect(Collectors.toList());
        }

        Pizza pizza = Pizza.builder()
                .Id(entity.getId())
                .dough(doughMapper.mapEntityToDough(entity.getDough()).orElse(new Dough()))
                .size(sizeMapper.mapEntityToSize(entity.getSize()).orElse(new Size()))
                .ingredients(ingredients)
                .build();

        return Optional.of(pizza);
    }

    public PizzaEntity mapPizzaToEntity(Pizza pizza) {

        List<IngredientEntity> ingredients = new ArrayList<>();

        if(!pizza.getIngredients().isEmpty()) {
            ingredients = pizza.getIngredients().stream()
                    .map(i -> ingredientMapper.mapIngredientToEntity(i))
                    .collect(Collectors.toList());
        }

        return  PizzaEntity.builder()
                .Id(pizza.getId())
                .dough(doughMapper.mapDoughToEntity(pizza.getDough()))
                .size(sizeMapper.mapSizeToEntity(pizza.getSize()))
                .ingridients(ingredients)
                .build();
    }
}
