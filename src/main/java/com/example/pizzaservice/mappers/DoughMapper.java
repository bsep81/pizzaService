package com.example.pizzaservice.mappers;

import com.example.pizzaservice.db.DoughEntity;
import com.example.pizzaservice.model.Dough;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DoughMapper {

    public Optional<Dough> mapEntityToDough(DoughEntity entity) {

        if(entity == null){
            return Optional.empty();
        }

        Dough dough = Dough.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();

        return Optional.of(dough);

    }

    public DoughEntity mapDoughToEntity(Dough dough) {
        return DoughEntity.builder()
                .id(dough.getId())
                .name(dough.getName())
                .build();
    }
}
