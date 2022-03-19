package com.example.pizzaservice.service;

import com.example.pizzaservice.db.SizeEntity;
import com.example.pizzaservice.exceptions.SizeException;
import com.example.pizzaservice.mappers.SizeMapper;
import com.example.pizzaservice.model.Size;
import com.example.pizzaservice.repository.SizeRepository;
import com.example.pizzaservice.validators.SizeValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SizeServiceTest {

    @Mock
    private SizeRepository sizeRepository;
    @Mock
    private SizeValidator sizeValidator;
    @Mock
    private SizeMapper sizeMapper;

    @InjectMocks
    private SizeService sizeService;

    @Test
    void shouldReturnListOfSizes(){
        List<Size> sizes = new ArrayList<>();
        sizes.add(new Size(1L, 10));
        sizes.add(new Size(2L, 12));
        sizes.add(new Size(3L, 15));

        when(sizeRepository.findAll()).thenReturn(Arrays.asList(
                SizeEntity.builder().id(1L).build(),
                SizeEntity.builder().id(2L).build(),
                SizeEntity.builder().id(3L).build()
        ));

        when(sizeMapper.mapEntityToSize(SizeEntity.builder().id(1L).build())).thenReturn(Optional.of(new Size(1L, 10)));
        when(sizeMapper.mapEntityToSize(SizeEntity.builder().id(2L).build())).thenReturn(Optional.of(new Size(2L, 12)));
        when(sizeMapper.mapEntityToSize(SizeEntity.builder().id(3L).build())).thenReturn(Optional.of(new Size(3L, 15)));

        List<Size> result = sizeService.getList();

        assertIterableEquals(sizes, result);
    }

    @Test
    void shouldReturnEmptyListWhenNoSizesInDB(){
        when(sizeRepository.findAll()).thenReturn(new ArrayList<>());

        List<Size> result = sizeService.getList();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnSizeWithGizenId(){
        Size size = new Size(3L, 20);
        Optional<SizeEntity> sizeEntityOptional = Optional.of(new SizeEntity(3L, 20));

        when(sizeRepository.findById(3L)).thenReturn(Optional.of(new SizeEntity(3L, 20)));
        when(sizeMapper.mapEntityToSize(sizeEntityOptional.get())).thenReturn(Optional.of(size));
        Size result = sizeService.getSizeById(3L);

        assertEquals(size, result);
    }

    @Test
    void shouldReturnEmptyOptionalIfSizeWithGivenIdNotPresentInDB(){
        when(sizeRepository.findById(2L)).thenReturn(Optional.empty());

        Size result = sizeService.getSizeById(2L);

        assertEquals(new Size(), result);
    }

    @Test
    void shouldReturnSavedSize(){
        Size size = new Size(1L, 13);
        SizeEntity created = new SizeEntity(1L, 13);

        when(sizeValidator.isValid(size)).thenReturn("");
        when(sizeRepository.save(sizeMapper.mapSizeToEntity(size))).thenReturn(created);
        when(sizeMapper.mapEntityToSize(created)).thenReturn(Optional.of(size));

        Size result = sizeService.save(size);

        assertEquals(size, result);
    }

    @Test
    void shouldThrowSizeExceptionWhenSizeNotValid(){
        Size size = new Size();

        when(sizeValidator.isValid(size)).thenReturn("Pizza needs to have a size.");

        SizeException exception = assertThrows(SizeException.class, () -> sizeService.save(size));
        assertEquals("Pizza needs to have a size.", exception.getMessage());
    }
}