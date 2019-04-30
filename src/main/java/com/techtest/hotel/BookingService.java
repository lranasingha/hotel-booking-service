package com.techtest.hotel;

import com.techtest.hotel.Booking.Status;

public class BookingService {

    public Booking makeReservation(final BookingRequest bookingRequest) {
        return new Booking(Status.ACCEPTED, bookingRequest);
    }

}
