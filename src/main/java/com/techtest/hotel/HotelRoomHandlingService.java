package com.techtest.hotel;

public class HotelRoomHandlingService implements RoomHandlingService {
    private final RoomStore roomStore;

    public HotelRoomHandlingService(final RoomStore roomStore) {
        this.roomStore = roomStore;
    }

    @Override
    public void addRoom(final Room room) {
        room.makeAvailable();
        roomStore.store(room);
    }

    @Override
    public void removeRoom(final int roomNumber) {
        roomStore.remove(roomNumber);
    }
}
