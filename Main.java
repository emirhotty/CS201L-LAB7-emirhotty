
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

    //PRE: none
    //POST: runs the pet processing program 
    public static void main(String[] args) {

        // Set the file names
        String inputCSV = "PPShelter.csv";
        String reportFile = "report.txt";
        String errorFile = "error.txt";

        // Create the ArrayList of Pet objects
        ArrayList<Pet> pets = new ArrayList<Pet>();

        // Read the input file
        Functions.readFile(inputCSV, errorFile, pets);

        Scanner scanner = new Scanner(System.in);

        try {
            PrintWriter out = new PrintWriter(new FileWriter(reportFile));

            while (true) { 
                System.out.println();
                System.out.println("=== Pet Processing Menu ===");
                System.out.println("1. List Adopted Pets");
                System.out.println("2. List Available Pets");
                System.out.println("3. Search Pet by Name");
                System.out.println("Q. Quit");

                System.out.print("Enter your choice: ");
                String choice = scanner.nextLine().trim().toUpperCase();

                switch (choice) {

                    case "1":
                        System.out.print("Enter Month: ");
                        String monthInput = scanner.nextLine().trim();

                        System.out.print("Enter Year: ");
                        String yearInput = scanner.nextLine().trim();

                        if (!Functions.isDigits(monthInput)
                                || !Functions.isDigits(yearInput)) {
                            System.out.println("Invalid month or year.");
                            break;
                        }

                        try {
                            int month = Integer.parseInt(monthInput);
                            int year = Integer.parseInt(yearInput);

                            if (month < 1 || month > 12
                                    || year < 2020 || year > 2024) {
                                System.out.println("Invalid month or year.");
                            } else {
                                Functions.listAdoptions(pets, month, year, out);
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Invalid month or year.");
                        }

                        break;

                    case "2":
                        Functions.petsAvailable(pets, out);
                        break;

                    case "3":
                        System.out.print("Enter pet name: ");
                        String name = scanner.nextLine();

                        System.out.print("Enter pet type: ");
                        String type = scanner.nextLine();

                        Functions.findPet(pets, name, type, out);
                        break;

                    case "Q":
                        System.out.println("Goodbye!");
                        scanner.close();
                        out.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (IOException e) { 
            System.out.println("Error opening file.");
            e.printStackTrace();
        }
    }
}
