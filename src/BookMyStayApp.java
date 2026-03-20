

import java.util.*;

// Simple reservation class
class Reservation {
    private String reservationID;
    private String guestName;
    private String roomType;
    private double roomCost;

    public Reservation(String reservationID, String guestName, String roomType, double roomCost) {
        this.reservationID = reservationID;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomCost = roomCost;
    }

    public String getReservationID() {
        return reservationID;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getRoomCost() {
        return roomCost;
    }

    @Override
    public String toString() {
        return reservationID + " | " + guestName + " | " + roomType + " | $" + roomCost;
    }
}

// Booking History: stores confirmed reservations
class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(history); // Prevent modification
    }
}

// Reporting service: generates summary from booking history
class BookingReportService {
    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    public void generateReport() {
        System.out.println("Booking History Report:");
        System.out.println("ReservationID | Guest | Room Type | Cost");
        System.out.println("-----------------------------------------");
        for (Reservation res : history.getAllReservations()) {
            System.out.println(res);
        }
        System.out.println("\nTotal Bookings: " + history.getAllReservations().size());
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Book My Stay - Booking History & Reporting (v8.0)\n");

        // Initialize booking history
        BookingHistory bookingHistory = new BookingHistory();

        // Sample confirmed reservations
        bookingHistory.addReservation(new Reservation("SI100", "Alice", "Single Room", 100.0));
        bookingHistory.addReservation(new Reservation("SU101", "Bob", "Suite Room", 250.0));
        bookingHistory.addReservation(new Reservation("DO102", "Charlie", "Double Room", 180.0));
        bookingHistory.addReservation(new Reservation("SI103", "Diana", "Single Room", 100.0));

        // Initialize reporting service
        BookingReportService reportService = new BookingReportService(bookingHistory);

        // Generate booking report for admin
        reportService.generateReport();
    }
}
