package com.example.pizzaservice.validators;

import com.example.pizzaservice.model.Dough;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DoughValidator {
    public List<String> isValid(Dough dough) {

        List<String> errors = new ArrayList<>();

        if(dough.getName() == null || dough.getName().isEmpty()){
            errors.add("Dough should have a name.");
        }

        if(dough.getBasePrice() == null){
            errors.add("Dough should have a price");
        }else if(dough.getBasePrice() < 0 ){
            errors.add("Price can not be negative");
        }

        return errors;
    }
}
