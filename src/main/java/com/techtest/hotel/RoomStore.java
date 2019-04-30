package com.techtest.hotel;

import java.util.Optional;

public interface RoomStore {
    Optional<Room> get(int roomNumber);

    void store(Room room);

    void remove(int roomNumber);
}