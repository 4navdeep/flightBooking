package org.org.flightbooking.repository;

import org.org.flightbooking.model.BookingRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookingRepository {
    private final ConcurrentHashMap<String, BookingRecord> bookings = new ConcurrentHashMap<>();

    public Optional<BookingRecord> findById(String bookingId) {
        return Optional.ofNullable(bookings.get(bookingId));
    }

    public List<BookingRecord> findAll() {
        return new ArrayList<>(bookings.values());
    }

    public List<BookingRecord> findByFlightNumber(String flightNumber) {
        return bookings.values().stream()
                .filter(b -> b.getFlightNumber().equals(flightNumber))
                .toList();
    }

    public void save(BookingRecord booking) {
        bookings.put(booking.getBookingId(), booking);
    }

    public void update(BookingRecord booking) {
        bookings.put(booking.getBookingId(), booking);
    }

    public void delete(String bookingId) {
        bookings.remove(bookingId);
    }
}


