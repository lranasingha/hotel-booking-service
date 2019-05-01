package com.techtest.hotelbooking;

import com.techtest.hotelbooking.room.Room;

import java.time.LocalDate;
import java.util.Collection;

public interface BookingService {
    Booking makeBooking(BookingRequest bookingRequest);

    Collection<Room> findAvailableRoomsByDate(LocalDate date);

    Collection<Booking> findAvailableRoomsByGuest(Guest guest);
}
