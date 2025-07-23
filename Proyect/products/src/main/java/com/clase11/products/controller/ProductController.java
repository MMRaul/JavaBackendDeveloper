package com.clase11.products.controller;

import com.clase11.products.dto.saveNewProductDto;
import com.clase11.products.dto.updateProductDto;
import com.clase11.products.model.Product;
import com.clase11.products.service.ProdutcService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProdutcService produtcService;

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody saveNewProductDto dto){
        Product response = produtcService.saveNewProduct(dto.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable("name") String name){
        Product response = produtcService.getProductByName(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Product> updateProduct(@RequestBody updateProductDto dto){
        Product response = produtcService.updateStock(dto.getId(), dto.getQuantity());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/replenishProduct")
    public ResponseEntity<Product> ReplenishProduct(@RequestBody updateProductDto dto){
        Product response = produtcService.replenishStock(dto.getId(), dto.getQuantity());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/productCatalog")
    public ResponseEntity<List<Product>> getAllProduct(){
        List<Product> response = produtcService.getAllProduct();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
