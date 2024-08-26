package org.example.orderservice.repository;

import org.example.orderservice.model.domain.Order;
import org.example.orderservice.model.enums.OrderStatus;
import org.example.orderservice.model.response.OrderListingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdAndStatus(Long id, OrderStatus orderStatus);

    @Query("""
            select new org.example.orderservice.model.response.OrderListingResponse(o.id,o.product)
            from Order o
            where (:prompt is null  or o.product like :prompt)
            and o.status='ACTIVE'
            """)
    Page<OrderListingResponse> list(String prompt, Pageable pageable);
}
