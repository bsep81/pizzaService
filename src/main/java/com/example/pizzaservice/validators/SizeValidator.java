package com.example.pizzaservice.validators;

import com.example.pizzaservice.model.Size;
import org.springframework.stereotype.Component;

@Component
public class SizeValidator {

    public String isValid(Size size){

        String error = "";
        if(size.getDiameter() == null){
            error = "Pizza needs to have a size.";
        } else if(size.getDiameter() < 5){
            error = "Pizza to small.";
        } else if(size.getDiameter() > 25){
            error = "Pizza to large.";
        }

        return error;
    }
}
