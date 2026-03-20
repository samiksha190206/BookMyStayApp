

import java.util.HashMap;
import java.util.Map;

public class BookMyStayApp {

    // Abstract Room class
    abstract static class Room {
        private String roomType;
        private int numberOfBeds;
        private double size;
        private double pricePerNight;

        public Room(String roomType, int numberOfBeds, double size, double pricePerNight) {
            this.roomType = roomType;
            this.numberOfBeds = numberOfBeds;
            this.size = size;
            this.pricePerNight = pricePerNight;
        }

        public String getRoomType() {
            return roomType;
        }

        public double getPricePerNight() {
            return pricePerNight;
        }

        public void displayRoomDetails() {
            System.out.println("Room Type: " + roomType);
            System.out.println("Beds: " + numberOfBeds);
            System.out.println("Size: " + size + " sqm");
            System.out.println("Price per Night: $" + pricePerNight);
        }
    }

    // Concrete Room Types
    static class SingleRoom extends Room {
        public SingleRoom() { super("Single Room", 1, 20.0, 50.0); }
    }

    static class DoubleRoom extends Room {
        public DoubleRoom() { super("Double Room", 2, 30.0, 80.0); }
    }

    static class SuiteRoom extends Room {
        public SuiteRoom() { super("Suite Room", 3, 50.0, 150.0); }
    }

    // Inventory class (centralized, read access allowed)
    static class RoomInventory {
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
    }

    // Search service (read-only)
    static class RoomSearchService {
        private RoomInventory inventory;
        private Room[] rooms;

        public RoomSearchService(RoomInventory inventory, Room[] rooms) {
            this.inventory = inventory;
            this.rooms = rooms;
        }

        public void displayAvailableRooms() {
            System.out.println("Available Rooms for Booking:\n");
            for (Room room : rooms) {
                int available = inventory.getAvailability(room.getRoomType());
                if (available > 0) {
                    room.displayRoomDetails();
                    System.out.println("Available: " + available + "\n");
                }
            }
        }
    }

    public static void main(String[] args) {
        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 0);  // No availability
        inventory.addRoomType("Suite Room", 2);

        // Initialize room objects
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService(inventory, rooms);

        System.out.println("Book My Stay - Room Search & Availability (v4.0)\n");

        // Perform search (read-only)
        searchService.displayAvailableRooms();

        System.out.println("Room search completed successfully.");
    }
}