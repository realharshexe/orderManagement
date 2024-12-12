package com.orderService.orderManagement.service;

import com.orderService.orderManagement.Entity.Order;
import com.orderService.orderManagement.model.OrderRequest;

public interface OrderService {

    Order createOrder(OrderRequest orderRequest);

    Order getOrderById(Long id);

  //  Order handleRetryFailure(OrderRequest request, Throwable t);

   // Order handleCircuitBreakerFailure(OrderRequest request, Throwable t);

    void cancelOrder(Long id);
}
