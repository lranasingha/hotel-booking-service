package com.techtest.hotel;

import java.util.Collection;
import java.util.function.Predicate;

public interface RoomHandlingService {

    void addRoom(Room room);

    void removeRoom(int roomNumber);

    void bookRoom(int roomNumber);

    Collection<Room> findRoomsBy(Predicate<Room> condition);
}
