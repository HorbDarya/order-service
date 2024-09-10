package ru.itq.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itq.orderservice.entity.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);
}
