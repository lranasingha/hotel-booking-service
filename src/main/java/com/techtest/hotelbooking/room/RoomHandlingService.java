package com.techtest.hotelbooking.room;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public interface RoomHandlingService {

    void addRoom(Room room);

    void removeRoom(int roomNumber);

    Optional<Room> makeUnavailable(int roomNumber);

    Collection<Room> findRoomsBy(Predicate<Room> condition);
}
