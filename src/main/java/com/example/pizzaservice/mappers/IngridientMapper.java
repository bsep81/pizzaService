package com.example.pizzaservice.mappers;

import com.example.pizzaservice.db.IngridientEntity;
import com.example.pizzaservice.model.Ingridient;

import java.util.Optional;

public class IngridientMapper {
    public Optional<Ingridient> mapEntityToIngridient(IngridientEntity entity) {

        if(entity == null){
            return Optional.empty();
        }

        Ingridient ingridient = Ingridient.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .build();

        return Optional.of(ingridient);
    }

    public IngridientEntity mapIngridientToEntity(Ingridient ingridient) {
        return IngridientEntity.builder()
                .id(ingridient.getId())
                .name(ingridient.getName())
                .price(ingridient.getPrice())
                .build();
    }
}
