package com.example.pizzaservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pizza {

    private Long id;
    private String name;
    private Dough dough;
    private Size size;
    private List<Ingredient> ingredients;

    public Double getPrice() {
        double price;
        Double sizeFactor = (getSize().getDiameter() * getSize().getDiameter()) / 100D;
        price = ingredients.stream().mapToDouble(ingredient -> ingredient.getBasePrice() * sizeFactor).sum();
        price += dough.getBasePrice() * sizeFactor;
        return (int) (price * 100) / 100D;
    }

}
