package com.clase11.orders.service;

import com.clase11.orders.dto.*;
import com.clase11.orders.model.Order;
import com.clase11.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public Order createNewOrder(GenerateNewOrderDto dto){

        ResponseEntity<ClientResponseDto> responseEntityClientDto = responseEntityClient(dto.getClientEmail());

        ResponseEntity<ProductResponseDto> responseEntityProductDto = responseEntityProduct(dto.getProductName());

        if(responseEntityClientDto.getStatusCode().is2xxSuccessful() &&
                responseEntityProductDto.getStatusCode().is2xxSuccessful()){

            ClientResponseDto bodyResponse = responseEntityClientDto.getBody();
            ProductResponseDto bodyResponseProduct = responseEntityProductDto.getBody();

            Order order = new Order();
            order.setClientId(bodyResponse.getId());
            order.setProductId(bodyResponseProduct.getId());
            order.setQuantitySold(Integer.parseInt(dto.getQuantitySold()));

            UpdateProduct(order.getProductId(), order.getQuantitySold());

            return orderRepository.save(order);

        }

        throw new RuntimeException("ERROR");

    }

    public Order createNewOrderCreatingNewClient(CreateNewOrderCreatingNewClientDto dto){

        ResponseEntity<ClientResponseDto> responseEntityDto = responseEntityNewClient(dto);
        ResponseEntity<ProductResponseDto> responseEntityProductDto = responseEntityProduct(dto.getProductName());

        ClientResponseDto bodyResponse = responseEntityDto.getBody();
        ProductResponseDto bodyResponseProduct = responseEntityProductDto.getBody();

        Order order = new Order();
        order.setClientId(bodyResponse.getId());
        order.setProductId(bodyResponseProduct.getId());
        order.setQuantitySold(Integer.parseInt(dto.getQuantitySold()));

        UpdateProduct(order.getProductId(),order.getQuantitySold());

        return orderRepository.save(order);
    }

    private ResponseEntity<ClientResponseDto> responseEntityClient(String email){
        String urlApiClient = "http://localhost:8081/api/v1/client/" + email;

        return restTemplate.exchange(urlApiClient, HttpMethod.GET, null, ClientResponseDto.class);
    }

    private ResponseEntity<ClientResponseDto> responseEntityNewClient(CreateNewOrderCreatingNewClientDto dto){
        String urlApiClient = "http://localhost:8081/api/v1/client";

        CreateNewClientDto newClientDto = new CreateNewClientDto();
        newClientDto.setEmail(dto.getEmail());
        newClientDto.setName(dto.getName());

        HttpEntity<CreateNewClientDto> request = new HttpEntity<>(newClientDto);

        return restTemplate.exchange(urlApiClient, HttpMethod.POST, request, ClientResponseDto.class);
    }

    private ResponseEntity<ProductResponseDto> responseEntityProduct(String name){
        String urlApiPorduct = "http://localhost:8082/api/v1/product/" + name;

        return restTemplate.exchange(urlApiPorduct, HttpMethod.GET, null, ProductResponseDto.class);
    }

    private void UpdateProduct(String id, Integer quantitySold){
        String urlApiProduct = "http://localhost:8082/api/v1/product/updateProduct";

        UpdateProductDto dto = new UpdateProductDto();
        dto.setId(id);
        dto.setQuantity(quantitySold);

        HttpEntity<UpdateProductDto> request = new HttpEntity<>(dto);

        restTemplate.exchange(urlApiProduct, HttpMethod.PUT, request, UpdateProductDto.class);
    }

}
