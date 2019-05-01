package com.techtest.hotelbooking;

import com.techtest.hotelbooking.room.Room;
import com.techtest.hotelbooking.room.RoomHandlingService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

import static com.techtest.hotelbooking.Booking.Status.ACCEPTED;

public class BookingService {
    private final BookingStore bookingStore;
    private final RoomHandlingService roomHandlingService;

    public BookingService(final BookingStore bookingStore,
                          final RoomHandlingService roomHandlingService) {
        this.bookingStore = bookingStore;
        this.roomHandlingService = roomHandlingService;
    }

    public Booking makeBooking(final BookingRequest bookingRequest) {
        Optional<Room> bookedRoom = roomHandlingService.makeUnavailable(bookingRequest.roomNumber());

        Booking booking;
        if (bookedRoom.isPresent()) {
            booking = Booking.acceptedBooking(bookingRequest);
        } else {
            booking = Booking.failedBooking(bookingRequest);
        }

        bookingStore.store(booking);
        return booking;
    }

    public Collection<Room> findAvailableRoomsByDate(final LocalDate date) {

        Collection<Booking> activeBookingsForTheDate = bookingStore
                .findBookingsBy(activeBookingsForTheDate(date));

        return roomHandlingService.findRoomsBy(notAlreadyBooked(activeBookingsForTheDate));
    }

    public Collection<Booking> findAvailableRoomsByGuest(final Guest guest) {
        return bookingStore.findBookingsBy(byTheGuest(guest));
    }

    private static Predicate<Booking> byTheGuest(final Guest guest) {
        return booking -> booking.guest().equals(guest);
    }

    private static Predicate<Room> notAlreadyBooked(final Collection<Booking> activeBookingsForTheDate) {
        return room -> activeBookingsForTheDate
                .stream()
                .noneMatch(b -> b.roomNumber() == room.number());
    }

    private static Predicate<Booking> activeBookingsForTheDate(final LocalDate date) {
        return ((Predicate<Booking>) booking -> booking.date().equals(date))
                .and(booking -> booking.status() == ACCEPTED);
    }
}
