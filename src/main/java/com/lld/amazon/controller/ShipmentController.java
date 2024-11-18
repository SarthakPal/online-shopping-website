package com.lld.amazon.controller;

import com.lld.amazon.entity.Shipment;
import com.lld.amazon.enums.ShipmentStatus;
import com.lld.amazon.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @PostMapping("/create/{orderId}")
    public Shipment createShipment(@PathVariable Long orderId, @RequestParam Date estimatedArrivalTime, @RequestParam String shipmentMethod) {
        return shipmentService.createShipment(orderId, estimatedArrivalTime, shipmentMethod);
    }

    @PutMapping("/update-status/{trackingId}")
    public Shipment updateShipmentStatus(@PathVariable String trackingId, @RequestParam Date estimatedArrivalTime, @RequestParam String shipmentMethod, @RequestParam ShipmentStatus status) {
        return shipmentService.updateShipment(trackingId, estimatedArrivalTime, shipmentMethod, status);
    }

    @GetMapping("/track/{trackingId}")
    public Shipment trackShipment(@PathVariable String trackingId) {
        return shipmentService.trackShipment(trackingId);
    }
}

