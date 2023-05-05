package pl.milenamrugala;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;


public class MainATM {

    static final String[] OPTIONS = {"balance", "deposit", "withdrawal", "exit"};
    static BigDecimal balance = BigDecimal.ZERO;

    public static void printOptions(String[] array) {

        System.out.println("Please select an option: ");
        for (String option : array) {
            System.out.println(option);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            readAccountInformation();
        } catch (IOException e) {
            System.out.println("Error reading account information: " + e.getMessage());
            return;
        }

        while (true) {
            printOptions(OPTIONS);
            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "balance":
                    System.out.println("Your balance is: " + balance);
                    break;
                case "deposit":
                    System.out.println("How much do you want to deposit?");
                    BigDecimal depositAmount = scanner.nextBigDecimal();
                    balance = balance.add(depositAmount);
                    System.out.println("Your new balance is: " + balance);
                    scanner.nextLine();
                    break;
                case "withdrawal":
                    System.out.println("How much do you want to withdraw?");
                    BigDecimal withdrawalAmount = scanner.nextBigDecimal();
                    if (withdrawalAmount.compareTo(balance) > 0) {
                        System.out.println("You don't have enough balance.");
                    } else {
                        balance = balance.subtract(withdrawalAmount);
                        System.out.println("Your new balance is: " + balance);
                    }
                    scanner.nextLine();
                    break;
                case "exit":
                    try {
                        writeAccountInformation(balance.toString());
                    } catch (IOException e) {
                        System.out.println("Error writing account information: " + e.getMessage());
                    }
                    System.out.println("Thank you for using the ATM.");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    public static void readAccountInformation() throws IOException {
        File file = new File("account.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                balance = new BigDecimal(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading account information: " + e.getMessage());
        }
    }

    public static void writeAccountInformation(String balance) throws IOException {
        try (FileWriter writer = new FileWriter("account.txt")) {
            writer.write(balance);
        }
    }
}
