package com.clase11.orders.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "quantity_sold")
    private Integer quantitySold;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
}
