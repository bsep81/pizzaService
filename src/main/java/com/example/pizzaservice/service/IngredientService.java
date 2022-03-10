package com.example.pizzaservice.service;

import com.example.pizzaservice.db.IngredientEntity;
import com.example.pizzaservice.mappers.IngredientMapper;
import com.example.pizzaservice.model.Ingredient;
import com.example.pizzaservice.repository.IngredientRepository;
import com.example.pizzaservice.validators.IngredientValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final IngredientValidator ingredientValidator;
    private static final Logger LOG = LoggerFactory.getLogger(IngredientService.class);
    private static final String INGREDIENT_NOT_FOUND = "Ingredient with id={} not found.";


    public IngredientService(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper, IngredientValidator ingredientValidator) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
        this.ingredientValidator = ingredientValidator;
    }

    public List<Ingredient> getList() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(entity -> ingredients.add(ingredientMapper.mapEntityToIngredient(entity).get()));
        return ingredients;
    }


    public Ingredient getIngredientById(long id) {
        Optional<IngredientEntity> ingredientEntityOptional = ingredientRepository.findById(id);

        if(ingredientEntityOptional.isEmpty()){
            LOG.info(INGREDIENT_NOT_FOUND, id);
            return new Ingredient();
        }

        LOG.info("Ingredient with id={} found.", id);
        return ingredientMapper.mapEntityToIngredient(ingredientEntityOptional.get()).get();
    }
}
