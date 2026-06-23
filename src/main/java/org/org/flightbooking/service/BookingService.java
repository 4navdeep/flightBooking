package org.org.flightbooking.service;

import org.org.flightbooking.model.BookingRecord;
import org.org.flightbooking.model.BookingStatus;
import org.org.flightbooking.model.FlightRecord;
import org.org.flightbooking.model.PassengerRecord;
import org.org.flightbooking.repository.BookingRepository;
import org.org.flightbooking.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    public BookingRecord createBooking(String flightNumber, List<PassengerRecord> passengers) throws Exception {
        if (passengers == null || passengers.isEmpty()) {
            throw new Exception("At least one passenger is required");
        }

        // Get flight and check availability
        FlightRecord flight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new Exception("Flight not found: " + flightNumber));

        int numberOfSeats = passengers.size();

        // Check overbooking
        synchronized (flight) {
            if (!flight.hasAvailableSeats(numberOfSeats)) {
                throw new Exception("Not enough available seats. Available: " + flight.getAvailableSeats() + ", Requested: " + numberOfSeats);
            }

            // Book the seats
            flight.bookSeats(numberOfSeats);
        }

        // Create booking
        BookingRecord booking = BookingRecord.create(flightNumber, flight.getPricePerSeat(), passengers);

        // Save booking
        bookingRepository.save(booking);

        return booking;
    }

    public BookingRecord getBooking(String bookingId) throws Exception {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking not found: " + bookingId));
    }

    public BookingRecord cancelBooking(String bookingId) throws Exception {
        BookingRecord booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking not found: " + bookingId));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new Exception("Booking is already cancelled");
        }

        // Release seats
        FlightRecord flight = flightRepository.findByFlightNumber(booking.getFlightNumber())
                .orElseThrow(() -> new Exception("Flight not found: " + booking.getFlightNumber()));

        synchronized (flight) {
            flight.cancelSeats(booking.getNumberOfSeatsBooked());
        }

        // Update booking status
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.update(booking);

        return booking;
    }

    public List<BookingRecord> getBookingsByFlight(String flightNumber) {
        return bookingRepository.findByFlightNumber(flightNumber);
    }
}


