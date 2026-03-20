

import java.util.*;

class Reservation {
    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory and room allocation management
class RoomInventory {
    private Map<String, Integer> inventory;
    private Map<String, Stack<String>> allocatedRooms; // Track allocated room IDs per type

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);

        allocatedRooms = new HashMap<>();
        allocatedRooms.put("Single", new Stack<>());
        allocatedRooms.put("Double", new Stack<>());
        allocatedRooms.put("Suite", new Stack<>());
    }

    // Allocate a room and return a room ID
    public String allocateRoom(String roomType) throws Exception {
        if (!inventory.containsKey(roomType)) {
            throw new Exception("Invalid room type: " + roomType);
        }
        int available = inventory.get(roomType);
        if (available <= 0) {
            throw new Exception("No rooms available for type: " + roomType);
        }

        // Generate room ID
        String roomId = roomType.substring(0, 1) + (available);
        allocatedRooms.get(roomType).push(roomId);
        inventory.put(roomType, available - 1);

        return roomId;
    }

    // Cancel a booking and rollback inventory
    public void cancelRoom(String roomType) throws Exception {
        if (!inventory.containsKey(roomType)) {
            throw new Exception("Invalid room type: " + roomType);
        }
        Stack<String> roomStack = allocatedRooms.get(roomType);
        if (roomStack.isEmpty()) {
            throw new Exception("No allocated rooms to cancel for type: " + roomType);
        }

        String releasedRoom = roomStack.pop();
        int available = inventory.get(roomType);
        inventory.put(roomType, available + 1);

        System.out.println("Cancellation successful for room ID: " + releasedRoom +
                " (" + roomType + "). Inventory restored to " + (available + 1));
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " rooms: " + entry.getValue());
        }
        System.out.println();
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Book My Stay - Booking Cancellation & Inventory Rollback (v10.0)\n");

        RoomInventory inventory = new RoomInventory();

        // Simulate bookings
        List<Reservation> reservations = new ArrayList<>();
        try {
            reservations.add(new Reservation("R001", "Single"));
            reservations.add(new Reservation("R002", "Suite"));
            reservations.add(new Reservation("R003", "Double"));

            for (Reservation res : reservations) {
                String roomId = inventory.allocateRoom(res.getRoomType());
                System.out.println("Booking confirmed: " + res.getReservationId() +
                        " -> " + roomId);
            }
        } catch (Exception e) {
            System.out.println("Booking Error: " + e.getMessage());
        }

        inventory.displayInventory();

        // Simulate cancellations
        String[] cancellations = {"Suite", "Single", "Suite"}; // Last one will fail
        for (String roomType : cancellations) {
            try {
                System.out.println("Attempting to cancel: " + roomType);
                inventory.cancelRoom(roomType);
            } catch (Exception e) {
                System.out.println("Cancellation Failed: " + e.getMessage());
            }
        }

        inventory.displayInventory();
    }
}