package com.orderService.orderManagement.model;

import com.orderService.orderManagement.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Getter
@Setter
public class OrderResponse {

    private Long id;
    private String customerName;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;

    // Constructor matching the call in the controller
    public OrderResponse(Long id, String customerName, OrderStatus status, BigDecimal totalAmount, LocalDateTime createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
    }
}
