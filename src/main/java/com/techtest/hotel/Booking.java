package com.techtest.hotel;

import java.time.LocalDate;

public class Booking {
    public enum Status {
        ACCEPTED
    }

    private Status status;
    private Guest guest;
    private LocalDate from;
    private LocalDate to;
    private int roomNumber;

    public Booking(final Status status, final BookingRequest bookingRequest) {
        this.status = status;
        this.guest = bookingRequest.getGuest();
        this.from = bookingRequest.getFromDate();
        this.to = bookingRequest.getToDate();
        this.roomNumber = bookingRequest.getRoomNumber();
    }

    public Status status() {
        return status;
    }

    public Guest guest() {
        return guest;
    }

    public LocalDate from() {
        return from;
    }

    public LocalDate to() {
        return to;
    }

    public int roomNumber() {
        return roomNumber;
    }
}
