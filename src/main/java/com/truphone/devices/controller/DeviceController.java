package com.truphone.devices.controller;

import com.truphone.devices.model.Device;
import com.truphone.devices.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/all")
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping("{id}")
    public Device getDeviceById(@PathVariable("id") long deviceId) {
        return deviceService.getDeviceById(deviceId);
    }

    @GetMapping("/brands/{brand}")
    public Device getDeviceById(@PathVariable("brand") String brand) {
        return deviceService.getDeviceByBrand(brand);
    }

    @PutMapping("{id}")
    public ResponseEntity<Device> updateEntireDevice(@RequestBody Device device,
                                                     @PathVariable("id") long deviceId) {
        return new ResponseEntity<>(deviceService.updateDevice(device, deviceId), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Device> updatePartialDevice(@RequestBody HashMap<String, String> jsonRequest,
                                                      @PathVariable("id") long deviceId) {
        return new ResponseEntity<>(deviceService.updatePartialDevice(jsonRequest, deviceId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Device> saveDevice(@RequestBody Device device) {
        return new ResponseEntity<>(deviceService.saveDevice(device), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable("id") long id) {
        this.deviceService.deleteDeviceById(id);
        return new ResponseEntity<String>("Device successfully deleted", HttpStatus.OK);
    }

}
