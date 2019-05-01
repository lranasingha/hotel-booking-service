package com.techtest.hotelbooking.room;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class SimpleRoomStore implements RoomStore {
    private final Map<Number, Room> roomsMap = new HashMap<>();

    @Override
    public Optional<Room> get(final int roomNumber) {
        return Optional.ofNullable(roomsMap.get(roomNumber));
    }

    @Override
    public void store(final Room room) {
        roomsMap.put(room.number(), room);
    }

    @Override
    public void remove(final int roomNumber) {
        roomsMap.remove(roomNumber);
    }

    @Override
    public Collection<Room> rooms() {
        return roomsMap.values();
    }
}
