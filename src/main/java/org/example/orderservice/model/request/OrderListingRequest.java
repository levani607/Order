package org.example.orderservice.model.request;

import lombok.Data;
import org.example.orderservice.model.enums.OrderSort;
import org.springframework.data.domain.Sort;

@Data
public class OrderListingRequest {

    private String productPrompt;
    private Integer page;
    private Integer size;
    private Sort.Direction direction;
    private OrderSort sort;
}
