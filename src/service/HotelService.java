package service;

import model.Booking;
import model.BookingStatus;
import model.Guest;
import model.Room;
import model.RoomType;
import repository.FileManager;
import utility.IDGenerator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * HotelService.java (Service)
 *
 * Core business logic layer. Implements:
 *   - list available rooms
 *   - search rooms
 *   - create booking
 *   - calculate bill
 *   - cancel booking
 *   - view booking
 *
 * Main.java only talks to this class - it never touches FileManager or the
 * raw room/booking lists directly. This keeps the console layer thin and
 * the business rules centralized in one place.
 */
public class HotelService {

    private List<Room> rooms;
    private List<Booking> bookings;
    private FileManager fileManager;

    public HotelService() {
        this.rooms = createRoomInventory();
        this.fileManager = new FileManager();
        this.bookings = fileManager.loadBookings(rooms);
        syncRoomAvailabilityWithLoadedBookings();
        initializeBookingIdCounter();
    }

    /** Phase 3: Room data creation - starting inventory of the hotel. */
    private List<Room> createRoomInventory() {
        List<Room> list = new ArrayList<>();
        list.add(new Room("S101", RoomType.SINGLE, RoomType.SINGLE.getDefaultPrice(), RoomType.SINGLE.getMaxCapacity()));
        list.add(new Room("S102", RoomType.SINGLE, RoomType.SINGLE.getDefaultPrice(), RoomType.SINGLE.getMaxCapacity()));
        list.add(new Room("S103", RoomType.SINGLE, RoomType.SINGLE.getDefaultPrice(), RoomType.SINGLE.getMaxCapacity()));
        list.add(new Room("D201", RoomType.DOUBLE, RoomType.DOUBLE.getDefaultPrice(), RoomType.DOUBLE.getMaxCapacity()));
        list.add(new Room("D202", RoomType.DOUBLE, RoomType.DOUBLE.getDefaultPrice(), RoomType.DOUBLE.getMaxCapacity()));
        list.add(new Room("D203", RoomType.DOUBLE, RoomType.DOUBLE.getDefaultPrice(), RoomType.DOUBLE.getMaxCapacity()));
        list.add(new Room("DX301", RoomType.DELUXE, RoomType.DELUXE.getDefaultPrice(), RoomType.DELUXE.getMaxCapacity()));
        list.add(new Room("DX302", RoomType.DELUXE, RoomType.DELUXE.getDefaultPrice(), RoomType.DELUXE.getMaxCapacity()));
        list.add(new Room("SU401", RoomType.SUITE, RoomType.SUITE.getDefaultPrice(), RoomType.SUITE.getMaxCapacity()));
        list.add(new Room("SU402", RoomType.SUITE, RoomType.SUITE.getDefaultPrice(), RoomType.SUITE.getMaxCapacity()));
        return list;
    }

    /** After loading saved bookings from file, mark their rooms as unavailable again. */
    private void syncRoomAvailabilityWithLoadedBookings() {
        for (Booking b : bookings) {
            if (b.getStatus() == BookingStatus.CONFIRMED) {
                b.getRoom().setAvailable(false);
            }
        }
    }

    /** Makes sure new booking IDs continue after the highest ID already saved on disk. */
    private void initializeBookingIdCounter() {
        int highest = 1000;
        for (Booking b : bookings) {
            String numericPart = b.getBookingId().replaceAll("[^0-9]", "");
            if (!numericPart.isEmpty()) {
                int value = Integer.parseInt(numericPart);
                if (value > highest) {
                    highest = value;
                }
            }
        }
        IDGenerator.initializeFrom(highest);
    }

    // ---------------- Display available rooms ----------------
    public List<Room> getAvailableRooms() {
        List<Room> available = new ArrayList<>();
        for (Room r : rooms) {
            if (r.isAvailable()) {
                available.add(r);
            }
        }
        return available;
    }

    // ---------------- Search rooms by type ----------------
    public List<Room> searchByType(RoomType type) {
        List<Room> matched = new ArrayList<>();
        for (Room r : rooms) {
            if (r.getRoomType() == type) {
                matched.add(r);
            }
        }
        return matched;
    }

    public Room findRoomById(String roomId) {
        for (Room r : rooms) {
            if (r.getRoomId().equalsIgnoreCase(roomId)) {
                return r;
            }
        }
        return null;
    }

    // ---------------- Bill calculation ----------------
    public int calculateNights(LocalDate checkIn, LocalDate checkOut) {
        return (int) ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    public double calculateBill(Room room, int nights) {
        return room.getPricePerNight() * nights;
    }

    // ---------------- Create booking ----------------
    public Booking createBooking(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) {
        int nights = calculateNights(checkIn, checkOut);
        double total = calculateBill(room, nights);
        String bookingId = IDGenerator.nextBookingId();

        Booking booking = new Booking(bookingId, guest, room, checkIn, checkOut, nights, total, BookingStatus.CONFIRMED);

        room.setAvailable(false);      // room status update
        bookings.add(booking);
        fileManager.saveBooking(booking); // file-based storage

        return booking;
    }

    // ---------------- View booking ----------------
    public Booking findBookingById(String bookingId) {
        for (Booking b : bookings) {
            if (b.getBookingId().equalsIgnoreCase(bookingId)) {
                return b;
            }
        }
        return null;
    }

    // ---------------- Cancel booking ----------------
    public boolean cancelBooking(String bookingId) {
        Booking booking = findBookingById(bookingId);
        if (booking == null || booking.getStatus() == BookingStatus.CANCELLED) {
            return false;
        }
        booking.setStatus(BookingStatus.CANCELLED);
        booking.getRoom().setAvailable(true); // room status update
        fileManager.rewriteAll(bookings);     // keep the file in sync
        return true;
    }

    // ---------------- Booking history ----------------
    public List<Booking> getBookingHistory() {
        return bookings;
    }
}
