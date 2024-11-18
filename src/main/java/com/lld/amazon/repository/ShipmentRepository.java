package com.lld.amazon.repository;

import com.lld.amazon.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Shipment findByShipmentNumber(String trackingId);

    Shipment findByOrderId(Long orderId);
}

