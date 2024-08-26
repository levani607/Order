package org.example.orderservice.model.request;

import lombok.Data;

@Data
public class OrderRequest {

    private Long userId;
    private String product;
    private Integer quantity;
    private Double price;
}
