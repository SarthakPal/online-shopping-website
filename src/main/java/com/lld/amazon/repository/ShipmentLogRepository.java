package com.lld.amazon.repository;

import com.lld.amazon.entity.ShipmentLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentLogRepository extends JpaRepository<ShipmentLog, Long> {
}

