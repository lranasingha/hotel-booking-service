package com.techtest.hotelbooking;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.techtest.hotelbooking.Booking.Status.ACCEPTED;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class SimpleBookingStoreTest {

    private final SimpleBookingStore bookingStore = new SimpleBookingStore();

    @Test
    void can_store_a_booking() {
        var date = LocalDate.now();
        var bookingRequest = new BookingRequest(new Guest("A", "B"), date, 10);
        var booking = Booking.acceptedBooking(bookingRequest);

        bookingStore.store(booking);

        assertEquals(Booking.acceptedBooking(bookingRequest), bookingStore
                .findBookingsBy(b -> b.status() == ACCEPTED)
                .iterator()
                .next());
    }

    @Test
    void only_returns_bookings_if_condition_is_satisfied() {
        var dateOne = LocalDate.now();
        var bookingRequestOne = new BookingRequest(new Guest("A", "B"), dateOne, 10);
        var bookingOne = Booking.acceptedBooking(bookingRequestOne);

        var dateTwo = dateOne.plusDays(1);
        var bookingRequestTwo = new BookingRequest(new Guest("A", "B"), dateTwo, 10);
        var bookingTwo = Booking.acceptedBooking(bookingRequestTwo);

        bookingStore.store(bookingOne);
        bookingStore.store(bookingTwo);

        assertEquals(Booking.acceptedBooking(bookingRequestTwo), bookingStore
                .findBookingsBy(b -> b.date().equals(dateTwo))
                .iterator()
                .next());
    }

}