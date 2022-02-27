package com.example.pizzaservice.mappers;

import com.example.pizzaservice.db.PizzaEntity;
import com.example.pizzaservice.model.Dough;
import com.example.pizzaservice.model.Ingridient;
import com.example.pizzaservice.model.Pizza;
import com.example.pizzaservice.model.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PizzaMapper {

    DoughMapper doughMapper = new DoughMapper();
    SizeMapper sizeMapper = new SizeMapper();
    IngridientMapper ingridientMapper = new IngridientMapper();

    public Optional<Pizza> mapEntityToPizza(PizzaEntity entity) {
        if (entity == null) {
            return Optional.empty();
        }

        List<Ingridient> ingridients = new ArrayList<>();

        if(!entity.getIngridients().isEmpty()) {
            ingridients = entity.getIngridients().stream()
                    .map(i -> ingridientMapper.mapEntityToIngridient(i).get())
                    .collect(Collectors.toList());
        }

        Pizza pizza = Pizza.builder()
                .Id(entity.getId())
                .dough(doughMapper.mapEntityToDough(entity.getDough()).orElse(new Dough()))
                .size(sizeMapper.mapEntityToSize(entity.getSize()).orElse(new Size()))
                .ingridients(ingridients)
                .build();

        return Optional.of(pizza);
    }
}
