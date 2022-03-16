package com.example.pizzaservice.validators;

import com.example.pizzaservice.model.Dough;
import org.springframework.stereotype.Component;

@Component
public class DoughValidator {
    public String isValid(Dough dough) {

        String error = "";

        if(dough.getName() == null || dough.getName().isEmpty()){
            error = "Dough should have a name.";
        }

        return error;
    }
}
