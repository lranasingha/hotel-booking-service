package com.techtest.hotel;

import com.techtest.hotel.Booking.Status;

import java.time.LocalDate;
import java.util.Collection;

public class BookingService {

    public Booking makeBooking(final BookingRequest bookingRequest) {
        return new Booking(Status.ACCEPTED, bookingRequest);
    }

    public Collection<Room> findRoomsByDate(final LocalDate date) {
        throw new UnsupportedOperationException("not yet implemented!");
    }
}
