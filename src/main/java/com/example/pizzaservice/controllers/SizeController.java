package com.example.pizzaservice.controllers;

import com.example.pizzaservice.model.Size;
import com.example.pizzaservice.service.SizeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sizes")
public class SizeController {

    private final SizeService sizeService;
    private static final Logger LOG = LoggerFactory.getLogger(SizeController.class);

    public SizeController(SizeService sizeService) {
        this.sizeService = sizeService;
    }

    @GetMapping("/list")
    public List<Size> getSizesList(){
        LOG.info("Getting sizes list.");
        return sizeService.getList();
    }

    @GetMapping("/{id}")
    public Size getSize(@PathVariable("id") Long id){
        LOG.info("Getting size details for id={}.", id);
        return sizeService.getSizeById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Size addSize(@RequestBody Size size){
        LOG.info("Adding size {} to database", size.getDiameter());
        return sizeService.save(size);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteSize(@PathVariable("id") Long id){
        LOG.info("Attempting to delete size with id={} from database.", id);
        sizeService.deleteSize(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    public Size updateSize(@RequestBody Size size){
        LOG.info("Attempting to update size with id {}", size.getId());
        return sizeService.updateSize(size);
    }

}
