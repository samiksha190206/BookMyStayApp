import java.util.LinkedList;
import java.util.Queue;

// Represents a guest's booking request
class Reservation {
    private String guestName;
    private String requestedRoomType;

    public Reservation(String guestName, String requestedRoomType) {
        this.guestName = guestName;
        this.requestedRoomType = requestedRoomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRequestedRoomType() {
        return requestedRoomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Requested Room: " + requestedRoomType;
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Book My Stay - Booking Request Queue (v5.0)\n");

        // Initialize booking request queue (FIFO)
        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Sample booking requests from guests
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Suite Room"));
        bookingQueue.add(new Reservation("Charlie", "Double Room"));
        bookingQueue.add(new Reservation("Diana", "Single Room"));

        // Display queued requests without modifying inventory
        System.out.println("Current Booking Requests (FIFO order):\n");
        for (Reservation request : bookingQueue) {
            System.out.println(request);
        }

        System.out.println("\nAll booking requests queued successfully. Inventory remains unchanged.");
    }
}