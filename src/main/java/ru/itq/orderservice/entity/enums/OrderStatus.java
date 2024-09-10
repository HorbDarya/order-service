package ru.itq.orderservice.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    PENDING("Pending", "Order has been placed but not yet processed"),
    PROCESSING("Processing", "Order is currently being processed"),
    COMPLETED("Completed", "Order has been completed and shipped"),
    CANCELLED("Cancelled", "Order has been cancelled");

    private final String displayName;
    private final String description;
}
