package com.techtest.hotelbooking.room;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toUnmodifiableList;

public final class HotelRoomHandlerService implements RoomHandlerService {
    private final RoomStore roomStore;

    public HotelRoomHandlerService(final RoomStore roomStore) {
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
    public Optional<Room> makeUnavailable(final int roomNumber) {
        Optional<Room> room = roomStore.get(roomNumber);
        room.ifPresent(Room::makeUnavailable);
        return room;
    }

    public Collection<Room> findRoomsBy(final Predicate<Room> condition) {
        return roomStore
                .rooms()
                .stream()
                .filter(condition)
                .collect(toUnmodifiableList());
    }

}
