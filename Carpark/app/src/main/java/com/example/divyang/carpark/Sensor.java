package com.example.divyang.carpark;

/**
 * Created by gauri on 04/03/18.
 */

public class Sensor {

    public String booked;
    public String status;

    public Sensor() {

    }

    public String getBooked() {
        return booked;
    }

    public void setBooked(String booked) {
        this.booked = booked;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Sensor(String booked, String status) {
        this.booked = booked;
        this.status = status;
    }
}
