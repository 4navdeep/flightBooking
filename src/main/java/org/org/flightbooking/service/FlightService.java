package org.org.flightbooking.service;

import org.org.flightbooking.model.FlightRecord;
import org.org.flightbooking.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    public Optional<FlightRecord> searchFlight(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }

    public List<FlightRecord> getAllFlights() {
        return flightRepository.findAll();
    }

    public FlightRecord getFlight(String flightNumber) throws Exception {
        return flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new Exception("Flight not found: " + flightNumber));
    }
}


