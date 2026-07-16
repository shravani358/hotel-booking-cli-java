# Interview Preparation

## 1. Explain your project.

I built a Java console application for managing hotel room bookings.
Users can view available rooms, search by room type, enter guest
details, book a room, see a calculated bill, view booking details, and
cancel a booking. The project is split into model, service, repository,
and utility layers, and it stores booking records in a text file so data
survives a restart.

## 2. What problem does this project solve?

It replaces manual tracking of room availability and bookings with an
automated flow: it checks whether a room is free, records guest and
booking details, computes the bill, and updates room status when a
booking is made or cancelled.

## 3. Which Java concepts did you use?

Classes and objects, encapsulation (private fields with getters/setters),
enums (`RoomType`, `BookingStatus`), collections (`ArrayList`), exception
handling (`try/catch` around parsing and file I/O), file handling
(`BufferedReader`/`BufferedWriter`), date handling (`java.time.LocalDate`,
`ChronoUnit`), loops, conditionals, and input validation.

## 4. What classes did you create, and why?

`Room`, `Guest`, and `Booking` are model classes that hold data only.
`HotelService` holds all business logic (availability, billing,
cancellation). `FileManager` handles reading and writing the booking
file. `DateValidator` and `IDGenerator` are small utility helpers. `Main`
only handles the console menu and user input. Separating these
responsibilities keeps each class focused and easy to test or change.

## 5. How do you check room availability?

Every `Room` has a boolean `available` field. When a booking is created,
that room's `available` flag is set to `false`. When the booking is
cancelled, it's set back to `true`. The "search" and "display available
rooms" features simply filter the room list by this flag.

## 6. How is the total bill calculated?

`HotelService.calculateBill()` multiplies the room's price per night by
the number of nights, where the number of nights is computed from the
check-in and check-out dates using `ChronoUnit.DAYS.between(...)`.

## 7. How did you generate booking IDs?

`IDGenerator` keeps an internal counter starting at 1000 and returns
`"BK" + counter.incrementAndGet()` for each new booking, giving IDs like
`BK1001`, `BK1002`, and so on. On startup, the counter is initialized
from the highest booking number already saved in the file, so restarting
the app never produces a duplicate ID.

## 8. How did you handle invalid inputs?

Every user input goes through a validation loop: menu choices are
parsed with `try/catch` around `Integer.parseInt`, phone numbers and
emails are checked with regular expressions, dates are parsed and
validated with `DateValidator`, and guest counts are checked against the
room's maximum capacity. If validation fails, the user sees a message
and is asked again — the program never crashes on bad input.

## 9. What challenges did you face?

Keeping the in-memory room/booking state consistent with the file on
disk was the trickiest part — for example, making sure that when the
app restarts and reloads bookings from the file, rooms with an active
booking are correctly marked unavailable again, and that booking IDs
don't collide after a restart.

## 10. How can this project be improved further?

It could be extended with JDBC and MySQL for real persistent storage,
admin/customer login roles, payment simulation, discount codes, a GUI
built with Swing or JavaFX, and support for overlapping date-range
availability per room instead of a simple booked/available flag.
