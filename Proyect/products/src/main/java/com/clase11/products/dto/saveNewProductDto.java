package com.clase11.products.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class saveNewProductDto {

    private String name;
    private Integer stock;

    public saveNewProductDto() {
        this.stock = 100;
    }
}
