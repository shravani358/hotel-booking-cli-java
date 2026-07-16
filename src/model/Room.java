package model;

/**
 * Room.java (Model)
 *
 * Represents a single hotel room.
 * Required fields (from the specification):
 *   - room ID
 *   - room type
 *   - price per night
 *   - availability status
 *   - maximum capacity
 */
public class Room {

    private String roomId;
    private RoomType roomType;
    private double pricePerNight;
    private boolean available;
    private int maxCapacity;

    public Room(String roomId, RoomType roomType, double pricePerNight, int maxCapacity) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.maxCapacity = maxCapacity;
        this.available = true; // every room starts out available
    }

    public String getRoomId() {
        return roomId;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return available;
    }

    /** Used for the "room status update" feature (booked / cancelled). */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    @Override
    public String toString() {
        String status = available ? "Available" : "Booked";
        return String.format("%-6s | %-7s | Rs.%-9.2f | Max Guests: %-2d | %s",
                roomId, roomType, pricePerNight, maxCapacity, status);
    }
}
