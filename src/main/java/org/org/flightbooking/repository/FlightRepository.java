package org.org.flightbooking.repository;

import org.org.flightbooking.model.FlightRecord;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FlightRepository {
    private final ConcurrentHashMap<String, FlightRecord> flights = new ConcurrentHashMap<>();

    public FlightRepository() {
        initializeFlights();
    }

    private void initializeFlights() {
        // Initialize with sample flights
        flights.put("AA100", new FlightRecord(
                "AA100",
                "New York",
                "Los Angeles",
                LocalDateTime.of(2026, 7, 15, 8, 0, 0),
                LocalDateTime.of(2026, 7, 15, 11, 30, 0),
                150,
                120,
                299.99
        ));

        flights.put("BA200", new FlightRecord(
                "BA200",
                "London",
                "New York",
                LocalDateTime.of(2026, 7, 16, 10, 0, 0),
                LocalDateTime.of(2026, 7, 16, 13, 30, 0),
                180,
                150,
                499.99
        ));

        flights.put("UA300", new FlightRecord(
                "UA300",
                "San Francisco",
                "Chicago",
                LocalDateTime.of(2026, 7, 17, 14, 0, 0),
                LocalDateTime.of(2026, 7, 17, 20, 0, 0),
                200,
                200,
                199.99
        ));
    }

    public Optional<FlightRecord> findByFlightNumber(String flightNumber) {
        return Optional.ofNullable(flights.get(flightNumber));
    }

    public List<FlightRecord> findAll() {
        return new ArrayList<>(flights.values());
    }

    public void save(FlightRecord flight) {
        flights.put(flight.getFlightNumber(), flight);
    }
}


