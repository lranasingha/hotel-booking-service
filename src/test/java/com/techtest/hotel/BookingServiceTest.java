package com.techtest.hotel;

import com.techtest.hotel.Booking.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingServiceTest {

    @Test
    void can_make_a_booking() {
        LocalDate toDate = LocalDate.now();
        LocalDate fromDate = toDate.plusDays(1);
        BookingRequest bookingRequest = new BookingRequest(
                new Guest("firstName", "lastName"),
                fromDate,
                toDate,
                1);

        Booking booking = new BookingService().makeReservation(bookingRequest);

        assertEquals(Status.ACCEPTED, booking.status());
        assertEquals(new Guest("firstName", "lastName"), booking.guest());
        assertEquals(fromDate, booking.from());
        assertEquals(toDate, booking.to());
        assertEquals(1, booking.roomNumber());
    }

}