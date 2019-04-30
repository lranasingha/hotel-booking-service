package com.techtest.hotel;

import java.util.Objects;

public class Room {
    public enum Availability {
        AVAILABLE,
        UNAVAILABLE
    }

    private final int roomNumber;

    private Availability availability;

    public Room(final int roomNumber) {
        this.roomNumber = roomNumber;
        availability = Availability.AVAILABLE;
    }

    public Availability availability() {
        return availability;
    }

    public int number() {
        return roomNumber;
    }

    void makeUnavailable() {
        availability = Availability.UNAVAILABLE;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber &&
                availability == room.availability;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, availability);
    }
}
