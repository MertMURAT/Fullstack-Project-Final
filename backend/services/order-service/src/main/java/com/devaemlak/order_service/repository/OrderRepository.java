package com.devaemlak.order_service.repository;

import com.devaemlak.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findTopByOrderByIdDesc();
    void deleteByCreatedDateBefore(LocalDateTime dateTime);
}
