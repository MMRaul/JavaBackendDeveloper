package com.enyoi.products.controller;

import com.enyoi.products.entity.Product;
import com.enyoi.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Mono<Product> saveProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @PostMapping(value = "/get", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getProductByIds(@RequestBody List<String> ids){
        return productService.getProductById(Flux.fromIterable(ids));
    }
}
