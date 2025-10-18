package com.arka.products.service;

import com.arka.products.models.Products;
import com.arka.products.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    public Flux<Products> getAllProducts(){
        return productsRepository.findAll()
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay productos que mostrar")
                ));
    }

    public Mono<Products> getByIdProduct(Long id){
        return productsRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado")
                ));
    }

    public Mono<Products> saveProduct(Products product){

        if (product.getEstado() == null) {
            product.setEstado(true);
        }
        if (product.getNombre() == null || product.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo nombre es obligatorio");
        }
        if (product.getDescripcion() == null || product.getDescripcion().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo descripcion es obligatorio");
        }

        return productsRepository.save(product);
    }

    public Mono<Products> updateProduct(Long id, Products productUpdate){
        return productsRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no entontrado con ese ID: "+id)))
                .flatMap(products -> {
                    products.setNombre(productUpdate.getNombre());
                    products.setDescripcion(productUpdate.getDescripcion());
                    products.setPrecio(productUpdate.getPrecio());
                    products.setStockActual(productUpdate.getStockActual());
                    products.setCategoria(productUpdate.getCategoria());
                    products.setEstado(productUpdate.getEstado());
                    return productsRepository.save(products);
                });
    }
}
