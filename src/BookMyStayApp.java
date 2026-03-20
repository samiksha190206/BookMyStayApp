import java.util.HashMap;
import java.util.Map;

public class BookMyStayApp {
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
        public void updateAvailability(String roomType, int count) {
            if (inventory.containsKey(roomType)) {
                inventory.put(roomType, count);
            } else {
                System.out.println("Room type " + roomType + " not found in inventory.");
            }
        }
        public void displayInventory() {
            System.out.println("Current Room Inventory:");
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
            }
        }
    }

    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Suite Room", 2);
        System.out.println("Welcome to Book My Stay - Centralized Room Inventory (v3.1)\n");
        inventory.displayInventory();
        System.out.println("\nBooking one Single Room...");
        inventory.updateAvailability("Single Room", inventory.getAvailability("Single Room") - 1);

        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();

        System.out.println("\nInventory setup completed successfully.");
    }
}


