package ru.itq.orderservice.service;

import ru.itq.orderservice.dto.OrderItemDto;
import ru.itq.orderservice.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> getAllOrderItems();

    OrderItemDto createOrderItem(OrderItemDto orderItem);

    void deleteOrderItem(Long orderItemId);

    OrderItemDto updateOrderItem(Long id, OrderItemDto orderItem);

    OrderItem getOrderItemById(Long id);
}
