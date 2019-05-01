# A simple hotel booking manager

This service provides a Java API to 
* add or remove rooms
* get all rooms
* make a booking for a given room
* find all available rooms on a given date
* find all bookings by a guest

### How to build & test?
This is a `Java 11` application which uses `Gradle 5+` for building. Therefor, **you must have `Java 11` available** before starting the build.

Please go to the project root directory (i.e `hotel-booking-service`) and use following command to build and run all tests

`./gradlew clean test`

### How to use ?
The public Java API has been provided in `HotelManagerService`. This is thread-safe and provide room handling and booking operations. Please use `HotelManagerService.createNewHotelManagerService()` to create an instance of this class.

### Design decisions
* The functionality is implemented in `HotelBookingService` & `HotelRoomHandlerService` classes. The `HotelManagerService` is the delegated thread-safe version of those classes. This helps separating multi-threaded and single-threaded code cleanly which is one of the best ways to implement multi-threaded code.
* The code has been written following TDD practices.
* The thread synchronisation has been achieved using a simple monitor embedded in the `HotelManagerService`. There are other options such as Reentrant Locks. However, they are too broad for this particular task. Simplicity matters.

#### Further Improvements
This is a minimal viable version of the application. Therefore, the sad paths of the system haven't been fully covered. More tests need to be done. 
