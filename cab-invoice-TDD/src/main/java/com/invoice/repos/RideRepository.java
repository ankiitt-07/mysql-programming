package com.invoice.repos;

import com.invoice.entities.Ride;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RideRepository {
    private Map<String, List<Ride>> userRides = new HashMap<>();

    public void addRides(String userId, Ride[] rides) {
        userRides.put(userId, Arrays.asList(rides));
    }

    public Ride[] getRides(String userId) {
        List<Ride> rides = userRides.get(userId);
        return rides != null ? rides.toArray(new Ride[0]) : new Ride[0];
    }
}