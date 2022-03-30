package com.example.pizzaservice.mappers;

import com.example.pizzaservice.db.DoughEntity;
import com.example.pizzaservice.model.Dough;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DoughMapperTest {

    private final DoughMapper doughMapper = new DoughMapper();

    @Test
    void shouldMapEntityToDough() {
        DoughEntity entity = new DoughEntity(1L, "thin", 2.2);
        Dough dough = new Dough(1L, "thin", 2.2);

        Optional<Dough> result = doughMapper.mapEntityToDough(entity);

        assertTrue(result.isPresent());
        assertEquals(dough, result.get());
    }

    @Test
    void shouldReturnEmptyOptionalIfNullEntity() {

        Optional<Dough> result = doughMapper.mapEntityToDough(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldMapDoughToEntity() {

        Dough dough = new Dough(2L, "thin", 2.2);
        DoughEntity doughEntity = new DoughEntity(2L, "thin", 2.2);

        DoughEntity result = doughMapper.mapDoughToEntity(dough);

        assertEquals(doughEntity, result);
    }


}