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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Device Controller Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeviceControllerTest {

    @LocalServerPort
    int randomServerPort;

    final String NAME = "Phone";
    final String BRAND = "Nokia";

    @Test
    @DisplayName("Save new Device")
    void testSaveDevice() {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/devices";
        URI uri = null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Device device = new Device(NAME, BRAND);

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
    void testSaveOneDeviceAndTryToGetAllDevices() {
        testSaveDevice();

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
        Gson g = new Gson();
        List<Device> responseDevice = g.fromJson(result.getBody(), ArrayList.class);
        Assertions.assertTrue(responseDevice.size() > 0);
    }

    @Test
    @DisplayName("Save Device and get it by ID")
    void testSaveDeviceAndThenGetDeviceById() {
        testSaveDevice();

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/devices/1";
        URI uri = null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        assertEquals(200, result.getStatusCodeValue());
        Gson g = new Gson();
        Device responseDevice = g.fromJson(result.getBody(), Device.class);
        assertEquals(NAME, responseDevice.getName(), "Should get the same name as the request");
        assertEquals(BRAND, responseDevice.getBrand(), "Should get the same brand as the request");
    }

    @Test
    @DisplayName("Save the device and delete it")
    void testSaveDeviceAndThenDeleteDevice() {
        testSaveDevice();

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/devices/1";
        URI uri = null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);

        assertEquals(200, result.getStatusCodeValue(), "Should receive 200 from server");
        assertEquals("Device successfully deleted", result.getBody(), "Should return deleted success message");
    }

}
