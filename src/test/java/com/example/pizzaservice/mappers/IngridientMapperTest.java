package com.example.pizzaservice.mappers;

import com.example.pizzaservice.db.IngridientEntity;
import com.example.pizzaservice.model.Ingridient;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class IngridientMapperTest {

    private final IngridientMapper ingridientMapper = new IngridientMapper();

    @Test
    void shouldMapEntityToIngridient(){
        IngridientEntity entity = new IngridientEntity(1L, "tomato", 0.59);
        Ingridient ingridient = new Ingridient(1L, "tomato", 0.59);

        Optional<Ingridient> result = ingridientMapper.mapEntityToIngridient(entity);

        assertTrue(result.isPresent());
        assertEquals(ingridient, result.get());
    }

    @Test
    void shouldReturnEmptyOptionalIfNullEntity(){

        Optional<Ingridient> result = ingridientMapper.mapEntityToIngridient(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldMapIngridientToEntity(){
        Ingridient ingridient = new Ingridient(2L, "mushroom", 0.45);
        IngridientEntity ingridientEntity = new IngridientEntity(2L, "mushroom", 0.45);

        IngridientEntity result = ingridientMapper.mapIngridientToEntity(ingridient);

        assertEquals(ingridientEntity, result);
    }



}