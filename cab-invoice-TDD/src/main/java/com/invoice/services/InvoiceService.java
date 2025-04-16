package com.invoice.services;

import com.invoice.entities.InvoiceSummary;
import com.invoice.entities.Ride;
import com.invoice.repos.RideRepository;

public class InvoiceService {
    private final RideRepository repository;
    private final InvoiceGenerator generator;

    public InvoiceService(RideRepository repo, InvoiceGenerator gen) {
        this.repository = repo;
        this.generator = gen;
    }

    public InvoiceSummary getInvoiceSummary(String userId) {
        Ride[] rides = repository.getRides(userId);
        return generator.calculateFareSummary(rides);
    }
}