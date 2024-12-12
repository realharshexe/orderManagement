package com.orderService.orderManagement.enums;

public enum OrderStatus {

    PENDING, // Order has been created but not yet processed.
    FAILED,
    CONFIRMED,  // Order has been confirmed.
    SHIPPED,    // Order has been shipped to the customer.
    DELIVERED,  // Order has been delivered successfully.
    CANCELLED   // Order has been cancelled by the user or system.
}
