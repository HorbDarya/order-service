package ru.itq.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itq.orderservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
