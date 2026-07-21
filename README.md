# Hotel Room Booking Console App

A Java console application that lets a user browse hotel rooms, search by
room type, enter guest details, book a room, calculate the total bill,
view booking details, cancel a booking, and review booking history — with
all data saved to a file so nothing is lost when the program closes.

## Problem Statement

Small hotels and property managers need a simple, reliable way to track
room inventory, guest bookings, availability, and billing without the
overhead of a full hotel management system. This project automates that
core workflow — checking availability, recording guest details, creating
bookings, generating bills, and handling cancellations — in a single,
easy-to-run console program.

## Industry Relevance

The same booking → billing → cancellation workflow used here is the
foundation of real Property Management Systems (PMS) used by hotels,
resorts, hostels, and booking platforms. Even as AI features are added to
modern booking software, a reliable backend that manages inventory,
guests, and billing correctly is still essential — which is exactly what
this project demonstrates.

## Tech Stack

This project follows **Option B (Recommended)** from the project's tech
stack options:

| Option | Stack | Chosen? |
|---|---|---|
| A - Easy | Core Java, ArrayList, console menu, in-memory data | No |
| **B - Recommended** | **Core Java, OOP, Collections, File handling, booking history, search & cancellation** | **✅ Yes** |
| C - Advanced | Java, JDBC, MySQL, admin/customer roles, GUI | No |

No database, frameworks, or build tools are used — only core Java
(Collections, `java.time`, `java.io`) as specified.

## Java Concepts Used

| Concept | Where it's used |
|---|---|
| Classes & Objects | `Room`, `Guest`, `Booking` model the real-world entities |
| Encapsulation | All fields are `private` with public getters/setters |
| Enums | `RoomType` (Single/Double/Deluxe/Suite), `BookingStatus` |
| Collections (`ArrayList`) | Storing rooms and bookings in `HotelService` |
| Loops & Conditions | Menu loop, input validation loops |
| Methods & Constructors | Every class is built around small, single-purpose methods |
| Exception Handling | `try/catch` around number parsing and file I/O |
| File Handling | `FileManager` reads/writes `data/bookings.txt` |
| Date Handling | `java.time.LocalDate` for check-in/check-out, `ChronoUnit` for nights |
| Input Validation | Name, phone, email, dates, guest count all validated before use |

## Features

**Mandatory**
- Show main menu
- Display available rooms
- Search rooms by type
- Book a room
- Enter guest details
- Calculate total bill
- Generate booking ID
- View booking details
- Cancel booking
- Exit safely

**Recommended (all implemented)**
- Room types: Single, Double, Deluxe, Suite
- Different room prices per type
- Booking history
- Date validation (no past check-in, check-out after check-in)
- Maximum guest limit (per room type capacity)
- Room status update (booked ↔ available)
- File-based data storage (`data/bookings.txt`)

## Architecture

```
Input                     Processing                       Output
-----                     ----------                       ------
guest name          ->    validate input             ->    booking confirmation
contact number      ->    check room availability    ->    bill summary
room type           ->    calculate nights           ->    available room list
check-in date        ->   calculate booking amount   ->    cancellation confirmation
check-out date       ->   generate booking ID        ->    booking history
number of guests     ->   update room status
                     ->    store booking details
```

**Booking flow**

```
Main Menu
   |
   v
Search Available Rooms  ---->  Select Room
                                    |
                                    v
                          Enter Guest Details
                                    |
                                    v
                            Create Booking
                                    |
                                    v
                            Calculate Bill
                                    |
                                    v
                          Confirm / Cancel Booking
                                    |
                                    v
                          Save Booking Record (file)
```

**Class relationship**

```
Main  ---uses--->  HotelService  ---uses--->  FileManager
                        |                          |
                        v                          v
                 Room, Guest, Booking        data/bookings.txt
                 (model classes)
DateValidator, IDGenerator (utility classes) are used by
Main and HotelService respectively.
```

## Folder Structure

