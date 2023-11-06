package com.example.fun7Service.controller;


import com.example.fun7Service.model.ServiceStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceControllerIntegrationTest {
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        restTemplate = new TestRestTemplate();
    }

    @Test
    public void testCheckServicesEndpoint() {
        ResponseEntity<ServiceStatus> responseEntity = restTemplate.exchange(
                "http://localhost:8080/api/check-services?timezone=Europe/Ljubljana&userId=user123&cc=US",
                HttpMethod.GET,
                null,
                ServiceStatus.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ServiceStatus serviceStatus = responseEntity.getBody();
        assertNotNull(serviceStatus);
        assertEquals("enabled", serviceStatus.getMultiplayer());
        assertEquals("disabled", serviceStatus.getCustomerSupport());
        assertEquals("disabled", serviceStatus.getAds());
    }

    @Test
    public void testNonExistentUser() {
        ResponseEntity<ServiceStatus> responseEntity = restTemplate.exchange(
                "http://localhost:8080/api/check-services?timezone=Europe/Ljubljana&userId=nonexistentUser&cc=US",
                HttpMethod.GET,
                null,
                ServiceStatus.class
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void testInvalidTimezone() {
        ResponseEntity<ServiceStatus> responseEntity = restTemplate.exchange(
                "http://localhost:8080/api/check-services?timezone=InvalidTimezone&userId=user123&cc=US",
                HttpMethod.GET,
                null,
                ServiceStatus.class
        );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
