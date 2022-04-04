package com.example.pizzaservice.validators;

import com.example.pizzaservice.model.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderValidator {

    public List<String> isValid(Order order){
        List<String> errors = new ArrayList<>();

        if(order.getAddress() == null || order.getAddress().isEmpty()){
            errors.add("Address not valid.");
        }

        if(order.getPizzas().isEmpty()){
            errors.add("No pizzas in order.");
        }


        return errors;
    }
}
