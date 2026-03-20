
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

        public int getNumberOfBeds() {
            return numberOfBeds;
        }

        public double getSize() {
            return size;
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
    static class SingleRoom extends Room {
        public SingleRoom() {
            super("Single Room", 1, 20.0, 50.0);
        }
    }

    static class DoubleRoom extends Room {
        public DoubleRoom() {
            super("Double Room", 2, 30.0, 80.0);
        }
    }

    static class SuiteRoom extends Room {
        public SuiteRoom() {
            super("Suite Room", 3, 50.0, 150.0);
        }
    }

    public static void main(String[] args) {
        int singleRoomAvailable = 5;
        int doubleRoomAvailable = 3;
        int suiteRoomAvailable = 2;
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();
        System.out.println("Welcome to Book My Stay - Room Overview (v2.1)\n");

        singleRoom.displayRoomDetails();
        System.out.println("Available: " + singleRoomAvailable + "\n");

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleRoomAvailable + "\n");

        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + suiteRoomAvailable + "\n");

        System.out.println("Room initialization completed.");
    }
}



