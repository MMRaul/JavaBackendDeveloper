package com.clase11.products.service;

import com.clase11.products.model.Product;
import com.clase11.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutcService {

    private final ProductRepository productRepository;

    public Product saveNewProduct(String name){
        Product product = new Product();
        product.setName(name);
        product.setStock(100);

        return productRepository.save(product);
    }

    public Product getProductByName(String name){
        return productRepository.findByName(name);
    }

    public Product updateStock(String id, Integer quantitySold){
        Product product = productRepository.findById(id).get();
        product.setStock(product.getStock() - quantitySold);

        return productRepository.save(product);
    }

    public Product replenishStock(String id, Integer quantityStock){
        Product product = productRepository.findById(id).get();
        product.setStock(product.getStock() + quantityStock);

        return productRepository.save(product);
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
}
