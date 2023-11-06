package com.example.fun7Service.datastore;

import com.example.fun7Service.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple in-memory data store for Users.
 * This in-memory store is intended for development and testing purposes.
 * In a production environment, consider using a persistent database like GCP Datastore or another suitable database.
 */
@Service
public class UserDataStore {
    private Map<String, User> usersMap;

    public UserDataStore() {
        usersMap = new HashMap<>();

        User user1 = new User("user123", "Alice", "alice@example.com", 10);
        User user2 = new User("user456", "Bob", "bob@example.com", 3);

        usersMap.put(user1.getUserId(), user1);
        usersMap.put(user2.getUserId(), user2);
    }

    public User getUser(String userId) {
        return usersMap.get(userId);
    }

    public Map<String, User> getAllUsers() {
        return usersMap;
    }

    public boolean deleteUser(String userId) {
        return usersMap.remove(userId) != null;
    }

    public void addUser(User user) {
        usersMap.put(user.getUserId(), user);
    }
}
