package com.orderService.orderManagement.service;
import com.orderService.orderManagement.Dao.OrderDao;
import com.orderService.orderManagement.Entity.Order;
import com.orderService.orderManagement.enums.OrderStatus;
import com.orderService.orderManagement.exception.OrderNotFoundException;
import com.orderService.orderManagement.kafkaEvent.OrderEvent;
import com.orderService.orderManagement.model.OrderRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderDao dao;

    @Autowired
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Value("${kafka.topic.order:default-order-topic}")
    private String orderTopic;



    public OrderServiceImpl(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Retry(name = "orderServiceRetry", fallbackMethod = "handleRetryFailure")
    @CircuitBreaker(name = "orderServiceCircuitBreaker", fallbackMethod = "handleCircuitBreakerFailure")
    @Override
    public Order createOrder(OrderRequest request) {
        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(request.getTotalAmount());
        order = dao.save(order);                                                    // Saving to Db
        logger.info("Order successfully created with ID: {}", order.getId());
        publishOrderEvent(orderTopic, new OrderEvent(order));                       // Publishing order creation event
        return order;
    }

    @Transactional
    @Override
    public Order getOrderById(Long id) {
        logger.info("Fetching order with ID: {}", id);
        return dao.findById(id).orElseThrow(() -> {
            logger.error("Order not found with ID: {}", id);
            return new OrderNotFoundException(id);
        });
    }
    @Override
    public void cancelOrder(Long id) {
        Order order = dao.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.setStatus(OrderStatus.CANCELLED);
        dao.save(order);
        logger.info(" Starting to publish event ");
        publishOrderEvent(orderTopic, new OrderEvent(order));
    }

    private void publishOrderEvent(String topic, OrderEvent event) {
        kafkaTemplate.send(topic, event);
        logger.info("Published event to topic {}: {}", topic, event);
    }

    public Order handleRetryFailure(OrderRequest request, Throwable t) {
        logger.error("Retry failure: Unable to process order for customer {}", request.getCustomerName(), t);
        Order fallbackOrder = new Order();
        fallbackOrder.setCustomerName(request.getCustomerName());
        fallbackOrder.setStatus(OrderStatus.FAILED);
        fallbackOrder.setTotalAmount(request.getTotalAmount());
        return fallbackOrder;
    }


    public Order handleCircuitBreakerFailure(OrderRequest request, Throwable t) {
          throw new RuntimeException("Order service is currently unavailable", t);
      }



}
