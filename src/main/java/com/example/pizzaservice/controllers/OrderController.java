package com.example.pizzaservice.controllers;

import com.example.pizzaservice.model.Order;
import com.example.pizzaservice.service.OrderService;
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
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public List<Order> getList(){
        LOG.info("Getting orders list.");
        return orderService.getList();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable("id") Long id){
        LOG.info("Getting order details for id={}", id);
        return orderService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Order addOrdrer(@RequestBody Order order){
        LOG.info("Adding order wiyh id={} to database.", order.getId());
        return orderService.save(order);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteOrder(@PathVariable Long id){
        LOG.info("Attempting to remove order with id={} from database.", id);
        orderService.deleteOrder(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    public Order updateOrdrer(@RequestBody Order order){
        LOG.info("Attempting to update order with id={}.", order.getId());
        return orderService.updateOrder(order);
    }
}
