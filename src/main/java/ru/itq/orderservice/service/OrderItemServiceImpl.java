package ru.itq.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itq.orderservice.dto.OrderItemDto;
import ru.itq.orderservice.entity.Order;
import ru.itq.orderservice.entity.OrderItem;
import ru.itq.orderservice.exception.NotFoundException;
import ru.itq.orderservice.mapper.OrderItemMapper;
import ru.itq.orderservice.repository.OrderItemRepository;
import ru.itq.orderservice.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemServiceImpl implements OrderItemService{

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Transactional
    public OrderItemDto createOrderItem(OrderItemDto orderDto) {

        if (!orderRepository.existsById(orderDto.getOrderId())) {
            log.error("Order not found with id: {}", orderDto.getOrderId());
            throw new NotFoundException("Order not found");
        }

        OrderItem orderItem = orderItemMapper.toEntity(orderDto);

        return getOrderItemDto(orderDto, orderItem);
    }

    @Transactional
    public void deleteOrderItem(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }

    @Transactional
    public OrderItemDto updateOrderItem(Long id, OrderItemDto orderItemDto) {

        if (!orderRepository.existsById(orderItemDto.getOrderId())) {
            log.error("Order not found with id: {}", orderItemDto.getOrderId());
            throw new NotFoundException("Order not found");
        }

        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order item not found with id: {}", id);
                    return new NotFoundException("OrderItem not found");
                });

        orderItem.setProductId(orderItemDto.getProductId());
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setPrice(orderItemDto.getPrice());


        return getOrderItemDto(orderItemDto, orderItem);
    }

    private OrderItemDto getOrderItemDto(OrderItemDto orderItemDto, OrderItem orderItem) {
        Order order = new Order();
        order.setId(orderItemDto.getOrderId());
        orderItem.setOrder(order);
        OrderItem savedItem = orderItemRepository.save(orderItem);
        OrderItemDto savedOrderItemDto = orderItemMapper.toDto(savedItem);
        savedOrderItemDto.setOrderId(savedItem.getOrder().getId());
        return savedOrderItemDto;
    }

    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> {
            log.error("Order item not found with id: {}", id);
            return new NotFoundException("OrderItem not found");
        });
    }


}
