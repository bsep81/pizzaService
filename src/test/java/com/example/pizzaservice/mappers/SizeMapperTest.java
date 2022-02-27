package com.example.pizzaservice.mappers;

import com.example.pizzaservice.db.SizeEntity;
import com.example.pizzaservice.model.Size;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SizeMapperTest {

    SizeMapper sizeMapper = new SizeMapper();

    @Test
    void shouldMapEntityToSize(){

        SizeEntity entity = new SizeEntity(1L, 10);
        Size size = new Size(1L, 10);

        Optional<Size> result = sizeMapper.mapEntityToSize(entity);

        assertTrue(result.isPresent());
        assertEquals(size, result.get());
    }

    @Test
    void shouldReturnEmptyOptionalIfNullEntity(){

        Optional<Size> result = sizeMapper.mapEntityToSize(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void ShouldMapSizeToEbtity(){

        Size size = new Size(2L, 15);
        SizeEntity entity = new SizeEntity(2L, 15);

        SizeEntity result = sizeMapper.mapSizeToEntity(size);

        assertEquals(entity, result);
    }

}