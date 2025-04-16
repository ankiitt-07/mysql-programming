package com.invoice.entities;

import com.invoice.enums.RideType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ride {
    private double distance;
    private int time;
    private RideType rideType;

    public Ride(double distance, int time, RideType rideType) {
        this.distance = distance;
        this.time = time;
        this.rideType = rideType;
    }
}
