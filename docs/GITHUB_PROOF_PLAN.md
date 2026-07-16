# 7-Day GitHub Proof-Building Plan

Spreading your commits over several days (instead of one big upload)
shows a realistic development history — which is exactly what this
project is meant to demonstrate on your GitHub profile.

## Day 1 — Project setup and class design
**Files to commit:** folder structure, empty package folders, `README.md`
(overview section only), `.gitignore`
**Commit message:** `Initial project setup with folder structure and README`
**Screenshots/proof:** project folder structure in Eclipse, empty
package explorer showing model/service/repository/utility/main

## Day 2 — Room and guest modules
**Files to commit:** `model/Room.java`, `model/RoomType.java`,
`model/Guest.java`
**Commit message:** `Add Room and Guest model classes with room types`
**Screenshots/proof:** the three files open in Eclipse, console output of
"Display Available Rooms" showing all 10 seeded rooms

## Day 3 — Availability and booking logic
**Files to commit:** `model/Booking.java`, `model/BookingStatus.java`,
`utility/DateValidator.java`, `utility/IDGenerator.java`,
`service/HotelService.java` (partial: availability + create booking)
**Commit message:** `Implement booking creation and availability checking`
**Screenshots/proof:** "Search Rooms by Type" output, a successful
"Book a Room" flow with a generated booking ID

## Day 4 — Billing and cancellation
**Files to commit:** `service/HotelService.java` (bill calculation,
cancellation), `main/Main.java` (booking + cancel flow)
**Commit message:** `Add bill calculation and booking cancellation`
**Screenshots/proof:** bill summary output after booking, cancellation
confirmation message, room becoming available again

## Day 5 — File handling and booking history
**Files to commit:** `repository/FileManager.java`, `data/bookings.txt`
(sample data), remaining `main/Main.java` (history view)
**Commit message:** `Add file-based storage and booking history`
**Screenshots/proof:** contents of `data/bookings.txt`, "View Booking
History" console output

## Day 6 — Testing and bug fixes
**Files to commit:** any bug fixes across `src/`, `docs/TESTING_STRATEGY.md`
**Commit message:** `Test edge cases and fix input validation bugs`
**Screenshots/proof:** console output for at least 3 test cases from
`docs/TESTING_STRATEGY.md` (e.g., invalid date, guest over capacity,
invalid booking ID)

## Day 7 — README and GitHub documentation
**Files to commit:** finished `README.md`, `docs/ARCHITECTURE.md`,
`docs/INTERVIEW_PREP.md`, `screenshots/` folder contents
**Commit message:** `Complete README, documentation, and screenshots`
**Screenshots/proof:** GitHub repository homepage showing the rendered
README, and the README preview itself

## Repository Details

- **Repository name:** `hotel-room-booking-console-app`
- **Description:** "A Java console application for hotel room booking —
  search rooms, manage guests, calculate bills, and track bookings with
  file-based storage."
- **Suggested tags/topics:** `java`, `oop`, `console-application`,
  `file-handling`, `beginner-project`, `hotel-management`, `dsa-practice`

## GitHub Hygiene

- Do not upload IDE-generated files (`.classpath`, `.project`,
  `.settings/`, `bin/`) — the included `.gitignore` already excludes these.
- Use the sample/seed data already in this project instead of any real
  personal or customer data.
- Keep commit messages short, specific, and in the imperative mood
  (e.g., "Add X", not "Added X" or "Adding X").
