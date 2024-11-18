package com.lld.amazon.service;

import com.lld.amazon.entity.Order;
import com.lld.amazon.entity.Shipment;
import com.lld.amazon.entity.ShipmentLog;
import com.lld.amazon.enums.ShipmentStatus;
import com.lld.amazon.repository.OrderRepository;
import com.lld.amazon.repository.ShipmentLogRepository;
import com.lld.amazon.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentLogRepository shipmentLogRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private NotificationService notificationService;

    public Shipment createShipment(Long orderId, Date estimatedArrival, String shipmentMethod) {
        // Fetch the order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));

        // Create a new shipment
        Shipment shipment = new Shipment();
        shipment.setShipmentNumber("SHIP-" + orderId + "-" + System.currentTimeMillis());
        shipment.setShipmentDate(new Date());
        shipment.setEstimatedArrival(estimatedArrival);
        shipment.setShipmentMethod(shipmentMethod);
        shipment.setStatus(ShipmentStatus.PENDING.toString());
        shipment.setOrder(order);

        // Initial ShipmentLog
        addShipmentLog(shipment.getShipmentNumber(), ShipmentStatus.PENDING);

        return shipmentRepository.save(shipment);
    }

    public ShipmentLog addShipmentLog(String shipmentNumber, ShipmentStatus status) {
        Shipment shipment = shipmentRepository.findByShipmentNumber(shipmentNumber);
        if (shipment == null) {
            throw new IllegalArgumentException("Shipment not found with tracking ID: " + shipmentNumber);
        }

        ShipmentLog log = new ShipmentLog();
        log.setShipmentNumber(shipmentNumber);
        log.setCreationDate(new Date());
        log.setStatus(status);
        log.setShipment(shipment);

        return shipmentLogRepository.save(log);
    }

    public Shipment trackShipment(String shipmentNumber) {
        return shipmentRepository.findByShipmentNumber(shipmentNumber);
    }

    public Shipment updateShipment(String shipmentNumber, Date newEstimatedArrival, String newShipmentMethod, ShipmentStatus newStatus) {
        // Fetch the shipment by shipment number
        Shipment shipment = shipmentRepository.findByShipmentNumber(shipmentNumber);
        if (shipment == null) {
            throw new IllegalArgumentException("Shipment not found with tracking ID: " + shipmentNumber);
        }

        // Update the shipment details
        if (newEstimatedArrival != null) {
            shipment.setEstimatedArrival(newEstimatedArrival);
        }

        if (newShipmentMethod != null && !newShipmentMethod.isEmpty()) {
            shipment.setShipmentMethod(newShipmentMethod);
        }

        if (newStatus != null) {
            // Add a log entry for the status update
            ShipmentLog log = new ShipmentLog();
            log.setShipmentNumber(shipmentNumber);
            log.setCreationDate(new Date());
            log.setStatus(newStatus);
            log.setShipment(shipment);
            shipmentLogRepository.save(log);
        }

        String subject = "Order Status Updated";
        String message = "Your order #" + shipment.getOrder().getOrderNumber() + " status has been updated to: " + newStatus;
        notificationService.sendNotification(shipment.getOrder().getUser().getEmail(), subject, message);

        return shipmentRepository.save(shipment);
    }


}

