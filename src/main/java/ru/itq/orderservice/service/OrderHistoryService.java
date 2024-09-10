package ru.itq.orderservice.service;

import java.util.List;

public interface OrderHistoryService {
    List<Object[]> getOrderRevisions(Long orderId);
}
