package com.example.pizzaservice.service;

import com.example.pizzaservice.db.PizzaEntity;
import com.example.pizzaservice.exceptions.PizzaEception;
import com.example.pizzaservice.mappers.PizzaMapper;
import com.example.pizzaservice.model.Pizza;
import com.example.pizzaservice.repository.PizzaRepository;
import com.example.pizzaservice.validators.PizzaValidator;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final PizzaMapper pizzaMapper;
    private final PizzaValidator pizzaValidator;

    private static final Logger LOG = LoggerFactory.getLogger(PizzaService.class);
    private static final String PIZZA_NOT_FOUND = "Pizza with id {} not found.";

    public PizzaService(PizzaRepository pizzaRepository, PizzaMapper pizzaMapper, PizzaValidator pizzaValidator) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaMapper = pizzaMapper;
        this.pizzaValidator = pizzaValidator;
    }

    public List<Pizza> getList() {
        List<Pizza> pizzas = new ArrayList<>();
        pizzaRepository.findAll().forEach(pizzaEntity -> pizzas.add(pizzaMapper.mapEntityToPizza(pizzaEntity).orElseGet(Pizza::new)));
        return pizzas;
    }

    public Pizza findById(long id) {
        Optional<PizzaEntity> pizzaEntityOptional = pizzaRepository.findById(id);

        if (pizzaEntityOptional.isEmpty()) {
            LOG.info(PIZZA_NOT_FOUND, id);
            return new Pizza();
        }

        LOG.info("Pizza wit id={} found.", id);
        return pizzaMapper.mapEntityToPizza(pizzaEntityOptional.get()).orElseGet(Pizza::new);

    }

    public Pizza save(Pizza pizza) {
        List<String> errors = pizzaValidator.isValid(pizza);
        if (!errors.isEmpty()) {
            LOG.info("Pizza not valid");
            throw new PizzaEception(Strings.join(errors, ' '));
        }

        PizzaEntity created = pizzaRepository.save(pizzaMapper.mapPizzaToEntity(pizza));
        LOG.info("Succesfully saned pizza {} to database", pizza.getId());
        return pizzaMapper.mapEntityToPizza(created).get();
    }

    public void deletePizza(Long id) {
        Optional<PizzaEntity> pizzaEntityOptional = pizzaRepository.findById(id);

        pizzaEntityOptional.ifPresentOrElse(pizzaEntity -> {
            pizzaRepository.delete(pizzaEntity);
            LOG.info("Pizza with id={} removed from database.", id);
        }, () -> LOG.info(PIZZA_NOT_FOUND, id));
    }

    public Pizza updatePizza(Pizza pizza){
        if(pizza.getId() != null && pizzaRepository.findById(pizza.getId()).isPresent()){
            return save(pizza);
        }

        LOG.info(PIZZA_NOT_FOUND, pizza.getId());
        return null;
    }


}
