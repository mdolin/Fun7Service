package com.example.fun7Service.controller;

import com.example.fun7Service.datastore.UserDataStore;
import com.example.fun7Service.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminUserControllerIntegrationTest {
    private TestRestTemplate restTemplate;

    @Autowired
    private UserDataStore userDataStore;

    @Before
    public void setup() {
        restTemplate = new TestRestTemplate();

        User testUser = new User("testUser", "Test User", "test@example.com", 5);
        userDataStore.addUser(testUser);
    }

    @Test
    public void testListAllUsers() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "http://localhost:8080/admin/api/users",
                String.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetUserDetails() {
        ResponseEntity<User> responseEntity = restTemplate.getForEntity(
                "http://localhost:8080/admin/api/users/testUser",
                User.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        User responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals("testUser", responseBody.getUserId());
        assertEquals("Test User", responseBody.getUsername());
        assertEquals("test@example.com", responseBody.getEmail());
        assertEquals(5, responseBody.getUserGameUsage());
    }

    @Test
    public void testGetUserDetails_NotFound() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "http://localhost:8080/admin/api/users/nonexistentUser",
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteUser() {
        // Add a user to the data store
        User user = new User("testUser", "Test User", "test@example.com", 5);
        userDataStore.addUser(user);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:8080/admin/api/users/testUser",
                org.springframework.http.HttpMethod.DELETE,
                null,
                String.class
        );

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteUser_NotFound() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:8080/admin/api/users/nonexistentUser",
                org.springframework.http.HttpMethod.DELETE,
                null,
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}

