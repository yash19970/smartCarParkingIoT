package com.example.divyang.carpark;

/**
 * Created by gauri on 04/03/18.
 */

public class User {

    public String userId;
    public String username;
    public String email;
    public Boolean active_booking;
    public Boolean active_parking;

    public User(){

    }

    public User(String userId,String username, String email, Boolean active_booking, Boolean active_parking) {

        this.userId = userId;
        this.username = username;
        this.email = email;
        this.active_booking = active_booking;
        this.active_parking = active_parking;
    }




}
