package ru.itq.orderservice.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itq.orderservice.client.ProductFeignClient;
import ru.itq.orderservice.dto.ProductDto;
import ru.itq.orderservice.entity.Customer;
import ru.itq.orderservice.entity.Order;
import ru.itq.orderservice.entity.OrderItem;
import ru.itq.orderservice.entity.enums.OrderStatus;
import ru.itq.orderservice.exception.NotFoundException;
import ru.itq.orderservice.repository.CustomerRepository;
import ru.itq.orderservice.repository.OrderItemRepository;
import ru.itq.orderservice.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductFeignClient productFeignClient;
    private final CustomerRepository customerRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional
    public Order createOrder(Order order) {

        Customer customer = customerRepository.findById(order.getCustomer().getId())

                .orElseThrow(() -> {
                    log.error("Customer not found with id: {}", order.getCustomer().getId());
                    return new RuntimeException("Customer not found");
                });

        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);

        for (OrderItem item : order.getItems()) {
            ProductDto productDto = productFeignClient.getProductById(item.getProductId());

            item.setPrice(productDto.getPrice());
            item.setOrder(savedOrder);

            orderItemRepository.save(item);
        }

        return savedOrder;
    }

    @Transactional
    public Order updateOrder(Long orderId, Order orderDetails) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(orderDetails.getStatus());
                    order.setOrderDate(orderDetails.getOrderDate());
                    order.setCustomer(orderDetails.getCustomer());
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> {
                    log.error("Order not found with id: {}", orderId);
                    return new RuntimeException("Order not found");
                });

    }

    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}
