package com.lld.amazon.service;

import com.lld.amazon.dto.PaymentRequest;
import com.lld.amazon.dto.PaymentResponse;
import com.lld.amazon.entity.*;
import com.lld.amazon.enums.OrderStatus;
import com.lld.amazon.enums.ShipmentStatus;
import com.lld.amazon.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ShipmentRepository shipmentRepository;

    public Order placeOrder(Long userId, PaymentRequest paymentRequest) {
        // Fetch the shopping cart for the user
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No shopping cart found for user ID: " + userId));

        if (shoppingCart.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Shopping cart is empty.");
        }

        // Process payment
        boolean paymentSuccess = paymentService.processPayment(paymentRequest);

        if (!paymentSuccess) {
            throw new RuntimeException("Payment failed.");
        }

        // Create the Order
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString()); // Generate unique order number
        order.setStatus(OrderStatus.PLACED);
        order.setOrderDate(LocalDateTime.now());
        order.setShoppingCart(shoppingCart);

        OrderLog orderLog = saveOrderLog(order);

        order.setOrderLog(List.of(orderLog));

        savePaymentDetails(paymentRequest, order);

        // Save the order
        Order savedOrder = orderRepository.save(order);

        // Update product quantities and clear shopping cart
        shoppingCart.getProducts().forEach(cartProduct -> {
            Product product = cartProduct.getProduct();
            product.setAvailableItemCount(product.getAvailableItemCount() - cartProduct.getQuantity());
            productRepository.save(product); // Update product inventory
        });

        shoppingCartRepository.delete(shoppingCart); // Clear the shopping cart

        return savedOrder;
    }

    private void savePaymentDetails(PaymentRequest paymentRequest, Order order) {
        // Save payment details
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus("SUCCESS");
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setAmount(paymentRequest.getAmount());
        payment.setCurrency(paymentRequest.getCurrency());
        payment.setCardHolderName(paymentRequest.getCardDetails().getCardHolderName());
        payment.setCardNumber(paymentRequest.getCardDetails().getCardNumber());
        payment.setExpiryDate(paymentRequest.getCardDetails().getExpiryDate());
        payment.setCvv(paymentRequest.getCardDetails().getCvv());
        paymentRepository.save(payment);
    }

    private static OrderLog saveOrderLog(Order order) {
        // Create initial OrderLog
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderNumber(order.getOrderNumber());
        orderLog.setCreationDate(new Date());
        orderLog.setStatus(OrderStatus.PLACED);
        orderLog.setOrder(order);
        return orderLog;
    }

    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        // Fetch the order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        // Update the order status
        order.setStatus(newStatus);

        // Save the updated order
        Order updatedOrder = orderRepository.save(order);

        // Send an email notification
        String subject = "Order Status Updated";
        String message = "Your order #" + order.getOrderNumber() + " status has been updated to: " + newStatus;
        notificationService.sendNotification(order.getUser().getEmail(), subject, message);

        return updatedOrder;
    }

    public Order cancelOrder(Long orderId) {
        // Fetch the order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        // Check if the order has been shipped
        Shipment shipment = shipmentRepository.findByOrderId(orderId);
        if (shipment != null && shipment.getStatus().equals(ShipmentStatus.IN_TRANSIT)) {
            throw new IllegalStateException("Order cannot be canceled as it has already been shipped.");
        }

        // Update the order status to CANCELLED
        order.setStatus(OrderStatus.CANCELLED);
        Order canceledOrder = orderRepository.save(order);

        // Send a notification
        String message = "Your order #" + order.getOrderNumber() + " has been canceled successfully.";
        notificationService.sendNotification(order.getUser().getEmail(), "Order Canceled", message);

        return canceledOrder;
    }

}

