/**
 * UseCase11ConcurrentBookingSimulation
 *
 * Demonstrates concurrent booking requests with thread safety.
 * Ensures room allocation and inventory updates are synchronized.
 *
 * Key Concepts:
 * - Race conditions
 * - Thread safety with synchronized methods
 * - Shared mutable state protection
 * - Critical sections
 *
 * Author: YourName
 * Version: 11.0
 */

import java.util.*;
import java.util.concurrent.*;

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

// Thread-safe inventory management
class RoomInventory {
    private Map<String, Integer> inventory;
    private Map<String, Set<String>> allocatedRooms;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);

        allocatedRooms = new HashMap<>();
        allocatedRooms.put("Single", new HashSet<>());
        allocatedRooms.put("Double", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());
    }

    // Synchronized method ensures only one thread allocates at a time
    public synchronized String allocateRoom(String roomType) throws Exception {
        if (!inventory.containsKey(roomType)) {
            throw new Exception("Invalid room type: " + roomType);
        }

        int available = inventory.get(roomType);
        if (available <= 0) {
            throw new Exception("No rooms available for type: " + roomType);
        }

        // Generate a unique room ID
        String roomId = roomType.substring(0, 1) + (available);
        allocatedRooms.get(roomType).add(roomId);

        // Decrement inventory
        inventory.put(roomType, available - 1);

        return roomId;
    }

    public synchronized void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " rooms: " + entry.getValue());
        }
        System.out.println();
    }
}

// Thread class to simulate guest booking
class GuestBookingThread extends Thread {
    private Reservation reservation;
    private RoomInventory inventory;

    public GuestBookingThread(Reservation reservation, RoomInventory inventory) {
        this.reservation = reservation;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        try {
            String roomId = inventory.allocateRoom(reservation.getRoomType());
            System.out.println("Booking confirmed: " + reservation.getReservationId() +
                    " -> " + roomId + " by " + Thread.currentThread().getName());
        } catch (Exception e) {
            System.out.println("Booking failed for " + reservation.getReservationId() +
                    ": " + e.getMessage());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Book My Stay - Concurrent Booking Simulation (v11.0)\n");

        RoomInventory inventory = new RoomInventory();

        // Simulate multiple concurrent guests
        List<Reservation> reservations = Arrays.asList(
                new Reservation("R001", "Single"),
                new Reservation("R002", "Double"),
                new Reservation("R003", "Suite"),
                new Reservation("R004", "Single"),
                new Reservation("R005", "Double"),
                new Reservation("R006", "Suite"),
                new Reservation("R007", "Single")
        );

        List<Thread> threads = new ArrayList<>();

        // Create threads for each reservation
        for (Reservation res : reservations) {
            GuestBookingThread thread = new GuestBookingThread(res, inventory);
            threads.add(thread);
            thread.start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }

        inventory.displayInventory();
    }
}