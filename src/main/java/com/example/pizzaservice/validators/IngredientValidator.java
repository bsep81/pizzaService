package com.example.pizzaservice.validators;

import com.example.pizzaservice.model.Ingredient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IngredientValidator {

    public List<String> isValid(Ingredient ingredient) {

        List<String> errors = new ArrayList<>();

        if(ingredient.getName() == null || ingredient.getName().isEmpty()){
            errors.add("Ingredient should have a name.");
        }

        if(ingredient.getBasePrice() == null){
            errors.add("Ingredient should have a price.");
        }else if(ingredient.getBasePrice() < 0){
            errors.add("Price can not be negative.");
        }

        return errors;
    }
}
