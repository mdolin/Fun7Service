package com.example.fun7Service.model;

public class User {
    private String userId;
    private String username;
    private String email;
    private int userGameUsage;

    public User() {
        // Default constructor
    }

    public User(String userId, String username, String email, int userGameUsage) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.userGameUsage = userGameUsage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserGameUsage() {
        return userGameUsage;
    }

    public void setUserGameUsage(int userGameUsage) {
        this.userGameUsage = userGameUsage;
    }
}
