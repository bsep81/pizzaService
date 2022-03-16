package com.example.pizzaservice.controllers;

import com.example.pizzaservice.model.Ingredient;
import com.example.pizzaservice.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    public List<Ingredient> getIngredientList(){
        LOG.info("Getting ingredients list.");
        return ingredientService.getList();
    }

    @GetMapping("/{id}")
    public Ingredient getIngredient(@PathVariable("id") Long id){
        LOG.info("Getting ingredient details for id = {}.", id);
        return ingredientService.getIngredientById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Ingredient addIngredient(@RequestBody Ingredient ingredient){
        LOG.info("Adding {} to database", ingredient.getName());
        return ingredientService.save(ingredient);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable("id") Long id){
        LOG.info("Attempting to delete ingredient with id = {} from database.", id);
        ingredientService.deleteIngredient(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    public Ingredient updateIngredient(@RequestBody Ingredient ingredient){
        LOG.info("Attempting to update ingredient with id {}.", ingredient.getId());
        return ingredientService.update(ingredient);
    }
}
