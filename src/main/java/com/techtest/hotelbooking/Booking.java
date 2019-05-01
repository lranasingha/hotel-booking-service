package com.techtest.hotelbooking;

import java.time.LocalDate;
import java.util.Objects;

import static com.techtest.hotelbooking.Booking.Status.ACCEPTED;
import static com.techtest.hotelbooking.Booking.Status.FAILED;
import static java.lang.String.format;

public final class Booking {

    public enum Status {
        ACCEPTED,
        FAILED
    }

    public static Booking acceptedBooking(final BookingRequest bookingRequest) {
        return new Booking(ACCEPTED, bookingRequest,
                format("The booking for the room %d has been successful.", bookingRequest.roomNumber()));
    }

    public static Booking failedBooking(final BookingRequest bookingRequest) {
        return new Booking(FAILED, bookingRequest,
                format("No room exists for the given room number %d.", bookingRequest.roomNumber()));
    }

    private final Status status;
    private final Guest guest;
    private final LocalDate date;
    private final int roomNumber;
    private final String statusMessage;


    private Booking(final Status status,
                    final BookingRequest bookingRequest,
                    final String statusMessage) {
        this.status = status;
        this.guest = bookingRequest.guest();
        this.date = bookingRequest.date();
        this.roomNumber = bookingRequest.roomNumber();
        this.statusMessage = statusMessage;
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

    public String statusMessage() {
        return statusMessage;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return roomNumber == booking.roomNumber &&
                status == booking.status &&
                guest.equals(booking.guest) &&
                date.equals(booking.date) &&
                statusMessage.equals(booking.statusMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, guest, date, roomNumber, statusMessage);
    }
}
