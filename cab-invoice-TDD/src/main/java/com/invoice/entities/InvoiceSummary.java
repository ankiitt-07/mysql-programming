package com.invoice.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceSummary {
    private int totalRides;
    private double totalFare;
    private double averageFare;

    public InvoiceSummary(int totalRides, double totalFare) {
        this.totalRides = totalRides;
        this.totalFare = totalFare;
        this.averageFare = totalFare / totalRides;
    }
}