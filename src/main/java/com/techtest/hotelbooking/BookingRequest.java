package com.techtest.hotelbooking;

import java.time.LocalDate;

public final class BookingRequest {
    private final Guest guest;
    private final LocalDate fromDate;
    private final int roomNumber;

    public BookingRequest(final Guest guest,
                          final LocalDate fromDate,
                          final int roomNumber) {

        this.guest = guest;
        this.fromDate = fromDate;
        this.roomNumber = roomNumber;
    }

    public Guest guest() {
        return guest;
    }

    public LocalDate date() {
        return fromDate;
    }

    public int roomNumber() {
        return roomNumber;
    }
}
