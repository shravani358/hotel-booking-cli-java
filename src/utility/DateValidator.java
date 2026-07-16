package utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * DateValidator (Utility)
 *
 * Implements the "date validation" recommended feature.
 * Kept as a separate utility class so date rules can be reused by both
 * HotelService and Main, and tested independently.
 */
public class DateValidator {

    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /** Parses a date string in yyyy-MM-dd format. Returns null if the text is not a valid date. */
    public static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr.trim(), FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /** Check-in date cannot be in the past. */
    public static boolean isNotPastDate(LocalDate date) {
        return !date.isBefore(LocalDate.now());
    }

    /** Check-out date must be strictly after the check-in date. */
    public static boolean isCheckOutAfterCheckIn(LocalDate checkIn, LocalDate checkOut) {
        return checkOut.isAfter(checkIn);
    }
}
