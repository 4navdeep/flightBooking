package org.org.flightbooking.controller;

import org.org.flightbooking.model.BookingRecord;
import org.org.flightbooking.model.PassengerRecord;
import org.org.flightbooking.service.BookingService;
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
@RequestMapping("/api/bookings")
@Tag(name = "bookings", description = "Flight Booking API")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    @Operation(summary = "Book a flight")
    public ResponseEntity<?> bookFlight(@RequestBody Map<String, Object> request) {
        try {
            String flightNumber = (String) request.get("flightNumber");
            Integer numberOfPassengers = (Integer) request.get("numberOfPassengers");
            List<Map<String, String>> passengersData = (List<Map<String, String>>) request.get("passengers");

            if (flightNumber == null || flightNumber.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Flight number is required", HttpStatus.BAD_REQUEST.value()));
            }

            if (passengersData == null || passengersData.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("At least one passenger is required", HttpStatus.BAD_REQUEST.value()));
            }

            List<PassengerRecord> passengers = passengersData.stream()
                    .map(p -> new PassengerRecord(
                            p.get("firstName"),
                            p.get("lastName"),
                            p.get("email"),
                            p.get("phoneNumber")
                    ))
                    .toList();

            BookingRecord booking = bookingService.createBooking(flightNumber, passengers);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Booking created successfully");
            response.put("data", booking);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            int status = e.getMessage().contains("Not enough") ? HttpStatus.CONFLICT.value() : HttpStatus.INTERNAL_SERVER_ERROR.value();
            return ResponseEntity.status(status)
                    .body(createErrorResponse(e.getMessage(), status));
        }
    }

    @GetMapping("/{bookingId}")
    @Operation(summary = "Get booking details")
    public ResponseEntity<?> getBooking(@PathVariable String bookingId) {
        try {
            BookingRecord booking = bookingService.getBooking(bookingId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Booking found");
            response.put("data", booking);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }

    @DeleteMapping("/{bookingId}")
    @Operation(summary = "Cancel a booking")
    public ResponseEntity<?> cancelBooking(@PathVariable String bookingId) {
        try {
            BookingRecord booking = bookingService.cancelBooking(bookingId);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("bookingId", booking.getBookingId());
            responseData.put("status", booking.getStatus());
            responseData.put("refundAmount", booking.getTotalPrice());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Booking cancelled successfully");
            response.put("data", responseData);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            int status = e.getMessage().contains("already cancelled") ? HttpStatus.CONFLICT.value() : HttpStatus.NOT_FOUND.value();
            return ResponseEntity.status(status)
                    .body(createErrorResponse(e.getMessage(), status));
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

