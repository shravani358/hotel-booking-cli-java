package model;

/**
 * Enum representing the different types of rooms available in the hotel.
 *
 * This implements two "Recommended" features from the specification:
 *   - room types: Single, Double, Deluxe, Suite
 *   - different room prices
 *
 * Each type also carries its own maximum guest capacity, used later
 * by the "maximum guest limit" feature.
 */
public enum RoomType {

    SINGLE(1500.0, 1),
    DOUBLE(2500.0, 2),
    DELUXE(4000.0, 3),
    SUITE(6000.0, 4);

    private final double defaultPrice;
    private final int maxCapacity;

    RoomType(double defaultPrice, int maxCapacity) {
        this.defaultPrice = defaultPrice;
        this.maxCapacity = maxCapacity;
    }

    public double getDefaultPrice() {
        return defaultPrice;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
