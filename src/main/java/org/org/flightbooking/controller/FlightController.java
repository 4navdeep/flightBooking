package org.org.flightbooking.controller;

import org.org.flightbooking.model.FlightRecord;
import org.org.flightbooking.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/flights")
@Tag(name = "flights", description = "Flight Search API")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping("/search")
    @Operation(summary = "Search available flights")
    public ResponseEntity<?> searchFlights(@RequestBody Map<String, String> request) {
        try {
            String flightNumber = request.get("flightNumber");
            if (flightNumber == null || flightNumber.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Flight number is required", HttpStatus.BAD_REQUEST.value()));
            }

            List<FlightRecord> flights = flightService.getAllFlights().stream()
                    .filter(f -> f.getFlightNumber().equalsIgnoreCase(flightNumber))
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("success", !flights.isEmpty());
            response.put("message", flights.isEmpty() ? "No flights found" : "Flights found");
            response.put("data", flights);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    private Map<String, Object> createErrorResponse(String message, int status) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("message", message);
        error.put("status", status);
        return error;
    }
}

