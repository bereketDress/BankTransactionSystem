package bank.ui;

import bank.service.BankService;
import java.math.BigDecimal;
import java.util.Scanner;

public class BankApplication {
    private static final BankService bankService = new BankService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Bank Transaction System");
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Select an option: ");

            try {
                switch (choice) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        deposit();
                        break;
                    case 3:
                        withdraw();
                        break;
                    case 4:
                        transfer();
                        break;
                    case 5:
                        viewBalance();
                        break;
                    case 6:
                        running = false;
                        System.out.println("Thank you for using our bank!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Bank Menu ---");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. View Balance");
        System.out.println("6. Exit");
    }

    private static void createAccount() {
        String number = readString("Enter account number: ");
        String name = readString("Enter owner name: ");
        BigDecimal balance = readBigDecimal("Enter initial balance: ");
        bankService.createAccount(number, name, balance);
        System.out.println("Account created successfully.");
    }

    private static void deposit() {
        String number = readString("Enter account number: ");
        BigDecimal amount = readBigDecimal("Enter amount to deposit: ");
        bankService.deposit(number, amount);
        System.out.println("Deposit successful.");
    }

    private static void withdraw() {
        String number = readString("Enter account number: ");
        BigDecimal amount = readBigDecimal("Enter amount to withdraw: ");
        bankService.withdraw(number, amount);
        System.out.println("Withdrawal successful.");
    }

    private static void transfer() {
        String from = readString("Enter source account number: ");
        String to = readString("Enter destination account number: ");
        BigDecimal amount = readBigDecimal("Enter amount to transfer: ");
        bankService.transfer(from, to, amount);
        System.out.println("Transfer successful.");
    }

    private static void viewBalance() {
        String number = readString("Enter account number: ");
        bankService.getAccount(number).ifPresentOrElse(
            acc -> System.out.println("Account: " + acc),
            () -> System.out.println("Account not found.")
        );
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. " + prompt);
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static BigDecimal readBigDecimal(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextBigDecimal()) {
            System.out.print("Invalid input. " + prompt);
            scanner.next();
        }
        return scanner.nextBigDecimal();
    }
}
