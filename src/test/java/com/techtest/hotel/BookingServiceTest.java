package com.techtest.hotel;

import com.techtest.hotel.Booking.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;

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

        Booking booking = new BookingService().makeBooking(bookingRequest);

        assertEquals(Status.ACCEPTED, booking.status());
        assertEquals(new Guest("firstName", "lastName"), booking.guest());
        assertEquals(fromDate, booking.from());
        assertEquals(toDate, booking.to());
        assertEquals(1, booking.roomNumber());
    }


    @Test
    public void can_find_available_rooms_by_date() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate dayAfterTomorrow = today.plusDays(1);

        BookingRequest bookingRequest = new BookingRequest(
                new Guest("firstName", "lastName"),
                today,
                tomorrow.plusDays(1),
                1);

        BookingService bookingService = new BookingService();
        bookingService.makeBooking(bookingRequest);

        Collection<Room> rooms = bookingService.findRoomsByDate(dayAfterTomorrow);

        assertEquals(1, rooms.size());
        assertEquals(new Room(1), rooms.iterator().next());
    }


}