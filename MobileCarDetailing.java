import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// Base class representing an order
class Order {
    protected String customerName;
    protected String selectedService;
    protected double servicePrice;
    
    public Order(String customerName, String selectedService, double servicePrice) {
        this.customerName = customerName;
        this.selectedService = selectedService;
        this.servicePrice = servicePrice;
    }

    public void displayOrder() {
        System.out.println("\nOrder Summary:");
        System.out.println("Customer Name: " + customerName);
        System.out.println("Selected Service: " + selectedService);
        System.out.println("Service Price: $" + servicePrice);
    }
}

// Inherited class for setting appointment details
class Appointment extends Order {
    private String appointmentDate;
    private String appointmentTime;
    
    public Appointment(String customerName, String selectedService, double servicePrice, String appointmentDate, String appointmentTime) {
        super(customerName, selectedService, servicePrice);
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    public void displayAppointment() {
        displayOrder();
        System.out.println("Appointment Date: " + appointmentDate);
        System.out.println("Appointment Time: " + appointmentTime);
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter("order_summary.txt")) {
            writer.write("Order Summary\n");
            writer.write("Customer Name: " + customerName + "\n");
            writer.write("Selected Service: " + selectedService + "\n");
            writer.write("Service Price: $" + servicePrice + "\n");
            writer.write("Appointment Date: " + appointmentDate + "\n");
            writer.write("Appointment Time: " + appointmentTime + "\n");
            System.out.println("\nOrder summary saved to 'order_summary.txt'.");
        } catch (IOException e) {
            System.out.println("Error saving order summary: " + e.getMessage());
        }
    }
}

// Main class
public class MobileCarDetailing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Available services
        String[] services = {"Basic Wash", "Interior Detailing", "Full Detail", "Ceramic Coating"};
        double[] prices = {25.00, 50.00, 100.00, 200.00};

        // Getting customer information
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        // Displaying service options
        System.out.println("\nAvailable Services:");
        for (int i = 0; i < services.length; i++) {
            System.out.println((i + 1) + ". " + services[i] + " - $" + prices[i]);
        }

        // Selecting service
        int choice;
        do {
            System.out.print("Select a service (1-4): ");
            choice = scanner.nextInt();
            if (choice < 1 || choice > 4) {
                System.out.println("Invalid choice. Please select a valid service.");
            }
        } while (choice < 1 || choice > 4);

        String selectedService = services[choice - 1];
        double servicePrice = prices[choice - 1];

        // Setting appointment
        System.out.print("Enter appointment date (MM/DD/YYYY): ");
        scanner.nextLine();  // Consume newline
        String date = scanner.nextLine();

        // Ensuring valid time selection
        String time;
        do {
            System.out.print("Enter appointment time (9 AM - 6 PM): ");
            time = scanner.nextLine();
        } while (!isValidTime(time));

        // Creating appointment object
        Appointment appointment = new Appointment(name, selectedService, servicePrice, date, time);

        // Displaying and saving order
        appointment.displayAppointment();
        appointment.saveToFile();

        scanner.close();
    }

    // Method to validate time
    public static boolean isValidTime(String time) {
        String[] validTimes = {"9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM"};
        for (String valid : validTimes) {
            if (time.equalsIgnoreCase(valid)) {
                return true;
            }
        }
        System.out.println("Invalid time. Please enter a time between 9 AM and 6 PM.");
        return false;
    }
}
