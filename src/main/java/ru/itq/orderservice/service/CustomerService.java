package ru.itq.orderservice.service;

import ru.itq.orderservice.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long customerId);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long customerId, Customer customerDetails);
    void deleteCustomer(Long customerId);
}
