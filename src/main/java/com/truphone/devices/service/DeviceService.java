package com.truphone.devices.service;

import com.truphone.devices.model.Device;

import java.util.HashMap;
import java.util.List;


public interface DeviceService {
    Device saveDevice(Device device);

    List<Device> getAllDevices();

    void deleteDeviceById(long id);

    Device getDeviceById(long id);

    Device getDeviceByBrand(String brand);

    Device updateDevice(Device device, long id);

    Device updatePartialDevice(HashMap<String, String> jsonRequest, long id);

}
