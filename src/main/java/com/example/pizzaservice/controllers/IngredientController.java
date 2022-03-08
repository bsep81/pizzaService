package com.example.pizzaservice.controllers;

import com.example.pizzaservice.model.Ingredient;
import com.example.pizzaservice.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;
    private static final Logger LOG = LoggerFactory.getLogger(IngredientController.class);

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/list")
    public List<Ingredient> getList(){
        LOG.info("Getting ingredients list.");
        return ingredientService.getList();
    }
}
