package com.techtest.hotelbooking;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toUnmodifiableList;

public final class SimpleBookingStore implements BookingStore {
    private final Set<Booking> bookings = new HashSet<>();

    @Override
    public Collection<Booking> findBookingsBy(final Predicate<Booking> condition) {
        return bookings
                .stream()
                .filter(condition)
                .collect(toUnmodifiableList());
    }

    @Override
    public void store(final Booking booking) {
        bookings.add(booking);
    }
}
