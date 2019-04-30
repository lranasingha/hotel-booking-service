package com.techtest.hotel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.techtest.hotel.Room.Availability.AVAILABLE;
import static com.techtest.hotel.Room.Availability.UNAVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class HotelRoomHandlingServiceTest {

    private RoomStore roomStore;
    private HotelRoomHandlingService roomHandlingService;

    @BeforeEach
    void setUp() {
        roomStore = mock(RoomStore.class);
        roomHandlingService = new HotelRoomHandlingService(roomStore);
    }

    @Test
    void can_add_a_room() {
        var roomNumber = 1;
        var room = new Room(roomNumber);
        when(roomStore.get(eq(roomNumber))).thenReturn(Optional.of(room));

        roomHandlingService.addRoom(room);

        assertEquals(room, roomStore.get(roomNumber).get());
    }

    @Test
    void store_the_newly_added_room() {
        var room = new Room(1);

        roomHandlingService.addRoom(room);

        verify(roomStore).store(eq(room));
    }

    @Test
    void can_remove_a_room() {
        var roomNumber = 1;
        var room = new Room(roomNumber);
        when(roomStore.get(eq(room.number()))).thenReturn(Optional.empty());

        roomHandlingService.addRoom(room);
        roomHandlingService.removeRoom(roomNumber);

        verify(roomStore).remove(roomNumber);
        assertEquals(Optional.empty(), roomStore.get(roomNumber));
    }

    @Test
    void room_is_available_once_it_added() {
        var roomNumber = 1;
        var room = new Room(roomNumber);
        when(roomStore.get(eq(room.number()))).thenReturn(Optional.of(room));

        roomHandlingService.addRoom(room);

        assertEquals(roomStore.get(roomNumber).get().availability(), AVAILABLE);
    }

    @Test
    void can_book_a_room() {
        var roomNumber = 1;
        var room = new Room(roomNumber);
        when(roomStore.get(eq(room.number()))).thenReturn(Optional.of(room));
        roomHandlingService.addRoom(room);

        roomHandlingService.bookRoom(roomNumber);

        assertEquals(roomStore.get(roomNumber).get().availability(), UNAVAILABLE);
    }

    @Test
    void find_rooms_by_a_given_condition() {
        var roomOne = new Room(1);
        var roomTwo = new Room(2);
        when(roomStore.rooms()).thenReturn(List.of(roomOne, roomTwo));

        roomHandlingService.addRoom(roomOne);

        Collection<Room> rooms = roomHandlingService.findRoomsBy(room -> room.number() == 2);

        assertEquals(new Room(2), rooms.iterator().next());
    }
}