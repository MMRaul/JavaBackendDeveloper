package com.clase11.orders.controller;

import com.clase11.orders.dto.CreateNewOrderCreatingNewClientDto;
import com.clase11.orders.dto.GenerateNewOrderDto;
import com.clase11.orders.model.Order;
import com.clase11.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createNewOrder(@RequestBody GenerateNewOrderDto dto){
        Order order = orderService.createNewOrder(dto);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/client")
    public ResponseEntity<Order> createNewOrderNewClient(@RequestBody CreateNewOrderCreatingNewClientDto dto){
        Order order = orderService.createNewOrderCreatingNewClient(dto);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}