package model;

import java.time.LocalDate;

/**
 * Booking.java (Model)
 *
 * Represents one hotel booking record.
 * Required fields (from the specification):
 *   - booking ID
 *   - guest
 *   - room
 *   - check-in date
 *   - check-out date
 *   - number of nights
 *   - total amount
 *   - booking status
 */
public class Booking {

    private String bookingId;
    private Guest guest;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfNights;
    private double totalAmount;
    private BookingStatus status;

    public Booking(String bookingId, Guest guest, Room room, LocalDate checkInDate,
                   LocalDate checkOutDate, int numberOfNights, double totalAmount, BookingStatus status) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfNights = numberOfNights;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\n--------------------------------------------\n" +
                "Booking ID   : " + bookingId + "\n" +
                "Guest        : " + guest.getName() + " (" + guest.getPhoneNumber() + ")\n" +
                "Room         : " + room.getRoomId() + " - " + room.getRoomType() + "\n" +
                "Check-In     : " + checkInDate + "\n" +
                "Check-Out    : " + checkOutDate + "\n" +
                "Nights       : " + numberOfNights + "\n" +
                "Total Amount : Rs." + String.format("%.2f", totalAmount) + "\n" +
                "Status       : " + status + "\n" +
                "--------------------------------------------";
    }
}
