package com.example.pizzaservice.validators;

import com.example.pizzaservice.model.Ingredient;
import com.example.pizzaservice.model.Pizza;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PizzaValidator {
    private final DoughValidator doughValidator;
    private final SizeValidator sizeValidator;
    private final IngredientValidator ingredientValidator;

    public PizzaValidator(DoughValidator doughValidator, SizeValidator sizeValidator, IngredientValidator ingredientValidator) {
        this.doughValidator = doughValidator;
        this.sizeValidator = sizeValidator;
        this.ingredientValidator = ingredientValidator;
    }

    public List<String> isValid(Pizza pizza){
        List<String> errors = new ArrayList<>();


        if(pizza.getDough() == null){
            errors.add("No dough chosen.");
        }else if(!doughValidator.isValid(pizza.getDough()).isEmpty()){
            errors.addAll(doughValidator.isValid(pizza.getDough()));
        }

        if(pizza.getName() == null || pizza.getName().isEmpty()){
            errors.add("Pizza should have a name.");
        }

        if(pizza.getSize() == null){
            errors.add("No size chosen.");
        }else if(!sizeValidator.isValid(pizza.getSize()).isEmpty()){
            errors.add(sizeValidator.isValid(pizza.getSize()));
        }

        if(pizza.getIngredients() == null || pizza.getIngredients().isEmpty()){
            errors.add("No ingredients chosen.");
        }else{
            if(pizza.getIngredients().size() < 2){
                errors.add("Not enough ingredients.");
            }
            for(Ingredient ingredient: pizza.getIngredients()){
                errors.addAll(ingredientValidator.isValid(ingredient));
            }
        }


        return errors;
    }
}
