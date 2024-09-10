package ru.itq.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itq.orderservice.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
