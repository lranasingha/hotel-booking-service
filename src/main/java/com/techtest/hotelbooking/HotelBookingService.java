package com.techtest.hotelbooking;

import com.techtest.hotelbooking.room.HotelRoomHandlerService;
import com.techtest.hotelbooking.room.Room;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

import static com.techtest.hotelbooking.Booking.Status.ACCEPTED;

final class HotelBookingService implements BookingService {
    private final BookingStore bookingStore;
    private final HotelRoomHandlerService hotelRoomHandlerService;

    public HotelBookingService(final BookingStore bookingStore,
                               final HotelRoomHandlerService hotelRoomHandlerService) {
        this.bookingStore = bookingStore;
        this.hotelRoomHandlerService = hotelRoomHandlerService;
    }

    @Override
    public Booking makeBooking(final BookingRequest bookingRequest) {
        Optional<Room> bookedRoom = hotelRoomHandlerService.makeUnavailable(bookingRequest.roomNumber());

        Booking booking;
        if (bookedRoom.isPresent()) {
            booking = Booking.acceptedBooking(bookingRequest);
        } else {
            booking = Booking.failedBooking(bookingRequest);
        }

        bookingStore.store(booking);
        return booking;
    }

    @Override
    public Collection<Room> findAvailableRoomsByDate(final LocalDate date) {

        Collection<Booking> activeBookingsForTheDate = bookingStore
                .findBookingsBy(activeBookingsForTheDate(date));

        return hotelRoomHandlerService.findRoomsBy(notAlreadyBooked(activeBookingsForTheDate));
    }

    @Override
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
