package com.truphone.devices.service.impl;

import com.truphone.devices.exception.ResourceNotFoundException;
import com.truphone.devices.model.Device;
import com.truphone.devices.repository.DeviceRepository;
import com.truphone.devices.service.DeviceService;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();


    public void deleteDeviceById(long id) {
        deviceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Device", "Id", id));
        this.deviceRepository.deleteById(id);
    }

    @Override
    public Device getDeviceById(long id) {
        return deviceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Device", "Id", id));
    }

    @Override
    public Device getDeviceByBrand(String brand) {
        Device returnDevice = deviceRepository.findDeviceByBrand(brand);
        if (returnDevice != null) {
            return returnDevice;
        }
        throw new ResourceNotFoundException("Device", "Brand", brand);
    }

    @Override
    public Device updateDevice(Device device, long id) {
        Device existingDevice = deviceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Device", "Id", id));

        existingDevice.setName(device.getName());
        existingDevice.setBrand(device.getBrand());
        if (device.getCreationDate() != null) {
            existingDevice.setCreationDate(device.getCreationDate());
        }
        deviceRepository.save(existingDevice);
        return existingDevice;
    }

    @Override
    public Device updatePartialDevice(HashMap<String, String> jsonRequest, long id) {
        Device existingDevice = deviceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Device", "Id", id));

        if (jsonRequest.get("name") != null) {
            existingDevice.setName(jsonRequest.get("name"));
        }
        if (jsonRequest.get("brand") != null) {
            existingDevice.setBrand(jsonRequest.get("brand"));
        }
        if (jsonRequest.get("creationDate") != null) {
            //Using ISO8601 format
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            try {
                existingDevice.setCreationDate(formatter.parse(jsonRequest.get("creationDate")));
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }
        deviceRepository.save(existingDevice);
        return existingDevice;
    }

    @Override
    public Device saveDevice(Device device) {
        Set<ConstraintViolation<Device>> violations = validator.validate(device);
        if (device.getCreationDate() == null) {
            device.setCreationDate(new Date());
        }
        if (violations.size() > 0) {
            for (ConstraintViolation<Device> violation : violations) {
                System.out.println(violation.getMessage());
            }
            throw new RuntimeException("Error creating devices ");
        } else
            return this.deviceRepository.save(device);
    }

    @Override
    public List<Device> getAllDevices() {
        return this.deviceRepository.findAll();
    }

}
