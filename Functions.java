
import java.io.*;
import java.util.*;

public class Functions {

    //PRE: accepts a string
    //POST: returns true if the string only contains digits
    public static boolean isDigits(String str) {
        return str.matches("\\d+");
    }

    //PRE: accepts one line from a CSV file
    //POST: returns the fields in an ArrayList 
    public static ArrayList<String> splitCSV(String line) {
        ArrayList<String> fields = new ArrayList<String>();
        String field = "";
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '"') {
                if (inQuotes && i + 1 < line.length()
                        && line.charAt(i + 1) == '"') {
                    field += '"';
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (ch == ',' && !inQuotes) {
                fields.add(field.trim());
                field = "";
            } else {
                field += ch;
            }
        }

        fields.add(field.trim());
        return fields;
    }

    //PRE: accepts a filename, error log, and empty ArrayList
    //POST: reads the CSV and loads valid Pet objects 
    public static void readFile(String filename, String errorLog,
                                ArrayList<Pet> allPets) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            PrintWriter errWriter = new PrintWriter(new FileWriter(errorLog));

            String line = br.readLine();
            int lineNumber = 1;

            while ((line = br.readLine()) != null) {
                lineNumber++;

                if (line.trim().equals(""))
                    continue;

                ArrayList<String> data = splitCSV(line);

                if (data.size() < 12) {
                    errWriter.println("Invalid data on line " + lineNumber);
                    continue;
                }

                try {
                    String ID = data.get(0);
                    String name = data.get(1);
                    String dateTime = data.get(2);
                    String birthDate = data.get(4);
                    String outcome = data.get(5);
                    String type = data.get(7);
                    String sex = data.get(8);
                    String breed = data.get(10);

                    Pet pet = new Pet(ID, name, dateTime, birthDate,
                                      outcome, type, sex, breed);

                    pet.setAgeAtDateTime();
                    pet.setAgeCurrent();

                    allPets.add(pet);

                } catch (RuntimeException e) {
                    errWriter.println("Error on line " + lineNumber
                            + ": " + e.getMessage());
                }
            }

            br.close();
            errWriter.close();

            System.out.println(allPets.size() + " pets loaded.");

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    //PRE: accepts a report writer
    //POST: writes the column headings 
    public static void printHeader(PrintWriter out) {
        out.printf("%-22s %-10s %-35s %-18s %10s%n",
                "NAME", "TYPE", "BREED", "SEX", "INTAKE AGE");
    }

    //PRE: accepts a Pet object and report writer
    //POST: writes the pet information to the report
    public static void printPet(Pet pet, PrintWriter out) {
        out.printf("%-22s %-10s %-35s %-18s %10.2f%n",
                pet.getName(), pet.getType(), pet.getBreed(),
                pet.getSex(), pet.getAgeAtDateTime());
    }

    //PRE: accepts all pets and a report writer
    //POST: lists available pets by type and current age
    public static void petsAvailable(ArrayList<Pet> allPets,
                                     PrintWriter out) {

        ArrayList<Pet> available = new ArrayList<Pet>();

        for (Pet pet : allPets) {
            if (pet.getOutcome().trim().equals("")) {
                available.add(pet);
            }
        }

        available.sort(Comparator.comparing(Pet::getType)
                .thenComparing(Pet::getAgeCurrent));

        out.println();
        out.println("LISTING OF AVAILABLE PETS");
        printHeader(out);

        for (Pet pet : available) {
            printPet(pet, out);
        }

        if (available.size() == 0) {
            out.println("No available pets found.");
        }

        out.println();
        out.flush();

        System.out.println("Available pets written to report.txt");
    }

    //PRE: accepts all pets, a name, type, and report writer
    //POST: finds matching pets and writes results to report
    public static void findPet(ArrayList<Pet> allPets, String name,
                               String type, PrintWriter out) {

        ArrayList<Pet> found = new ArrayList<Pet>();

        for (Pet pet : allPets) {

            String petName = pet.getName();

            // Remove the asterisk when comparing names
            if (petName.startsWith("*")) {
                petName = petName.substring(1);
            }

            if (petName.equalsIgnoreCase(name.trim())
                    && pet.getType().equalsIgnoreCase(type.trim())) {
                found.add(pet);
            }
        }

        found.sort(Comparator.comparing(Pet::getAgeCurrent));

        out.println();
        out.println("SEARCH RESULTS FOR: " + name + " / " + type);

        if (found.size() == 0) {
            String message = "No pets found matching this description.";
            System.out.println(message);
            out.println(message);
        } else {
            printHeader(out);

            for (Pet pet : found) {
                printPet(pet, out);
            }

            System.out.println("Search results written to report.txt");
        }

        out.println();
        out.flush();
    }

    //PRE: accepts all pets, valid month/year, and report writer
    //POST: lists adopted pets sorted by type and name
    public static void listAdoptions(ArrayList<Pet> allPets,
                                     int month, int year, PrintWriter out) {

        ArrayList<Pet> adopted = new ArrayList<Pet>();

        for (Pet pet : allPets) {
            if (pet.getOutcome().equalsIgnoreCase("Adoption")) {
                int[] date = DateUtils.parseDateTime(pet.getDateTime());

                if (date[0] == month && date[2] == year) {
                    adopted.add(pet);
                }
            }
        }

        adopted.sort(Comparator.comparing(Pet::getType)
                .thenComparing(Pet::getName));

        out.println();
        out.println("LISTING OF ADOPTIONS FOR: " + month + " / " + year);
        printHeader(out);

        for (Pet pet : adopted) {
            printPet(pet, out);
        }

        if (adopted.size() == 0) {
            out.println("No adoptions found for this month and year.");
        }

        out.println();
        out.flush();

        System.out.println("Adopted pets written to report.txt");
    }
}
