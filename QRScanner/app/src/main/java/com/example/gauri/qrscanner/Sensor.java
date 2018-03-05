package com.example.gauri.qrscanner;

/**
 * Created by gauri on 05/03/18.
 */

public class Sensor {
    public String status;

    public Sensor() {
    }

    public String booked;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBooked() {
        return booked;
    }

    public void setBooked(String booked) {
        this.booked = booked;
    }

    public Sensor(String status, String booked) {
        this.status = status;
        this.booked = booked;
    }
}
