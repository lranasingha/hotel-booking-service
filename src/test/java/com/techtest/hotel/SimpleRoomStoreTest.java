package com.techtest.hotel;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class SimpleRoomStoreTest {

    private final SimpleRoomStore roomStore = new SimpleRoomStore();

    @Test
    void can_store_a_room() {
        roomStore.store(new Room(10));

        assertEquals(new Room(10), roomStore.get(10).get());
    }

    @Test
    void empty_when_no_room_found_for_room_number() {
        assertEquals(Optional.empty(), roomStore.get(100));
    }

    @Test
    void can_remove_a_room() {
        roomStore.store(new Room(10));

        assertEquals(Optional.of(new Room(10)), roomStore.get(10));

        roomStore.remove(10);

        assertEquals(Optional.empty(), roomStore.get(10));
    }
}