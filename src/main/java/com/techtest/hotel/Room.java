package com.techtest.hotel;

public class Room {
    private final int roomNumber;
    private Availability availability;

    public Room(final int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Availability availability() {
        return availability;
    }

    void makeAvailable() {
        this.availability = Availability.AVAILABLE;
    }

    public int number() {
        return roomNumber;
    }

    public enum Availability {
        AVAILABLE
    }

}