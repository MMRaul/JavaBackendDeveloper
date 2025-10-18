package com.arka.products.controller;

import com.arka.products.models.Products;
import com.arka.products.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Products> createProduct(@RequestBody Products product){
        return productsService.saveProduct(product);
    }

    @GetMapping
    public Flux<Products> getAllProduct(){
        return productsService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Mono<Products> getByIdProduct(@PathVariable Long id){
        return productsService.getByIdProduct(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Products> updateProduct(@PathVariable Long id, @RequestBody Products products){
        return productsService.updateProduct(id,products);
    }
}
