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

}
