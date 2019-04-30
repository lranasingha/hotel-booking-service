package com.techtest.hotel;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toUnmodifiableList;

public class HotelRoomHandlingService implements RoomHandlingService {
    private final RoomStore roomStore;

    public HotelRoomHandlingService(final RoomStore roomStore) {
        this.roomStore = roomStore;
    }

    @Override
    public void addRoom(final Room room) {
        roomStore.store(room);
    }

    @Override
    public void removeRoom(final int roomNumber) {
        roomStore.remove(roomNumber);
    }

    @Override
    public void bookRoom(final int roomNumber) {
        Optional<Room> room = roomStore.get(roomNumber);
        room.ifPresent(Room::makeUnavailable);
    }

    @Override
    public Collection<Room> findRoomsBy(final Predicate<Room> condition) {
        return roomStore
                .rooms()
                .stream()
                .filter(condition)
                .collect(toUnmodifiableList());
    }

}
