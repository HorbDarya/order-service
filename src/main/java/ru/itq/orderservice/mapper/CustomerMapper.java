package ru.itq.orderservice.mapper;

import org.mapstruct.Mapper;
import ru.itq.orderservice.dto.CustomerDto;
import ru.itq.orderservice.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDto toDto(Customer customer);
    Customer toEntity(CustomerDto customerDto);
}
