package com.orderService.orderManagement.Dao;

import com.orderService.orderManagement.Entity.Order;
import com.orderService.orderManagement.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
}
