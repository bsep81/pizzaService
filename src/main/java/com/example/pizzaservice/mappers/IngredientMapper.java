package com.example.pizzaservice.mappers;

import com.example.pizzaservice.db.IngredientEntity;
import com.example.pizzaservice.model.Ingredient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IngredientMapper {

    public Optional<Ingredient> mapEntityToIngredient(IngredientEntity entity) {

        if(entity == null){
            return Optional.empty();
        }

        Ingredient ingredient = Ingredient.builder()
                .id(entity.getId())
                .name(entity.getName())
                .basePrice(entity.getPrice())
                .build();

        return Optional.of(ingredient);
    }

    public IngredientEntity mapIngredientToEntity(Ingredient ingredient) {
        return IngredientEntity.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .price(ingredient.getBasePrice())
                .build();
    }
}
