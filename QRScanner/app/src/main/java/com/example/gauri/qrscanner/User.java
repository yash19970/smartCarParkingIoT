package com.example.gauri.qrscanner;

/**
 * Created by gauri on 05/03/18.
 */

public class User {
    public boolean active_booking;
    public boolean active_parking;
    public String email;
    public String userId;
    public String username;

    public User(boolean active_booking, boolean active_parking, String email, String userId, String username) {
        this.active_booking = active_booking;
        this.active_parking = active_parking;
        this.email = email;
        this.userId = userId;
        this.username = username;
    }
    public User(){}

    public boolean isActive_booking() {
        return active_booking;
    }

    public void setActive_booking(boolean active_booking) {
        this.active_booking = active_booking;
    }

    public boolean isActive_parking() {
        return active_parking;
    }

    public void setActive_parking(boolean active_parking) {
        this.active_parking = active_parking;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
