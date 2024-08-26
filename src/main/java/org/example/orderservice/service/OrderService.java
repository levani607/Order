package org.example.orderservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.exceptions.CustomException;
import org.example.orderservice.model.domain.Order;
import org.example.orderservice.model.enums.OrderStatus;
import org.example.orderservice.model.request.OrderListingRequest;
import org.example.orderservice.model.request.OrderRequest;
import org.example.orderservice.model.response.OrderListingResponse;
import org.example.orderservice.model.response.OrderResponse;
import org.example.orderservice.model.response.UserResponse;
import org.example.orderservice.model.response.UserServiceUserResponse;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {


    private final OrderRepository orderRepository;

    private final UserService userService;
    @Transactional
    public Long create(OrderRequest request) {
        Order order = new Order();
        userService.getUser(request.getUserId());
        mapRequestToOrder(request, order);
        return orderRepository.save(order).getId();
    }


    @Transactional
    public Long update(Long id, OrderRequest request) {
        Order order = orderRepository.findByIdAndStatus(id, OrderStatus.ACTIVE)
                .orElseThrow(() -> new CustomException("Order with id %s was not found!;".formatted(id), HttpStatus.NOT_FOUND));
        userService.getUser(request.getUserId());
        mapRequestToOrder(request, order);
        return orderRepository.save(order).getId();
    }

    @Transactional
    public void delete(Long id) {
        Order order = orderRepository.findByIdAndStatus(id, OrderStatus.ACTIVE)
                .orElseThrow(() -> new CustomException("Order with id %s was not found!;".formatted(id), HttpStatus.NOT_FOUND));
        order.setStatus(OrderStatus.DELETED);
        orderRepository.save(order);
    }

    public OrderResponse get(Long id) {
        Order order = orderRepository.findByIdAndStatus(id, OrderStatus.ACTIVE)
                .orElseThrow(() -> new CustomException("Order with id %s was not found!;".formatted(id), HttpStatus.NOT_FOUND));
        OrderResponse orderResponse = new OrderResponse(order);
        orderResponse.setUserResponse(new UserResponse(userService.getUser(order.getUserId())));
        return orderResponse;
    }

    public PagedModel<OrderListingResponse> list(OrderListingRequest request) {
        return new PagedModel<>(
                orderRepository
                        .list(
                                resolvePrompt(request.getProductPrompt()),
                                PageRequest.of(request.getPage(),request.getSize(), Sort.by(request.getDirection(),request.getSort().getValue()))
                        )
        );
    }

    private String resolvePrompt(String usernamePrompt) {
        if(StringUtils.hasText(usernamePrompt)) {
            return "%"+usernamePrompt+"%";
        }

        return usernamePrompt;
    }

    private void mapRequestToOrder(OrderRequest request, Order order) {
        order.setUserId(request.getUserId());
        order.setProduct(request.getProduct());
        order.setPrice(request.getPrice());
        order.setQuantity(request.getQuantity());
        order.setStatus(OrderStatus.ACTIVE);
    }


}
