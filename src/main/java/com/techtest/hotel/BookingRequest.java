package com.techtest.hotel;

import java.time.LocalDate;

public class BookingRequest {
    private final Guest guest;
    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final int roomNumber;

    public BookingRequest(final Guest guest,
                          final LocalDate fromDate,
                          final LocalDate toDate,
                          final int roomNumber) {

        this.guest = guest;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.roomNumber = roomNumber;
    }

    public Guest getGuest() {
        return guest;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
}
