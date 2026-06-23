package org.org.flightbooking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightRecord {
    private String flightNumber;
    private String departureCity;
    private String arrivalCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer totalSeats;
    private Integer availableSeats;
    private Double pricePerSeat;

    public synchronized boolean hasAvailableSeats(int numberOfSeats) {
        return availableSeats >= numberOfSeats;
    }

    public synchronized void bookSeats(int numberOfSeats) {
        if (!hasAvailableSeats(numberOfSeats)) {
            throw new IllegalArgumentException("Not enough available seats");
        }
        this.availableSeats -= numberOfSeats;
    }

    public synchronized void cancelSeats(int numberOfSeats) {
        if (this.availableSeats + numberOfSeats > this.totalSeats) {
            throw new IllegalArgumentException("Cannot release more seats than total");
        }
        this.availableSeats += numberOfSeats;
    }
}

