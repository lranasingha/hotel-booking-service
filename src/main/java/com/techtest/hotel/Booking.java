package com.techtest.hotel;

import java.time.LocalDate;
import java.util.Objects;

public final class Booking {
    public enum Status {
        ACCEPTED
    }

    private final Status status;
    private final Guest guest;
    private final LocalDate date;
    private final int roomNumber;

    public Booking(final Status status, final BookingRequest bookingRequest) {
        this.status = status;
        this.guest = bookingRequest.guest();
        this.date = bookingRequest.date();
        this.roomNumber = bookingRequest.roomNumber();
    }

    public Status status() {
        return status;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return roomNumber == booking.roomNumber &&
                status == booking.status &&
                guest.equals(booking.guest) &&
                date.equals(booking.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, guest, date, roomNumber);
    }
}
