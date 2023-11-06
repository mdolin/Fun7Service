package com.example.fun7Service.controller;

import com.example.fun7Service.datastore.UserDataStore;
import com.example.fun7Service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/users")
public class AdminUserController {

    @Autowired
    private UserDataStore userDataStore;

    // Endpoint to list all users
    @GetMapping
    public ResponseEntity<List<User>> listAllUsers() {
        Map<String, User> userMap = userDataStore.getAllUsers();
        List<User> users = new ArrayList<>(userMap.values());
        return ResponseEntity.ok(users);
    }

    // Endpoint to get user details by ID
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserDetails(@PathVariable String userId) {
        User user = userDataStore.getUser(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // Endpoint to delete a user by ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        boolean deleted = userDataStore.deleteUser(userId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
