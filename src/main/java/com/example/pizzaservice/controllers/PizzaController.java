package com.example.pizzaservice.controllers;

import com.example.pizzaservice.model.Pizza;
import com.example.pizzaservice.service.PizzaService;
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
@RequestMapping("/api/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;
    private static final Logger LOG = LoggerFactory.getLogger(PizzaController.class);

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping("/list")
    public List<Pizza> getPizzaList(){
        LOG.info("Getting pizzas list.");
        return pizzaService.getList();
    }

    @GetMapping("/{id}")
    public Pizza getPizza(@PathVariable("id") Long id){
        LOG.info("Getting pizza details for id={}.", id);
        return pizzaService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Pizza addPizza(@RequestBody Pizza pizza){
        LOG.info("Adding {} to database", pizza.getId());
        return pizzaService.save(pizza);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePizza(@PathVariable("id") Long id){
        LOG.info("Attempting to delete pizza with id={} from database.", id);
        pizzaService.deletePizza(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    public Pizza updatePizza(@RequestBody Pizza pizza){
        LOG.info("Attempting to update pizza with id={}.", pizza.getId());
        return pizzaService.updatePizza(pizza);
    }

}
