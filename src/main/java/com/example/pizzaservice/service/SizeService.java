package com.example.pizzaservice.service;

import com.example.pizzaservice.db.SizeEntity;
import com.example.pizzaservice.exceptions.SizeException;
import com.example.pizzaservice.mappers.SizeMapper;
import com.example.pizzaservice.model.Size;
import com.example.pizzaservice.repository.SizeRepository;
import com.example.pizzaservice.validators.SizeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SizeService {

    private final SizeRepository sizeRepository;
    private final SizeMapper sizeMapper;
    private final SizeValidator sizeValidator;
    private static final Logger LOG = LoggerFactory.getLogger(SizeService.class);
    private static final String SIZE_NOT_FOUND = "Size with id={} not found.";

    public SizeService(SizeRepository sizeRepository, SizeMapper sizeMapper, SizeValidator sizeValidator) {
        this.sizeRepository = sizeRepository;
        this.sizeMapper = sizeMapper;
        this.sizeValidator = sizeValidator;
    }

    public List<Size> getList() {
        List<Size> sizes = new ArrayList<>();
        sizeRepository.findAll().forEach(entity -> sizes.add(sizeMapper.mapEntityToSize(entity).orElseGet(Size::new)));
        return sizes;
    }

    public Size getSizeById(long id) {
        Optional<SizeEntity> sizeEntityOptional = sizeRepository.findById(id);

        if(sizeEntityOptional.isEmpty()){
            LOG.info(SIZE_NOT_FOUND, id);
            return new Size();
        }

        LOG.info("Size with id={} found.", id);
        return sizeMapper.mapEntityToSize(sizeEntityOptional.get()).orElseGet(Size::new);
    }

    public Size save(Size size) {
        String error = sizeValidator.isValid(size);
        if(!error.isEmpty()){
            LOG.info("Size not valid.");
            throw new SizeException(error);
        }

        SizeEntity created = sizeRepository.save(sizeMapper.mapSizeToEntity(size));
        LOG.info("Succesfully saved size {} to database.", size.getDiameter());
        return sizeMapper.mapEntityToSize(created).get();
    }

    public void deleteSize(Long id){
        Optional<SizeEntity> sizeEntityOptional = sizeRepository.findById(id);
        sizeEntityOptional.ifPresentOrElse(sizeEntity -> {
            sizeRepository.delete(sizeEntity);
            LOG.info("Size with id={} removed from database.", id);
        }, () -> LOG.info(SIZE_NOT_FOUND, id));
    }

    public Size updateSize(Size size){
        if(size.getId() != null && sizeRepository.findById(size.getId()).isPresent()){
            return save(size);
        }

        LOG.info(SIZE_NOT_FOUND, size.getId());
        return null;
    }
}
