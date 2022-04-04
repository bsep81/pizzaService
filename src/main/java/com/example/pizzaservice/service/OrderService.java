package com.example.pizzaservice.service;

import com.example.pizzaservice.db.OrderEntity;
import com.example.pizzaservice.exceptions.OrderException;
import com.example.pizzaservice.mappers.OrderMapper;
import com.example.pizzaservice.model.Order;
import com.example.pizzaservice.repository.OrderRepository;
import com.example.pizzaservice.validators.OrderValidator;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderValidator orderValidator;

    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);
    private static final String ORDER_NOT_FOUND = "Order with id={} not found.";


    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, OrderValidator orderValidator) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderValidator = orderValidator;
    }


    public List<Order> getList(){
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orderEntity -> orders.add(orderMapper.mapEntityToOrder(orderEntity).orElseGet(Order::new)));
        return orders;
    }

    public Order findById(Long id){
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);

        if(orderEntityOptional.isEmpty()){
            LOG.info(ORDER_NOT_FOUND, id);
            return new Order();
        }

        LOG.info("Order with id={} found.", id);
        return orderMapper.mapEntityToOrder(orderEntityOptional.get()).orElseGet(Order::new);
    }

    public Order save(Order order){
        List<String> errors = orderValidator.isValid(order);

        if(!errors.isEmpty()){
            LOG.info("Order not valid.");
            throw new OrderException(Strings.join(errors, ' '));
        }

        OrderEntity created = orderRepository.save(orderMapper.mapOrderToEntity(order));
        LOG.info("Successfully saved order with id={} to database.", order.getId());
        return orderMapper.mapEntityToOrder(created).get();
    }

    public void deleteOrder(Long id){
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);

        orderEntityOptional.ifPresentOrElse(orderEntity -> {
            orderRepository.delete(orderEntity);
            LOG.info("Order with id={} removed from database.", id);
        }, () -> LOG.info(ORDER_NOT_FOUND, id));
    }

    public Order updateOrder(Order order){
        if(order.getId() != null && orderRepository.findById(order.getId()).isPresent()){
            return save(order);
        }

        LOG.info(ORDER_NOT_FOUND, order.getId());
        return null;
    }
}
