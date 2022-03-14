package com.example.pizzaservice.service;

import com.example.pizzaservice.db.IngredientEntity;
import com.example.pizzaservice.exceptions.IngredientException;
import com.example.pizzaservice.mappers.IngredientMapper;
import com.example.pizzaservice.model.Ingredient;
import com.example.pizzaservice.repository.IngredientRepository;
import com.example.pizzaservice.validators.IngredientValidator;
import lombok.extern.java.Log;
import org.apache.logging.log4j.util.Strings;
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
        ingredientRepository.findAll().forEach(entity -> ingredients.add(ingredientMapper.mapEntityToIngredient(entity).orElseGet(Ingredient::new)));
        return ingredients;
    }


    public Ingredient getIngredientById(long id) {
        Optional<IngredientEntity> ingredientEntityOptional = ingredientRepository.findById(id);

        if(ingredientEntityOptional.isEmpty()){
            LOG.info(INGREDIENT_NOT_FOUND, id);
            return new Ingredient();
        }

        LOG.info("Ingredient with id={} found.", id);
        return ingredientMapper.mapEntityToIngredient(ingredientEntityOptional.get()).orElseGet(Ingredient::new);
    }

    public Ingredient save(Ingredient ingredient) {
        List<String> errors = ingredientValidator.isValid(ingredient);
        if(!errors.isEmpty()){
            LOG.info("Ingredient not valid.");
            throw new IngredientException(Strings.join(errors, ','));
        }

        IngredientEntity created = ingredientRepository.save(ingredientMapper.mapIngredientToEntity(ingredient));
        LOG.info("Succesfully saved ingredient {} to database.", ingredient.getName());
        return ingredientMapper.mapEntityToIngredient(created).get();
    }

    public void deleteIngredient(Long id){
        Optional<IngredientEntity> ingredientEntityOptional = ingredientRepository.findById(id);
        ingredientEntityOptional.ifPresentOrElse(ingredientEntity -> {
            ingredientRepository.delete(ingredientEntity);
            LOG.info("Ingredient with id {} removed from database.", id);
        }, () -> LOG.info(INGREDIENT_NOT_FOUND, id));
    }

    private Ingredient update(Ingredient ingredient){
        if(ingredient.getId() != null && ingredientRepository.findById(ingredient.getId()).isPresent()){
            return save(ingredient);
        }

        LOG.info(INGREDIENT_NOT_FOUND, ingredient.getId());
        return null;
    }
}
