package org.example.orderservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.orderservice.model.enums.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListingResponse {

    private Long id;
    private String product;
}
