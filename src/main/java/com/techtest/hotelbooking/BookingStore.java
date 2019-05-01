package com.techtest.hotelbooking;

import java.util.Collection;
import java.util.function.Predicate;

public interface BookingStore {
    Collection<Booking> findBookingsBy(final Predicate<Booking> condition);

    void store(Booking booking);
}
