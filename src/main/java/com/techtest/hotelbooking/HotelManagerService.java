package com.techtest.hotelbooking;

import com.techtest.hotelbooking.room.HotelRoomHandlerService;
import com.techtest.hotelbooking.room.Room;
import com.techtest.hotelbooking.room.RoomHandlerService;
import com.techtest.hotelbooking.room.SimpleRoomStore;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

public class HotelManagerService implements BookingService, RoomHandlerService {
    private final Object LOCK = new Object();

    public static HotelManagerService createNewHotelManagerService() {
        HotelRoomHandlerService roomHandlerService = new HotelRoomHandlerService(new SimpleRoomStore());
        HotelBookingService bookingService = new HotelBookingService(new SimpleBookingStore(), roomHandlerService);
        return new HotelManagerService(roomHandlerService, bookingService);
    }

    private final RoomHandlerService roomHandlerServiceDelegate;
    private final BookingService bookingServiceDelegate;

    HotelManagerService(final RoomHandlerService roomHandlerServiceDelegate,
                        final BookingService bookingServiceDelegate) {
        this.roomHandlerServiceDelegate = roomHandlerServiceDelegate;
        this.bookingServiceDelegate = bookingServiceDelegate;
    }

    @Override
    public void addRoom(final Room room) {
        synchronized (LOCK) {
            roomHandlerServiceDelegate.addRoom(room);
        }
    }

    @Override
    public void removeRoom(final int roomNumber) {
        synchronized (LOCK) {
            roomHandlerServiceDelegate.removeRoom(roomNumber);
        }
    }

    @Override
    public Optional<Room> makeUnavailable(final int roomNumber) {
        return doTaskSafely(roomNumber, roomHandlerServiceDelegate::makeUnavailable);
    }

    @Override
    public Booking makeBooking(final BookingRequest bookingRequest) {
        return doTaskSafely(bookingRequest, bookingServiceDelegate::makeBooking);
    }

    @Override
    public Collection<Room> findAvailableRoomsByDate(final LocalDate date) {
        return doTaskSafely(date, bookingServiceDelegate::findAvailableRoomsByDate);
    }

    @Override
    public Collection<Booking> findAvailableRoomsByGuest(final Guest guest) {
        return doTaskSafely(guest, bookingServiceDelegate::findAvailableRoomsByGuest);
    }

    private <T, R> R doTaskSafely(final T input, final Function<T, R> task) {
        synchronized (LOCK) {
            return task.apply(input);
        }
    }
}
