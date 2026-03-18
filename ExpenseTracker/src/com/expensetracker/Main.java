package com.expensetracker;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main entry point for the Expense Tracker CLI application.
 * Handles all user interaction and input validation.
 */
public class Main {

    private static final String DATA_FILE = "data/expenses.csv";
    private static Scanner scanner = new Scanner(System.in);
    private static ExpenseTracker tracker;

    public static void main(String[] args) {
        tracker = new ExpenseTracker(DATA_FILE);
        printWelcome();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("  Enter choice: ");
            System.out.println();

            switch (choice) {
                case 1 -> handleAddExpense();
                case 2 -> tracker.viewAll();
                case 3 -> handleViewByCategory();
                case 4 -> handleMonthlySummary();
                case 5 -> handleDelete();
                case 6 -> handleSearch();
                case 0 -> {
                    System.out.println("  Goodbye! Your expenses are saved. 👋");
                    running = false;
                }
                default -> System.out.println("  [Error] Invalid option. Please choose from the menu.");
            }
            System.out.println();
        }

        scanner.close();
    }

    // ─── HANDLERS ───────────────────────────────────────────────────────────

    private static void handleAddExpense() {
        System.out.println("  ── Add New Expense ──────────────────");

        System.out.print("  Description: ");
        String description = scanner.nextLine().trim();
        if (description.isEmpty()) {
            System.out.println("  [Error] Description cannot be empty.");
            return;
        }
        // Prevent commas (would break CSV)
        if (description.contains(",")) {
            System.out.println("  [Error] Description cannot contain commas.");
            return;
        }

        double amount = readDouble("  Amount (INR): ");
        if (amount <= 0) {
            System.out.println("  [Error] Amount must be greater than zero.");
            return;
        }

        System.out.println("  Select Category:");
        Category.printAll();
        int catIndex = readInt("  Category number: ");
        Category category;
        try {
            category = Category.fromIndex(catIndex);
        } catch (IllegalArgumentException e) {
            System.out.println("  [Error] " + e.getMessage());
            return;
        }

        System.out.print("  Date (YYYY-MM-DD) or press Enter for today: ");
        String dateInput = scanner.nextLine().trim();
        LocalDate date;
        if (dateInput.isEmpty()) {
            date = LocalDate.now();
        } else {
            try {
                date = LocalDate.parse(dateInput);
            } catch (DateTimeParseException e) {
                System.out.println("  [Error] Invalid date format. Use YYYY-MM-DD.");
                return;
            }
        }

        tracker.addExpense(description, amount, category, date);
    }

    private static void handleViewByCategory() {
        System.out.println("  ── View by Category ─────────────────");
        System.out.println("  Select Category:");
        Category.printAll();
        int catIndex = readInt("  Category number: ");
        try {
            Category category = Category.fromIndex(catIndex);
            tracker.viewByCategory(category);
        } catch (IllegalArgumentException e) {
            System.out.println("  [Error] " + e.getMessage());
        }
    }

    private static void handleMonthlySummary() {
        System.out.println("  ── Monthly Summary ──────────────────");
        int year = readInt("  Year (e.g. 2025): ");
        int month = readInt("  Month (1-12): ");
        if (month < 1 || month > 12) {
            System.out.println("  [Error] Month must be between 1 and 12.");
            return;
        }
        if (year < 2000 || year > 2100) {
            System.out.println("  [Error] Please enter a valid year.");
            return;
        }
        tracker.monthlySummary(year, month);
    }

    private static void handleDelete() {
        System.out.println("  ── Delete Expense ───────────────────");
        tracker.viewAll();
        int id = readInt("\n  Enter ID to delete (0 to cancel): ");
        if (id == 0) {
            System.out.println("  Cancelled.");
            return;
        }
        tracker.deleteExpense(id);
    }

    private static void handleSearch() {
        System.out.println("  ── Search Expenses ──────────────────");
        System.out.print("  Enter keyword: ");
        String keyword = scanner.nextLine().trim();
        if (keyword.isEmpty()) {
            System.out.println("  [Error] Please enter a search keyword.");
            return;
        }
        tracker.searchExpenses(keyword);
    }

    // ─── UI HELPERS ─────────────────────────────────────────────────────────

    private static void printWelcome() {
        System.out.println("\n  ╔══════════════════════════════════════╗");
        System.out.println("  ║       💰 EXPENSE TRACKER v1.0        ║");
        System.out.println("  ║   Track. Analyse. Stay in Control.   ║");
        System.out.println("  ╚══════════════════════════════════════╝");
        System.out.println("  Data file: " + DATA_FILE);
        System.out.println();
    }

    private static void printMenu() {
        System.out.println("  ┌──────────────────────────────────────┐");
        System.out.println("  │              MAIN MENU               │");
        System.out.println("  ├──────────────────────────────────────┤");
        System.out.println("  │  [1] Add Expense                     │");
        System.out.println("  │  [2] View All Expenses               │");
        System.out.println("  │  [3] View by Category                │");
        System.out.println("  │  [4] Monthly Summary                 │");
        System.out.println("  │  [5] Delete Expense                  │");
        System.out.println("  │  [6] Search Expenses                 │");
        System.out.println("  │  [0] Exit                            │");
        System.out.println("  └──────────────────────────────────────┘");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine(); // consume newline
                return value;
            } catch (InputMismatchException e) {
                scanner.nextLine(); // clear bad input
                System.out.println("  [Error] Please enter a valid number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine(); // consume newline
                return value;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("  [Error] Please enter a valid amount.");
            }
        }
    }
}
