package com.clase11.products.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("products")
public class Product {

    @Id
    private String id;

    private String name;
    private Integer stock;
}
