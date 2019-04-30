package com.techtest.hotel;

import java.util.Collection;
import java.util.function.Predicate;

public interface BookingStore {
    Collection<Booking> findBookingsBy(final Predicate<Booking> and);
}
