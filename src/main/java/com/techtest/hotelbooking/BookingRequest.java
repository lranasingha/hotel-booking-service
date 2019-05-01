package com.techtest.hotelbooking;

import java.time.LocalDate;

public final class BookingRequest {
    private final Guest guest;
    private final LocalDate date;
    private final int roomNumber;

    public BookingRequest(final Guest guest,
                          final LocalDate date,
                          final int roomNumber) {

        this.guest = guest;
        this.date = date;
        this.roomNumber = roomNumber;
    }

    public Guest guest() {
        return guest;
    }

    public LocalDate date() {
        return date;
    }

    public int roomNumber() {
        return roomNumber;
    }
}
