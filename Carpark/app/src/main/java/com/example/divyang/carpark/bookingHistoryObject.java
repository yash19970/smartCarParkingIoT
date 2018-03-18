package com.example.divyang.carpark;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;

/**
 * Created by gauri on 17/03/18.
 */

public class bookingHistoryObject {


    public Date inTime;
    public Date outTime;
    public String locationValue;


    // empty constructor
    public bookingHistoryObject() {

    }

    public bookingHistoryObject(Date inTime, Date outTime, String locationValue) {
        this.inTime = inTime;
        this.outTime = outTime;
        this.locationValue = locationValue;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public String getLocationValue() {
        return locationValue;
    }

    public void setLocationValue(String locationValue) {
        this.locationValue = locationValue;
    }




}
