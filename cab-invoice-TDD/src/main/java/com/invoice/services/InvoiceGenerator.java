package com.invoice.services;

import com.invoice.entities.InvoiceSummary;
import com.invoice.entities.Ride;
import com.invoice.enums.RideType;

public class InvoiceGenerator {
    public double calculateFare(Ride ride) {
        double ratePerKm = 10;
        double ratePerMinute = 1;
        double minFare = 5;

        if (ride.getRideType() == RideType.PREMIUM) {
            ratePerKm = 15;
            ratePerMinute = 2;
            minFare = 20;
        }

        double fare = ride.getDistance() * ratePerKm + ride.getTime() * ratePerMinute;
        return Math.max(fare, minFare);
    }

    public double calculateFare(Ride[] rides) {
        double total = 0;
        for (Ride ride : rides) {
            total += calculateFare(ride);
        }
        return total;
    }

    public InvoiceSummary calculateFareSummary(Ride[] rides) {
        double totalFare = calculateFare(rides);
        return new InvoiceSummary(rides.length, totalFare);
    }

}
