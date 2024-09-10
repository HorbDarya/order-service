package ru.itq.orderservice.mapper;

import org.mapstruct.Mapper;
import ru.itq.orderservice.dto.OrderItemDto;
import ru.itq.orderservice.entity.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItem orderItem);
    OrderItem toEntity(OrderItemDto orderItemDto);
}
