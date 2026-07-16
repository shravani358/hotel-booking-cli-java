package repository;

import model.Booking;
import model.BookingStatus;
import model.Guest;
import model.Room;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * FileManager.java (Repository)
 *
 * Implements the "file-based data storage" recommended feature:
 *   - save booking data
 *   - load booking data
 *   - maintain booking history
 *
 * Bookings are stored as one line per record in data/bookings.txt using
 * "|" as a field separator. A plain text format is used on purpose so a
 * beginner can open the file and read it directly.
 *
 * File line format:
 * bookingId|guestName|phone|email|idProof|roomId|checkIn|checkOut|nights|totalAmount|status
 */
public class FileManager {

    private static final String FOLDER_PATH = "data";
    private static final String FILE_PATH = "data/bookings.txt";

    public FileManager() {
        ensureDataFileExists();
    }

    private void ensureDataFileExists() {
        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Warning: could not create data file - " + e.getMessage());
            }
        }
    }

    /** Appends a single new booking record to the file. */
    public void saveBooking(Booking booking) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(toLine(booking));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving booking to file: " + e.getMessage());
        }
    }

    /**
     * Rewrites the entire file from the given list.
     * Used after a booking's status changes (e.g. cancellation) so the
     * file on disk always matches what is shown on screen.
     */
    public void rewriteAll(List<Booking> bookings) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Booking b : bookings) {
                writer.write(toLine(b));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating booking file: " + e.getMessage());
        }
    }

    /**
     * Loads every booking record from file and reconnects it to the matching
     * Room object from the current room inventory (needed so room availability
     * stays correct after a restart). This also gives us the booking history.
     */
    public List<Booking> loadBookings(List<Room> allRooms) {
        List<Booking> bookings = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return bookings;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                Booking booking = fromLine(line, allRooms);
                if (booking != null) {
                    bookings.add(booking);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading bookings from file: " + e.getMessage());
        }
        return bookings;
    }

    private String toLine(Booking b) {
        Guest g = b.getGuest();
        String idProof = (g.getIdProof() == null || g.getIdProof().isEmpty()) ? "NA" : g.getIdProof();
        return String.join("|",
                b.getBookingId(),
                g.getName(),
                g.getPhoneNumber(),
                g.getEmail(),
                idProof,
                b.getRoom().getRoomId(),
                b.getCheckInDate().toString(),
                b.getCheckOutDate().toString(),
                String.valueOf(b.getNumberOfNights()),
                String.valueOf(b.getTotalAmount()),
                b.getStatus().toString());
    }

    private Booking fromLine(String line, List<Room> allRooms) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 11) {
            return null; // skip malformed / corrupted lines instead of crashing
        }

        try {
            String bookingId = parts[0];
            String idProof = parts[4].equals("NA") ? "" : parts[4];
            Guest guest = new Guest(parts[1], parts[2], parts[3], idProof);

            Room room = findRoomById(allRooms, parts[5]);
            if (room == null) {
                return null; // room no longer exists in inventory
            }

            LocalDate checkIn = LocalDate.parse(parts[6]);
            LocalDate checkOut = LocalDate.parse(parts[7]);
            int nights = Integer.parseInt(parts[8]);
            double total = Double.parseDouble(parts[9]);
            BookingStatus status = BookingStatus.valueOf(parts[10]);

            return new Booking(bookingId, guest, room, checkIn, checkOut, nights, total, status);
        } catch (Exception e) {
            // any parsing problem on a single line should not crash the app
            return null;
        }
    }

    private Room findRoomById(List<Room> rooms, String roomId) {
        for (Room r : rooms) {
            if (r.getRoomId().equals(roomId)) {
                return r;
            }
        }
        return null;
    }
}
