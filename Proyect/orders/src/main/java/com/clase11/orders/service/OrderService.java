package com.clase11.orders.service;

import com.clase11.orders.dto.GenerateNewOrderDto;
import com.clase11.orders.repository.OrderRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.client.RestTemplate;

@Setter
@Getter
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public Order createNewOrder(GenerateNewOrderDto dto){
        String url = "localhost:8081/api/v1/client/"  dto.get;

    }
}
