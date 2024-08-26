package org.example.orderservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.model.request.OrderRequest;
import org.example.orderservice.service.OrderService;
import org.example.orderservice.model.response.OrderListingResponse;
import org.example.orderservice.model.response.OrderResponse;
import org.example.orderservice.model.request.OrderListingRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @PostMapping
    @Operation(
            security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<Long> create(@RequestBody @Valid OrderRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(
            security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<Long> update(@PathVariable("id") Long id, @Valid @RequestBody OrderRequest request){
        return ResponseEntity.ok(orderService.update(id,request));
    }

    @GetMapping
    @Operation(
            security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<PagedModel<OrderListingResponse>> list(OrderListingRequest request){
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(orderService.list(request));
    }
    @GetMapping("/{id}")
    @Operation(
            security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<OrderResponse> get(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.get(id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            security = @SecurityRequirement(name = "bearer-token"))
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