```
Hotel-Room-Booking-Console-App/
│
├── src/
│   ├── model/         -> Room, RoomType, Guest, Booking, BookingStatus
│   ├── service/        -> HotelService (business logic)
│   ├── repository/     -> FileManager (file read/write)
│   ├── utility/         -> DateValidator, IDGenerator
│   └── main/            -> Main (console menu)
├── data/                -> bookings.txt (created/updated at runtime)
├── outputs/             -> place sample console output .txt files here
├── screenshots/          -> place your proof screenshots here
├── docs/                 -> extended docs (architecture, testing, interview prep)
├── README.md
└── .gitignore
```

| Folder / File | Purpose |
|---|---|
| `src/model/` | Plain data classes with no business logic |
| `src/service/` | All business rules (availability, billing, cancellation) |
| `src/repository/` | All file I/O, isolated from business logic |
| `src/utility/` | Small reusable helpers (dates, ID generation) |
| `src/main/` | The console menu and user interaction only |
| `data/` | Where `bookings.txt` is created automatically |
| `outputs/` | Saved console output for proof/GitHub |
| `screenshots/` | Screenshots for proof/GitHub |
| `docs/` | Extra documentation (see below) |

See also: [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md),
[`docs/TESTING_STRATEGY.md`](docs/TESTING_STRATEGY.md),
[`docs/INTERVIEW_PREP.md`](docs/INTERVIEW_PREP.md),
[`docs/GITHUB_PROOF_PLAN.md`](docs/GITHUB_PROOF_PLAN.md).

## How to Run (Eclipse)

1. Open Eclipse → `File > New > Java Project` → name it
   `Hotel-Room-Booking-Console-App` → Finish.
2. Right-click the `src` folder → `New > Package`, and create these 5
   packages: `model`, `service`, `repository`, `utility`, `main`.
3. Copy each `.java` file from this project into the matching package
   folder in the Package Explorer (drag-and-drop works, or copy the file
   into the actual folder on disk and refresh in Eclipse with F5).
4. Right-click `src/main/Main.java` → `Run As > Java Application`.
5. Use the Console panel at the bottom to interact with the menu.

Full detailed steps are in the chat guide provided alongside this
project.

## Sample Menu

```
-------------------- MAIN MENU --------------------
1. Display Available Rooms
2. Search Rooms by Type
3. Book a Room
4. View Booking Details
5. Cancel Booking
6. View Booking History
7. Exit
-----------------------------------------------------
Enter your choice (1-7):
```

## Sample Booking Output

```
*** BOOKING CONFIRMED ***

--------------------------------------------
Booking ID   : BK1001
Guest        : Rahul Sharma (9876543210)
Room         : D201 - DOUBLE
Check-In     : 2026-07-20
Check-Out    : 2026-07-22
Nights       : 2
Total Amount : Rs.5000.00
Status       : CONFIRMED
--------------------------------------------
Bill Summary: 2 night(s) x Rs.2500.00 = Rs.5000.00
```

## Limitations

- Single-file, in-app text storage (not a real database) — fine for a
  student/portfolio project, not for production use.
- No admin/customer login (that is listed as an "Optional" feature in
  the original spec, not built here).
- One active booking per room at a time (no multi-date-range calendar).

## Future Improvements

- JDBC + MySQL integration for persistent, multi-user storage
- Admin and customer login roles
- Payment simulation and discount codes
- GUI using Swing or JavaFX
- Overlapping date-range booking support per room

## Learning Outcomes

- Practiced OOP design: encapsulation, enums, and separating concerns
  into model / service / repository / utility / main layers
- Practiced Java file I/O for persistent storage
- Practiced `java.time` for date parsing and validation
- Practiced defensive input validation in a console UI
- Practiced structuring a project for a clean GitHub portfolio entry

## Author

**Name:** *Shravani Margal*
**Course:** *Electronic & Telecommunication engineering*
**GitHub:** https://github.com/shravani358/To-Do-List-Manager-Local-Storage
**LinkedIn:** *https://www.linkedin.com/in/shravani-margal-80a520316?utm_source=share_via&utm_content=profile&utm_medium=member_android*

Project submitted as a Java course project and portfolio proof of work.

