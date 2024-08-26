package org.example.orderservice.model.response;

import lombok.Data;
import org.example.orderservice.model.domain.Order;
import org.example.orderservice.model.enums.OrderStatus;

@Data
public class OrderResponse {

    private Long id;
    private UserResponse userResponse;

    private String product;
    private Integer quantity;
    private Double price;
    private OrderStatus status;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.product = order.getProduct();
        this.quantity = order.getQuantity();
        this.price = order.getPrice();
        this.status = order.getStatus();
    }
}
