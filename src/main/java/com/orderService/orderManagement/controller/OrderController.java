package com.orderService.orderManagement.controller;

import com.orderService.orderManagement.Entity.Order;
import com.orderService.orderManagement.model.OrderRequest;
import com.orderService.orderManagement.model.OrderResponse;
import com.orderService.orderManagement.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

     private OrderService orderService;

     @PostMapping()
     public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
          Order order = orderService.createOrder(orderRequest);
          OrderResponse response = mapToResponse(order);
          return ResponseEntity.status(HttpStatus.CREATED).body(response);

     }
     @GetMapping("/{id}")
     public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
          Order order = orderService.getOrderById(id);
          OrderResponse response = mapToResponse(order);
          return ResponseEntity.ok(response);
     }
          private OrderResponse mapToResponse(Order order) {
               return new OrderResponse(
                       order.getId(),
                       order.getCustomerName(),
                       order.getStatus(),
                       order.getTotalAmount(),
                       order.getCreatedAt()
               );
     }

     @PutMapping("/{id}/cancel")
     public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
          orderService.cancelOrder(id);
          return ResponseEntity.noContent().build();
     }
}


