package org.org.flightbooking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRecord {
    private String bookingId;
    private String flightNumber;
    private BookingStatus status;
    private Double totalPrice;
    private Integer numberOfSeatsBooked;
    private List<PassengerRecord> passengers;
    private LocalDateTime bookingDate;

    public static BookingRecord create(String flightNumber, Double pricePerSeat, List<PassengerRecord> passengers) {
        int numberOfSeats = passengers.size();
        BookingRecord booking = new BookingRecord();
        booking.setBookingId(UUID.randomUUID().toString());
        booking.setFlightNumber(flightNumber);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setTotalPrice(pricePerSeat * numberOfSeats);
        booking.setNumberOfSeatsBooked(numberOfSeats);
        booking.setPassengers(passengers);
        booking.setBookingDate(LocalDateTime.now());
        return booking;
    }
}

