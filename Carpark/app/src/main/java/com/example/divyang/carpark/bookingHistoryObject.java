package com.example.divyang.carpark;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;

/**
 * Created by gauri on 17/03/18.
 */

public  class bookingHistoryObject {



        private String inTime;
        private String outTime;
        private String locationValue;


        // empty constructor
        public bookingHistoryObject() {

        }

        public bookingHistoryObject(String inTime, String outTime, String locationValue) {
            this.inTime = inTime;
            this.outTime = outTime;
            this.locationValue = locationValue;
        }

        public String getInTime() {
            return inTime;
        }

        public void setInTime(String inTime) {
            this.inTime = inTime;
        }

        public String getOutTime() {
            return outTime;
        }

        public void setOutTime(String outTime) {
            this.outTime = outTime;
        }

        public String getLocationValue() {
            return locationValue;
        }

        public void setLocationValue(String locationValue) {
            this.locationValue = locationValue;
        }


    }

