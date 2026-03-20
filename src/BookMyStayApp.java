

import java.util.*;

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

// Centralized inventory
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public boolean allocateRoom(String roomType) {
        int available = getAvailability(roomType);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}

// Booking Service: confirms reservations and allocates unique room IDs
class BookingService {
    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRooms; // roomType -> assigned room IDs
    private int roomCounter = 100; // To generate unique room IDs

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
    }

    // Process reservation
    public void confirmReservation(Reservation request) {
        String roomType = request.getRequestedRoomType();
        if (inventory.allocateRoom(roomType)) {
            // Generate unique room ID
            String roomID = roomType.substring(0, 2).toUpperCase() + roomCounter++;
            allocatedRooms.putIfAbsent(roomType, new HashSet<>());
            allocatedRooms.get(roomType).add(roomID);

            System.out.println("Reservation Confirmed:");
            System.out.println(request);
            System.out.println("Assigned Room ID: " + roomID + "\n");
        } else {
            System.out.println("Reservation Failed (No availability): " + request + "\n");
        }
    }

    public void displayAllocatedRooms() {
        System.out.println("Allocated Rooms:");
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Book My Stay - Room Allocation Service (v6.0)\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);
        inventory.addRoomType("Suite Room", 1);

        // Initialize booking queue (FIFO)
        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Suite Room"));
        bookingQueue.add(new Reservation("Charlie", "Double Room"));
        bookingQueue.add(new Reservation("Diana", "Single Room")); // Should get last available Single Room
        bookingQueue.add(new Reservation("Eve", "Single Room"));   // Should fail (no more Single Rooms)

        // Initialize booking service
        BookingService bookingService = new BookingService(inventory);

        // Process queued requests
        while (!bookingQueue.isEmpty()) {
            Reservation request = bookingQueue.poll();
            bookingService.confirmReservation(request);
        }

        // Display final inventory
        System.out.println("Final Inventory Status:");
        inventory.displayInventory();

        // Display allocated rooms
        System.out.println();
        bookingService.displayAllocatedRooms();
    }
}