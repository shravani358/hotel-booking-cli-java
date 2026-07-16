# Architecture & Project Explanation

## What is a Hotel Room Booking Console App?

**Simple explanation:** It's a text-based program you run in a terminal
that lets you act like a hotel front-desk clerk — see which rooms are
free, take a guest's details, book a room for a date range, print a
bill, and cancel a booking if needed.

**Technical explanation:** It's a layered Java console application that
models hotel entities (`Room`, `Guest`, `Booking`) as plain objects,
applies business rules (availability, pricing, cancellation) in a
service layer, persists state to a flat file through a repository layer,
and exposes everything through a single menu-driven `Main` class.

## What problem does it solve?

Manually tracking room availability, guest information, and bills on
paper or in a spreadsheet is slow and error-prone. This app automates
availability checks, prevents double-booking a room, calculates bills
consistently, and keeps a permanent record of every booking.

## How real hotels manage this

Hotels track: room **inventory** (which rooms exist and their type/rate),
**availability** (is a room free right now), **guest records** (who is
staying), **bookings** (which guest is in which room, for which dates),
**billing** (nights × rate), and **cancellations** (releasing a room back
to availability). This project implements a simplified version of every
one of those pieces.

## Why this is a good Java OOP project

- Multiple related entities (`Room`, `Guest`, `Booking`) map naturally to
  classes.
- Real business rules (availability, capacity limits, cancellation)
  give you logic worth testing and explaining in an interview.
- It touches most core Java skills at once: collections, enums, file
  I/O, `java.time`, and input validation — without needing a database or
  framework.

## Data Flow

```
Input                Processing                        Output
-----                ----------                        ------
guest name      ->   validate input               ->   booking confirmation
contact number  ->   check room availability       ->   bill summary
room type       ->   calculate number of nights    ->   available room list
check-in date   ->   calculate booking amount       ->   cancellation confirmation
check-out date  ->   generate booking ID            ->   booking history
guest count     ->   update room status
                ->    store booking details (file)
```

## Class Relationship

```
                +---------------------+
                |        Main         |   console menu, input, navigation
                +----------+----------+
                           |
                           v
                +----------+----------+
                |     HotelService     |   business rules
                +----------+----------+
                    |                |
                    v                v
          +---------+------+   +-----+--------+
          |  FileManager    |   | Room / Guest |
          |  (repository)   |   | Booking      |
          +---------+------+   +--------------+
                    |
                    v
          data/bookings.txt

  DateValidator and IDGenerator (utility) are used by
  Main and HotelService respectively.
```

## Booking Workflow

```
User Menu
   |
   v
Search Available Rooms
   |
   v
Select Room
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
Confirm Booking
   |
   v
Save Booking Record (file)
```
