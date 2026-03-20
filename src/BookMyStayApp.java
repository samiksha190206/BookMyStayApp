import java.io.*;
import java.util.*;
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
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

    @Override
    public String toString() {
        return reservationId + " -> " + roomType;
    }
}

// Serializable Inventory class
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public void allocateRoom(String roomType) throws Exception {
        if (!inventory.containsKey(roomType)) throw new Exception("Invalid room type: " + roomType);
        int available = inventory.get(roomType);
        if (available <= 0) throw new Exception("No rooms available for type: " + roomType);
        inventory.put(roomType, available - 1);
    }

    public void releaseRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " rooms: " + entry.getValue());
        }
        System.out.println();
    }

    public Map<String, Integer> getInventoryMap() {
        return inventory;
    }

    public void setInventoryMap(Map<String, Integer> map) {
        inventory = map;
    }
}

// Service for persistence
class PersistenceService {

    private static final String INVENTORY_FILE = "inventory.ser";
    private static final String BOOKINGS_FILE = "bookings.ser";

    // Save state to files
    public static void saveState(RoomInventory inventory, List<Reservation> bookings) {
        try (ObjectOutputStream oosInv = new ObjectOutputStream(new FileOutputStream(INVENTORY_FILE));
             ObjectOutputStream oosBook = new ObjectOutputStream(new FileOutputStream(BOOKINGS_FILE))) {

            oosInv.writeObject(inventory);
            oosBook.writeObject(bookings);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Restore state from files
    public static void restoreState(RoomInventory inventory, List<Reservation> bookings) {
        try (ObjectInputStream oisInv = new ObjectInputStream(new FileInputStream(INVENTORY_FILE));
             ObjectInputStream oisBook = new ObjectInputStream(new FileInputStream(BOOKINGS_FILE))) {

            RoomInventory restoredInventory = (RoomInventory) oisInv.readObject();
            List<Reservation> restoredBookings = (List<Reservation>) oisBook.readObject();

            inventory.setInventoryMap(restoredInventory.getInventoryMap());
            bookings.clear();
            bookings.addAll(restoredBookings);

            System.out.println("System state restored successfully.");

        } catch (FileNotFoundException e) {
            System.out.println("Persistence files not found. Starting with fresh state.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error restoring state: " + e.getMessage());
        }
    }
}

public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Data Persistence & Recovery (v12.0)\n");

        RoomInventory inventory = new RoomInventory();
        List<Reservation> bookings = new ArrayList<>();

        // Restore previous state if available
        PersistenceService.restoreState(inventory, bookings);

        inventory.displayInventory();
        System.out.println("Booking History:");
        for (Reservation r : bookings) {
            System.out.println(r);
        }
        System.out.println();

        // Simulate new bookings
        Reservation r1 = new Reservation("R101", "Single");
        Reservation r2 = new Reservation("R102", "Double");

        try {
            inventory.allocateRoom(r1.getRoomType());
            inventory.allocateRoom(r2.getRoomType());

            bookings.add(r1);
            bookings.add(r2);

            System.out.println("New bookings confirmed.");
        } catch (Exception e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        inventory.displayInventory();

        System.out.println("Updated Booking History:");
        for (Reservation r : bookings) {
            System.out.println(r);
        }

        // Save current state
        PersistenceService.saveState(inventory, bookings);
    }
}