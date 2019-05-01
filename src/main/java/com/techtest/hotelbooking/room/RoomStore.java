package com.techtest.hotelbooking.room;

import java.util.Collection;
import java.util.Optional;

public interface RoomStore {
    Optional<Room> get(int roomNumber);

    void store(Room room);

    void remove(int roomNumber);

    Collection<Room> rooms();
}
