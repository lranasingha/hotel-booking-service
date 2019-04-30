package com.techtest.hotel;

import java.util.Objects;

public final class Guest {
    private final String firstName;
    private final String lastName;

    public Guest(final String firstName, final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return Objects.equals(firstName, guest.firstName) &&
                Objects.equals(lastName, guest.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
