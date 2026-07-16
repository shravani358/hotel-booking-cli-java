package utility;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * IDGenerator (Utility)
 *
 * Implements the "generate booking ID" mandatory feature.
 * Produces sequential, unique booking IDs such as BK1001, BK1002, ...
 */
public class IDGenerator {

    private static final AtomicInteger counter = new AtomicInteger(1000);

    /**
     * Called once when the app starts (after loading existing bookings from file)
     * so that new IDs never collide with IDs already saved on disk.
     */
    public static void initializeFrom(int highestExistingNumber) {
        if (highestExistingNumber >= counter.get()) {
            counter.set(highestExistingNumber);
        }
    }

    public static String nextBookingId() {
        return "BK" + counter.incrementAndGet();
    }
}
