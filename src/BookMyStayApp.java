

import java.util.*;

class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name + " ($" + cost + ")";
    }
}

// Add-On Service Manager
class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Attach service(s) to a reservation
    public void addServices(String reservationID, Service... services) {
        reservationServices.putIfAbsent(reservationID, new ArrayList<>());
        reservationServices.get(reservationID).addAll(Arrays.asList(services));
    }

    // Display services attached to a reservation
    public void displayServices(String reservationID) {
        List<Service> services = reservationServices.get(reservationID);
        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected for Reservation ID: " + reservationID);
            return;
        }
        System.out.println("Add-On Services for Reservation ID: " + reservationID + ":");
        double totalCost = 0.0;
        for (Service service : services) {
            System.out.println("- " + service);
            totalCost += service.getCost();
        }
        System.out.println("Total Add-On Cost: $" + totalCost + "\n");
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Book My Stay - Add-On Service Selection (v7.0)\n");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Sample reservation IDs (from previous UC6)
        String res1 = "SI100";
        String res2 = "SU101";

        // Define some add-on services
        Service breakfast = new Service("Breakfast", 15.0);
        Service airportPickup = new Service("Airport Pickup", 25.0);
        Service spaPackage = new Service("Spa Package", 50.0);

        // Attach services to reservations
        serviceManager.addServices(res1, breakfast, airportPickup);
        serviceManager.addServices(res2, spaPackage);

        // Display attached services
        serviceManager.displayServices(res1);
        serviceManager.displayServices(res2);

        // Reservation with no add-ons
        String res3 = "DO102";
        serviceManager.displayServices(res3);
    }
}

