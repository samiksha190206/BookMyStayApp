

import java.util.*;

// Custom exception for invalid bookings
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Inventory management for rooms
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    // Validate and update inventory
    public void bookRoom(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
        int available = inventory.get(roomType);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
        inventory.put(roomType, available - 1);
        System.out.println("Booking confirmed for " + roomType + " room. Remaining: " + (available - 1));
    }

    // Display current inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " rooms: " + entry.getValue());
        }
        System.out.println();
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Book My Stay - Error Handling & Validation (v9.0)\n");

        RoomInventory inventory = new RoomInventory();
        inventory.displayInventory();

        // Sample guest booking attempts
        String[] bookingRequests = {"Single", "Suite", "Double", "King", "Suite", "Suite"};

        for (String roomType : bookingRequests) {
            try {
                System.out.println("Attempting to book: " + roomType);
                inventory.bookRoom(roomType);
            } catch (InvalidBookingException e) {
                System.out.println("Booking Failed: " + e.getMessage());
            }
        }

        inventory.displayInventory();
    }
}
