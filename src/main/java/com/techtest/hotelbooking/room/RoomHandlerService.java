package com.techtest.hotelbooking.room;

import java.util.Optional;

public interface RoomHandlerService {

    void addRoom(Room room);

    void removeRoom(int roomNumber);

    Optional<Room> makeUnavailable(int roomNumber);
}
