package ru.itq.orderservice.mapper;

import org.mapstruct.Mapper;
import ru.itq.orderservice.dto.OrderDto;
import ru.itq.orderservice.entity.Order;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    OrderDto toDto(Order order);
    Order toEntity(OrderDto orderDto);
}
