package com.techtest.hotelbooking;

import com.techtest.hotelbooking.room.HotelRoomHandlingService;
import com.techtest.hotelbooking.room.Room;
import com.techtest.hotelbooking.room.RoomHandlingService;
import com.techtest.hotelbooking.room.SimpleRoomStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;

import static com.techtest.hotelbooking.Booking.Status.ACCEPTED;
import static com.techtest.hotelbooking.Booking.Status.FAILED;
import static com.techtest.hotelbooking.Booking.acceptedBooking;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class BookingServiceTest {

    private BookingStore bookingStore;
    private RoomHandlingService roomHandlingService;
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingStore = new SimpleBookingStore();
        roomHandlingService = new HotelRoomHandlingService(new SimpleRoomStore());
        bookingService = new BookingService(bookingStore, roomHandlingService);
    }

    @Test
    void can_make_a_booking() {
        var date = LocalDate.now();
        var bookingRequest = new BookingRequest(new Guest("firstName", "lastName"), date, 1);
        roomHandlingService.addRoom(new Room(1));

        bookingService.makeBooking(bookingRequest);

        var booking = bookingStore.findBookingsBy(b -> b.roomNumber() == 1).iterator().next();

        assertEquals(ACCEPTED, booking.status());
        assertEquals(new Guest("firstName", "lastName"), booking.guest());
        assertEquals(date, booking.date());
        assertEquals(1, booking.roomNumber());
        assertEquals("The booking for the room 1 has been successful.", booking.statusMessage());
    }

    @Test
    void the_booking_is_stored_in_the_booking_store() {
        var date = LocalDate.now();
        var bookingRequest = new BookingRequest(new Guest("firstName", "lastName"), date, 1);
        roomHandlingService.addRoom(new Room(1));

        bookingService.makeBooking(bookingRequest);
        var booking = bookingStore.findBookingsBy(b -> b.roomNumber() == 1).iterator().next();
        assertEquals(ACCEPTED, booking.status());

        roomHandlingService.removeRoom(1);
        bookingService.makeBooking(bookingRequest);

        booking = bookingStore.findBookingsBy(b -> b.status() != ACCEPTED).iterator().next();
        assertEquals(FAILED, booking.status());
        assertEquals("No room exists for the given room number 1.", booking.statusMessage());
    }

    @Test
    void fail_to_make_a_booking_if_no_room_exists() {
        var today = LocalDate.now();

        var bookingRequest = new BookingRequest(new Guest("firstName", "lastName"), today, 1);

        var booking = bookingService.makeBooking(bookingRequest);

        assertEquals(FAILED, booking.status());
    }

    @Test
    void can_find_available_rooms_by_date() {
        var today = LocalDate.now();
        roomHandlingService.addRoom(new Room(1));
        roomHandlingService.addRoom(new Room(3));
        var bookingRequest = new BookingRequest(new Guest("firstName", "lastName"), today, 1);

        bookingService.makeBooking(bookingRequest);

        Collection<Room> rooms = bookingService.findAvailableRoomsByDate(today);

        assertEquals(1, rooms.size());
        assertEquals(new Room(3), rooms.iterator().next());
    }

    @Test
    void can_find_bookings_by_the_given_guest() {
        var today = LocalDate.now();
        Guest guest = new Guest("firstName", "lastName");
        roomHandlingService.addRoom(new Room(1));
        roomHandlingService.addRoom(new Room(3));
        roomHandlingService.addRoom(new Room(5));

        BookingRequest bookingRequest = new BookingRequest(guest, today, 5);
        bookingService.makeBooking(bookingRequest);

        Collection<Booking> rooms = bookingService.findAvailableRoomsByGuest(guest);

        assertEquals(1, rooms.size());
        assertEquals(acceptedBooking(bookingRequest), rooms.iterator().next());
    }

}