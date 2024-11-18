package com.lld.amazon.entity;

import com.lld.amazon.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shipmentNumber;

    @Temporal(TemporalType.TIMESTAMP)
    private Date shipmentDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date estimatedArrival;

    private String shipmentMethod; // e.g., "Air", "Ground", "Sea"

    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShipmentLog> shipmentLogs;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String status;

}


