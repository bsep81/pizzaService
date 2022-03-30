package com.example.pizzaservice.service;

import com.example.pizzaservice.db.DoughEntity;
import com.example.pizzaservice.exceptions.DoughException;
import com.example.pizzaservice.mappers.DoughMapper;
import com.example.pizzaservice.model.Dough;
import com.example.pizzaservice.repository.DoughRepository;
import com.example.pizzaservice.validators.DoughValidator;
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
class DoughServiceTest {

    @Mock
    private DoughRepository doughRepository;
    @Mock
    private DoughValidator doughValidator;
    @Mock
    private DoughMapper doughMapper;

    @InjectMocks
    private DoughService doughService;

    @Test
    void shouldReturnListOfDoughs() {
        List<Dough> doughs = new ArrayList<>();
        doughs.add(new Dough(1L, "thin", 2.2));
        doughs.add(new Dough(2L, "medium", 2.2));
        doughs.add(new Dough(3L, "thick", 2.2));

        when(doughRepository.findAll()).thenReturn(Arrays.asList(
                DoughEntity.builder().id(1L).build(),
                DoughEntity.builder().id(2L).build(),
                DoughEntity.builder().id(3L).build()
        ));

        when(doughMapper.mapEntityToDough(DoughEntity.builder().id(1L).build())).thenReturn(Optional.of(new Dough(1L, "thin", 2.2)));
        when(doughMapper.mapEntityToDough(DoughEntity.builder().id(2L).build())).thenReturn(Optional.of(new Dough(2L, "medium", 2.2)));
        when(doughMapper.mapEntityToDough(DoughEntity.builder().id(3L).build())).thenReturn(Optional.of(new Dough(3L, "thick", 2.2)));

        List<Dough> result = doughService.getList();

        assertIterableEquals(doughs, result);
    }

    @Test
    void shouldReturnEmptyListIfNoDoughsInDb() {
        when(doughRepository.findAll()).thenReturn(new ArrayList<>());

        List<Dough> result = doughService.getList();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnDoughWithGivenId() {
        Dough dough = new Dough(1L, "thin", 2.2);
        Optional<DoughEntity> doughEntityOptional = Optional.of(new DoughEntity(1L, "thin", 2.2));

        when(doughRepository.findById(1L)).thenReturn(Optional.of(new DoughEntity(1L, "thin", 2.2)));
        when(doughMapper.mapEntityToDough(doughEntityOptional.get())).thenReturn(Optional.of(dough));

        Dough result = doughService.getDoughById(1L);

        assertEquals(dough, result);
    }

    @Test
    void shouldReturnEmptyOptionalIfDoughWithGivenIdNotPresentInDB(){
        when(doughRepository.findById(1L)).thenReturn(Optional.empty());

        Dough result = doughService.getDoughById(1L);

        assertEquals(new Dough(), result);
    }

    @Test
    void shouldReturnSavedDough(){
        Dough dough = new Dough(1L, "thin", 2.2);
        DoughEntity created = new DoughEntity(1L, "thin", 2.2);

        when(doughValidator.isValid(dough)).thenReturn(new ArrayList<>());
        when(doughRepository.save(doughMapper.mapDoughToEntity(dough))).thenReturn(created);
        when(doughMapper.mapEntityToDough(created)).thenReturn(Optional.of(dough));

        Dough result = doughService.save(dough);

        assertEquals(dough, result);
    }

    @Test
    void shouldThrowDoughExceptionWhenDoughNotValid(){
        Dough dough = new Dough();

        when(doughValidator.isValid(dough)).thenReturn(List.of("Dough should have a name."));

        DoughException exception = assertThrows(DoughException.class, () -> doughService.save(dough));
        assertEquals("Dough should have a name.", exception.getMessage());
    }


}