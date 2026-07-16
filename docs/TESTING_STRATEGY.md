# Testing Strategy

## Manual Test Cases

| # | Test Case | Steps | Expected Result |
|---|---|---|---|
| 1 | Valid room booking | Book a DOUBLE room with valid guest details and valid dates | Booking is confirmed, booking ID generated, room becomes unavailable |
| 2 | Unavailable room | Try to book a room that is already booked | Room does not appear in the available list; booking cannot proceed with that room |
| 3 | Invalid room type | Enter "5" or "abc" when choosing a room type | "Invalid choice" message, prompt repeats |
| 4 | Invalid date format | Enter "20-07-2026" instead of "2026-07-20" | "Invalid date format" message, prompt repeats |
| 5 | Checkout before check-in | Enter a check-out date equal to or before check-in | "Check-out date must be after the check-in date" message |
| 6 | Guest count above capacity | Enter more guests than the room's max capacity | "This room allows a maximum of N guest(s)" message |
| 7 | Booking cancellation | Cancel a CONFIRMED booking by ID | Status changes to CANCELLED, room becomes available again |
| 8 | Invalid booking ID | View or cancel a booking ID that doesn't exist | "No booking found with ID: ..." message |
| 9 | Empty guest name | Press Enter without typing a name | "Name cannot be empty" message, prompt repeats |
| 10 | File read/write | Delete `data/bookings.txt`, then run the app | App recreates the file automatically with no crash |

## How to Run These Tests

1. Compile and run the app (see the main README).
2. Walk through each row above in order, typing the described input at
   each prompt.
3. Confirm the console output matches the "Expected Result" column.
4. For test 10, close the app, delete `data/bookings.txt`, and restart —
   the app should recreate the file without any error.

## Optional JUnit Test Ideas

If you want to add automated tests later:

- `DateValidatorTest` — assert `parseDate` returns `null` for bad input,
  and returns a `LocalDate` for good input.
- `HotelServiceTest` — assert `createBooking` marks the room unavailable,
  and `cancelBooking` marks it available again.
- `IDGeneratorTest` — assert two calls to `nextBookingId()` never return
  the same value.
- `FileManagerTest` — save a booking, reload it, and assert the reloaded
  booking's fields match the original.
