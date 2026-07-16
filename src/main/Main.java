package main;

import model.Booking;
import model.BookingStatus;
import model.Guest;
import model.Room;
import model.RoomType;
import service.HotelService;
import utility.DateValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Main.java (Console entry point)
 *
 * Implements:
 *   - console menu
 *   - user input
 *   - menu navigation
 *   - error handling
 *
 * This class is only responsible for talking to the user. All business
 * rules live in HotelService, and all file operations live in FileManager.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final HotelService hotelService = new HotelService();

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("=================================================");
        System.out.println("   WELCOME TO THE HOTEL ROOM BOOKING CONSOLE APP  ");
        System.out.println("=================================================");

        while (running) {
            showMainMenu();
            int choice = readMenuChoice();

            switch (choice) {
                case 1:
                    displayAvailableRooms();
                    break;
                case 2:
                    searchRoomsByType();
                    break;
                case 3:
                    bookRoom();
                    break;
                case 4:
                    viewBookingDetails();
                    break;
                case 5:
                    cancelBooking();
                    break;
                case 6:
                    viewBookingHistory();
                    break;
                case 7:
                    running = false;
                    System.out.println("\nThank you for using the Hotel Room Booking Console App. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a number between 1 and 7.");
            }
        }
        scanner.close();
    }

    // ---------------- Menu ----------------
    private static void showMainMenu() {
        System.out.println("\n-------------------- MAIN MENU --------------------");
        System.out.println("1. Display Available Rooms");
        System.out.println("2. Search Rooms by Type");
        System.out.println("3. Book a Room");
        System.out.println("4. View Booking Details");
        System.out.println("5. Cancel Booking");
        System.out.println("6. View Booking History");
        System.out.println("7. Exit");
        System.out.println("-----------------------------------------------------");
        System.out.print("Enter your choice (1-7): ");
    }

    private static int readMenuChoice() {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1; // falls into the "default" case below -> friendly error message
        }
    }

    // ---------------- Feature: Display available rooms ----------------
    private static void displayAvailableRooms() {
        List<Room> available = hotelService.getAvailableRooms();
        System.out.println("\n--- AVAILABLE ROOMS ---");
        if (available.isEmpty()) {
            System.out.println("No rooms are currently available.");
            return;
        }
        printRoomTableHeader();
        for (Room r : available) {
            System.out.println(r);
        }
    }

    // ---------------- Feature: Search rooms by type ----------------
    private static void searchRoomsByType() {
        RoomType type = readRoomType();
        List<Room> results = hotelService.searchByType(type);

        System.out.println("\n--- SEARCH RESULTS: " + type + " ROOMS ---");
        if (results.isEmpty()) {
            System.out.println("No rooms found for this type.");
            return;
        }
        printRoomTableHeader();
        for (Room r : results) {
            System.out.println(r);
        }
    }

    // ---------------- Feature: Book a room ----------------
    private static void bookRoom() {
        RoomType type = readRoomType();
        List<Room> available = hotelService.searchByType(type);
        available.removeIf(r -> !r.isAvailable());

        if (available.isEmpty()) {
            System.out.println("Sorry, no " + type + " rooms are available right now.");
            return;
        }

        System.out.println("\nAvailable " + type + " rooms:");
        printRoomTableHeader();
        for (Room r : available) {
            System.out.println(r);
        }

        Room selectedRoom = null;
        while (selectedRoom == null) {
            System.out.print("\nEnter Room ID to book: ");
            String roomId = scanner.nextLine().trim();
            Room candidate = hotelService.findRoomById(roomId);
            if (candidate == null || !candidate.isAvailable() || candidate.getRoomType() != type) {
                System.out.println("Invalid Room ID. Please choose one from the list above.");
            } else {
                selectedRoom = candidate;
            }
        }

        // Enter guest details (with maximum guest limit validation)
        Guest guest = readGuestDetails(selectedRoom.getMaxCapacity());

        // Date validation
        LocalDate checkIn = readValidatedDate("Enter check-in date (yyyy-MM-dd): ", null);
        LocalDate checkOut = readValidatedDate("Enter check-out date (yyyy-MM-dd): ", checkIn);

        // Calculate bill + generate booking ID happen inside createBooking()
        Booking booking = hotelService.createBooking(guest, selectedRoom, checkIn, checkOut);

        System.out.println("\n*** BOOKING CONFIRMED ***");
        System.out.println(booking);
        System.out.println("Bill Summary: " + booking.getNumberOfNights() + " night(s) x Rs." +
                String.format("%.2f", selectedRoom.getPricePerNight()) + " = Rs." +
                String.format("%.2f", booking.getTotalAmount()));
    }

    // ---------------- Feature: View booking details ----------------
    private static void viewBookingDetails() {
        System.out.print("\nEnter Booking ID: ");
        String id = scanner.nextLine().trim();
        Booking booking = hotelService.findBookingById(id);

        if (booking == null) {
            System.out.println("No booking found with ID: " + id);
            return;
        }
        System.out.println(booking);
    }

    // ---------------- Feature: Cancel booking ----------------
    private static void cancelBooking() {
        System.out.print("\nEnter Booking ID to cancel: ");
        String id = scanner.nextLine().trim();
        Booking booking = hotelService.findBookingById(id);

        if (booking == null) {
            System.out.println("No booking found with ID: " + id);
            return;
        }
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            System.out.println("This booking is already cancelled.");
            return;
        }

        boolean cancelled = hotelService.cancelBooking(id);
        if (cancelled) {
            System.out.println("Booking " + id + " has been cancelled successfully.");
            System.out.println("Room " + booking.getRoom().getRoomId() + " is now available again.");
        } else {
            System.out.println("Could not cancel this booking.");
        }
    }

    // ---------------- Feature: Booking history ----------------
    private static void viewBookingHistory() {
        List<Booking> history = hotelService.getBookingHistory();
        System.out.println("\n--- BOOKING HISTORY ---");
        if (history.isEmpty()) {
            System.out.println("No bookings have been made yet.");
            return;
        }
        for (Booking b : history) {
            System.out.println(b);
        }
    }

    // ---------------- Helper: read guest details ----------------
    private static Guest readGuestDetails(int maxCapacity) {
        System.out.println("\n--- ENTER GUEST DETAILS ---");

        String name;
        while (true) {
            System.out.print("Guest Name: ");
            name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                break;
            }
            System.out.println("Name cannot be empty. Please try again.");
        }

        String phone;
        while (true) {
            System.out.print("Phone Number (10 digits): ");
            phone = scanner.nextLine().trim();
            if (phone.matches("\\d{10}")) {
                break;
            }
            System.out.println("Enter a valid 10-digit phone number.");
        }

        String email;
        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();
            if (email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
                break;
            }
            System.out.println("Enter a valid email address (e.g., name@example.com).");
        }

        System.out.print("ID Proof Number (optional, press Enter to skip): ");
        String idProof = scanner.nextLine().trim();

        // Maximum guest limit feature
        int guestCount;
        while (true) {
            System.out.print("Number of Guests (max " + maxCapacity + "): ");
            String input = scanner.nextLine().trim();
            try {
                guestCount = Integer.parseInt(input);
                if (guestCount < 1) {
                    System.out.println("Number of guests must be at least 1.");
                } else if (guestCount > maxCapacity) {
                    System.out.println("This room allows a maximum of " + maxCapacity + " guest(s).");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        return new Guest(name, phone, email, idProof);
    }

    // ---------------- Helper: read and validate room type ----------------
    private static RoomType readRoomType() {
        while (true) {
            System.out.println("\nRoom Types: 1. SINGLE  2. DOUBLE  3. DELUXE  4. SUITE");
            System.out.print("Choose a room type (1-4): ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    return RoomType.SINGLE;
                case "2":
                    return RoomType.DOUBLE;
                case "3":
                    return RoomType.DELUXE;
                case "4":
                    return RoomType.SUITE;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 4.");
            }
        }
    }

    // ---------------- Helper: read and validate a date ----------------
    private static LocalDate readValidatedDate(String prompt, LocalDate mustBeAfter) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            LocalDate date = DateValidator.parseDate(input);

            if (date == null) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd (e.g., 2026-07-20).");
                continue;
            }

            if (mustBeAfter == null) {
                // this call is validating the check-in date
                if (!DateValidator.isNotPastDate(date)) {
                    System.out.println("Check-in date cannot be in the past.");
                    continue;
                }
            } else {
                // this call is validating the check-out date
                if (!DateValidator.isCheckOutAfterCheckIn(mustBeAfter, date)) {
                    System.out.println("Check-out date must be after the check-in date.");
                    continue;
                }
            }
            return date;
        }
    }

    private static void printRoomTableHeader() {
        System.out.println("RoomID | Type    | Price/Night | Max Guests | Status");
        System.out.println("-----------------------------------------------------");
    }
}
