package net.javaguides.orderservice.controller;

import dto.Order;
import dto.OrderEvent;
import net.javaguides.orderservice.kafka.OrderProducer;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }


    @PostMapping("/orders")
    public String placeOrder(Order order){
        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent orderEvent=new OrderEvent();
        orderEvent.setStatus("pending");
        orderEvent.setMessage("order status is in pending state");
        orderEvent.setOrder(order);
        orderProducer.sendMessage(orderEvent);
        return "order placed successfully";


    }
}
