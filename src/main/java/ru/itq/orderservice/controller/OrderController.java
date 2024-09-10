package ru.itq.orderservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itq.orderservice.dto.OrderDto;
import ru.itq.orderservice.dto.OrderItemDto;
import ru.itq.orderservice.entity.Order;
import ru.itq.orderservice.entity.OrderItem;
import ru.itq.orderservice.mapper.OrderItemMapper;
import ru.itq.orderservice.mapper.OrderMapper;
import ru.itq.orderservice.service.OrderHistoryService;
import ru.itq.orderservice.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderHistoryService orderHistoryService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders().stream()
                .map(orderMapper::toDto)
                .toList();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId)
                .map(order -> ResponseEntity.ok(orderMapper.toDto(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(orderMapper.toDto(createdOrder));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody OrderDto orderDto) {
        Order updatedOrder = orderService.updateOrder(orderId, orderMapper.toEntity(orderDto));
        return ResponseEntity.ok(orderMapper.toDto(updatedOrder));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItemDto>> getOrderItems(@PathVariable Long orderId) {
        List<OrderItem> orderItems = orderService.getOrderItemsByOrderId(orderId);
        List<OrderItemDto> orderItemDtos = orderItems.stream()
                .map(orderItemMapper::toDto)
                .toList();
        return ResponseEntity.ok(orderItemDtos);
    }

    @GetMapping("/{orderId}/history")
    public ResponseEntity<List<Object[]>> getOrderHistory(@PathVariable Long orderId) {
        List<Object[]> history = orderHistoryService.getOrderRevisions(orderId);
        return ResponseEntity.ok(history);
    }
}
