package com.lld.amazon.controller;

import com.lld.amazon.dto.PaymentRequest;
import com.lld.amazon.entity.Order;
import com.lld.amazon.enums.OrderStatus;
import com.lld.amazon.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}/place-order")
    public Order placeOrder(@PathVariable Long userId, @RequestBody PaymentRequest paymentRequest) {
        return orderService.placeOrder(userId, paymentRequest);
    }

    @PutMapping("/update-status/{orderId}")
    public Order updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus newStatus) {
        return orderService.updateOrderStatus(orderId, newStatus);
    }

    @PutMapping("/{orderId}/cancel")
    public Order cancelOrder(@PathVariable Long orderId) {
        return orderService.cancelOrder(orderId);
    }

}

