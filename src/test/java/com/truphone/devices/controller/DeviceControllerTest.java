package com.truphone.devices.controller;

import com.google.gson.Gson;
import com.truphone.devices.model.Device;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Devices Controller Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeviceControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Test
    @DisplayName("Save new Device")
    void saveDevice() {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/devices";
        URI uri = null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Device device = new Device("Phone", "Samsung");

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Device> request = new HttpEntity<>(device, headers);
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);


        assertEquals(201, result.getStatusCodeValue());
        Gson g = new Gson();
        Device responseDevice = g.fromJson(result.getBody(), Device.class);
        assertEquals(device.getName(), responseDevice.getName(), "Should get the same name as the request");
        assertEquals(device.getBrand(), responseDevice.getBrand(), "Should get the same brand as the request");
    }


    @Test
    @DisplayName("Get All Devices")
    void getAllDevices() {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/devices/all";
        URI uri = null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        Assertions.assertEquals(200, result.getStatusCodeValue(), "Should receive 200 from server");
    }

}
