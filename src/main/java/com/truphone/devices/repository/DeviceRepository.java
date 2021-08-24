package com.truphone.devices.repository;

import com.truphone.devices.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findDeviceByBrand(String brand);
}
