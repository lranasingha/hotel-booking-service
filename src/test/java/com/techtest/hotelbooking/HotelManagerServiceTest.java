package com.techtest.hotelbooking;

import com.techtest.hotelbooking.room.HotelRoomHandlerService;
import com.techtest.hotelbooking.room.Room;
import com.techtest.hotelbooking.room.SimpleRoomStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toCollection;
import static org.junit.jupiter.api.Assertions.assertEquals;


class HotelManagerServiceTest {

    private HotelManagerService hotelManagerService;
    private SimpleRoomStore roomStore;

    @BeforeEach
    void setUp() {
        roomStore = new SimpleRoomStore();
        HotelRoomHandlerService roomHandlerService = new HotelRoomHandlerService(roomStore);
        HotelBookingService bookingService = new HotelBookingService(new SimpleBookingStore(), roomHandlerService);
        hotelManagerService = new HotelManagerService(roomHandlerService, bookingService);
    }

    @Test
    void can_multiple_workers_handle_rooms() throws InterruptedException {
        var roomsToAdd = 19;
        var roomsToRemove = 11;
        var workers = 8;

        Collection<Room> rooms = manageRooms(roomsToAdd, roomsToRemove, workers);

        assertEquals(8, rooms.size());
    }

    @Test
    void make_bookings_while_doing_other_operations() throws InterruptedException {
        var roomsToAdd = 10;
        var roomsToRemove = 0;
        var workers = 1;
        Collection<Room> rooms = manageRooms(roomsToAdd, roomsToRemove, workers);
        assertEquals(10, rooms.size());

        workers = 8;
        Guest guest = new Guest("A", "B");
        makeBookingsWhileDoingOtherTasks(10, workers, guest, LocalDate.now());

        assertEquals(10, hotelManagerService.findAvailableRoomsByGuest(guest).size());
    }

    private void makeBookingsWhileDoingOtherTasks(final int bookings,
                                                  final int workers,
                                                  final Guest guest,
                                                  final LocalDate date) throws InterruptedException {
        var executorService = Executors.newFixedThreadPool(workers);
        var latch = new CountDownLatch(bookings * 2);

        List<Runnable> tasks = IntStream
                .rangeClosed(1, bookings)
                .mapToObj(i -> (Runnable) () -> {
                    try {
                        hotelManagerService.makeBooking(new BookingRequest(guest, date, i));
                    } finally {
                        latch.countDown();
                    }

                })
                .collect(toCollection(ArrayList::new));

        IntStream
                .rangeClosed(1, bookings)
                .mapToObj(i -> (Runnable) () -> {
                    try {
                        hotelManagerService.addRoom(new Room((i + 10)));
                        hotelManagerService.findAvailableRoomsByDate(date);
                    } finally {
                        latch.countDown();
                    }

                })
                .forEach(tasks::add);

        Collections.shuffle(tasks);

        tasks.forEach(executorService::submit);

        latch.await();
        executorService.shutdown();
    }

    private Collection<Room> manageRooms(final int roomsToAdd,
                                         final int roomsToRemove,
                                         final int workers) throws InterruptedException {
        var executorService = Executors.newFixedThreadPool(workers);
        var latch = new CountDownLatch(roomsToAdd + roomsToRemove);
        List<Runnable> addTasks = IntStream
                .rangeClosed(1, roomsToAdd)
                .mapToObj(i -> (Runnable) () -> {
                    try {
                        hotelManagerService.addRoom(new Room(i));
                    } finally {
                        latch.countDown();
                    }

                })
                .collect(toCollection(ArrayList::new));

        List<Runnable> removeTasks = IntStream
                .rangeClosed(1, roomsToRemove)
                .mapToObj(i -> (Runnable) () -> {
                    try {
                        hotelManagerService.removeRoom(i);
                    } finally {
                        latch.countDown();
                    }
                }).collect(Collectors.toList());


        var allTasks = new ArrayList<Runnable>();
        allTasks.addAll(addTasks);
        allTasks.addAll(removeTasks);

        allTasks.forEach(executorService::submit);

        latch.await();

        executorService.shutdown();

        return roomStore.rooms();
    }

}