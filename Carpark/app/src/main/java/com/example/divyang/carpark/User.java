package com.example.divyang.carpark;

import java.util.List;

/**
 * Created by gauri on 04/03/18.
 */

public class User {

    public String userId;
    public String username;
    public String email;
    public Boolean active_booking;
    public Boolean active_parking;
    public List<bookingHistory> bookingHistories;

    public String selectedLocation;

    public User(){

    }



    public User(String userId, String username, String email, Boolean active_booking, Boolean active_parking, List<bookingHistory> bookingHistories,String selectedLocation) {

        this.userId = userId;
        this.username = username;
        this.email = email;
        this.active_booking = active_booking;
        this.active_parking = active_parking;
        this.bookingHistories = bookingHistories;
        this.selectedLocation = selectedLocation;
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

    public Boolean getActive_booking() {
        return active_booking;
    }

    public void setActive_booking(Boolean active_booking) {
        this.active_booking = active_booking;
    }

    public Boolean getActive_parking() {
        return active_parking;
    }

    public void setActive_parking(Boolean active_parking) {
        this.active_parking = active_parking;
    }

    public List<bookingHistory> getBookingHistories() {
        return bookingHistories;
    }

    public void setBookingHistories(List<bookingHistory> bookingHistories) {
        this.bookingHistories = bookingHistories;
    }

    public String getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(String selectedLocation) {
        this.selectedLocation = selectedLocation;
    }
}
