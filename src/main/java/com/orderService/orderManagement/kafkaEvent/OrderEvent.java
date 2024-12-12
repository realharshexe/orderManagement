package com.orderService.orderManagement.kafkaEvent;
import com.orderService.orderManagement.Entity.Order;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private Long id;
    private String status;
    private BigDecimal totalAmount;



    public OrderEvent(Order order) {

        this.id = order.getId();
        this.status = order.getStatus().toString();
        this.totalAmount = order.getTotalAmount();
    }



}
