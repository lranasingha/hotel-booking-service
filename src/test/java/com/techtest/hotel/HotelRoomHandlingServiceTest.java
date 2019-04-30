package com.techtest.hotel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.techtest.hotel.Room.Availability.AVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class HotelRoomHandlingServiceTest {

    private RoomStore roomStore;
    private HotelRoomHandlingService hotelRoomAvailabilityService;

    @BeforeEach
    void setUp() {
        roomStore = mock(RoomStore.class);
        hotelRoomAvailabilityService = new HotelRoomHandlingService(roomStore);
    }

    @Test
    void can_add_a_room() {
        var roomNumber = 1;
        Room room = new Room(roomNumber);
        when(roomStore.get(eq(roomNumber))).thenReturn(Optional.of(room));

        hotelRoomAvailabilityService.addRoom(room);

        assertEquals(room, roomStore.get(roomNumber).get());
    }

    @Test
    void store_the_newly_added_room() {
        Room room = new Room(1);

        hotelRoomAvailabilityService.addRoom(room);

        verify(roomStore).store(eq(room));
    }

    @Test
    void can_remove_a_room() {
        var roomNumber = 1;
        Room room = new Room(roomNumber);
        when(roomStore.get(eq(room.number()))).thenReturn(Optional.empty());

        hotelRoomAvailabilityService.addRoom(room);
        hotelRoomAvailabilityService.removeRoom(roomNumber);

        verify(roomStore).remove(roomNumber);
        assertEquals(Optional.empty(), roomStore.get(roomNumber));
    }

    @Test
    void room_is_available_once_it_added() {
        var roomNumber = 1;
        Room room = new Room(roomNumber);
        when(roomStore.get(eq(room.number()))).thenReturn(Optional.of(room));

        hotelRoomAvailabilityService.addRoom(room);

        assertEquals(roomStore.get(roomNumber).get().availability(), AVAILABLE);
    }
}