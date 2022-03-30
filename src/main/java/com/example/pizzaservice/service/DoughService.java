package com.example.pizzaservice.service;

import com.example.pizzaservice.db.DoughEntity;
import com.example.pizzaservice.exceptions.DoughException;
import com.example.pizzaservice.mappers.DoughMapper;
import com.example.pizzaservice.model.Dough;
import com.example.pizzaservice.repository.DoughRepository;
import com.example.pizzaservice.validators.DoughValidator;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoughService {

    private final DoughRepository doughRepository;
    private final DoughMapper doughMapper;
    private final DoughValidator doughValidator;
    private static final Logger LOG = LoggerFactory.getLogger(DoughService.class);
    private static final String DOUGH_NOT_FOUND = "Dough with id={} not found.";

    public DoughService(DoughRepository doughRepository, DoughMapper doughMapper, DoughValidator doughValidator) {
        this.doughRepository = doughRepository;
        this.doughMapper = doughMapper;
        this.doughValidator = doughValidator;
    }


    public List<Dough> getList() {
        List<Dough> doughs = new ArrayList<>();
        doughRepository.findAll().forEach(entity -> doughs.add(doughMapper.mapEntityToDough(entity).orElseGet(Dough::new)));
        return doughs;
    }

    public Dough getDoughById(long id) {
        Optional<DoughEntity> doughEntityOptional = doughRepository.findById(id);

        if(doughEntityOptional.isEmpty()){
            LOG.info(DOUGH_NOT_FOUND, id);
            return new Dough();
        }

        LOG.info("Dough with id={} found.", id);
        return doughMapper.mapEntityToDough(doughEntityOptional.get()).orElseGet(Dough::new);
    }


    public Dough save(Dough dough) {
        List<String> errors = doughValidator.isValid(dough);
        if(!errors.isEmpty()){
            LOG.info("Dough not valid.");
            throw new DoughException(Strings.join(errors, ' '));
        }

        DoughEntity created = doughRepository.save(doughMapper.mapDoughToEntity(dough));
        LOG.info("Succesfully saved dough {} to database.", dough.getName());
        return doughMapper.mapEntityToDough(created).get();
    }

    public void deleteDough(Long id){
        Optional<DoughEntity> doughEntityOptional = doughRepository.findById(id);
        doughEntityOptional.ifPresentOrElse(doughEntity -> {
            doughRepository.delete(doughEntity);
            LOG.info("Dough with id={} removed from database.", id);
        }, () -> LOG.info(DOUGH_NOT_FOUND, id));
    }

    public Dough update(Dough dough){
        if(dough.getId() != null && doughRepository.findById(dough.getId()).isPresent()){
            return save(dough);
        }

        LOG.info(DOUGH_NOT_FOUND, dough.getId());
        return null;
    }
}
