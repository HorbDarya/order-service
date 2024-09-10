package ru.itq.orderservice.service;

import ru.itq.orderservice.entity.Order;
import ru.itq.orderservice.entity.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getAllOrders();

    Optional<Order> getOrderById(Long orderId);

    Order createOrder(Order order);

    Order updateOrder(Long orderId, Order orderDetails);

    void deleteOrder(Long orderId);

    List<OrderItem> getOrderItemsByOrderId(Long orderId);
}
