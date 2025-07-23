package com.clase11.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateNewOrderDto {

    @JsonProperty("client_email")
    private String clientEmail;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("quantity_sold")
    private String quantitySold;

}
