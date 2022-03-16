package com.example.pizzaservice.controllers;

import com.example.pizzaservice.model.Dough;
import com.example.pizzaservice.service.DoughService;
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
@RequestMapping("api/doughs")
public class DoughController {

    private final DoughService doughService;
    private static final Logger LOG = LoggerFactory.getLogger(DoughController.class);

    public DoughController(DoughService doughService) {
        this.doughService = doughService;
    }

    @GetMapping("/list")
    public List<Dough> getDoughList(){
        LOG.info("Getting doughs list.");
        return doughService.getList();
    }

    @GetMapping("/{id}")
    public Dough getDough(@PathVariable Long id){
        LOG.info("Getting dough details for id = {}", id);
        return doughService.getDoughById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Dough addDough(@RequestBody Dough dough){
        LOG.info("Adding {} to database", dough.getName());
        return doughService.save(dough);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteDough(@PathVariable Long id){
        LOG.info("Attempting to delete dough with id={} from database.", id);
        doughService.deleteDough(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    public Dough updateDough(@RequestBody Dough dough){
        LOG.info("Attempting to update dough with id={}.", dough.getId());
        return doughService.update(dough);
    }
}
