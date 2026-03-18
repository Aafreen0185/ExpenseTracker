package com.expensetracker;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Core business logic for the Expense Tracker.
 * Manages the in-memory list and delegates persistence to FileManager.
 */
public class ExpenseTracker {

    private final List<Expense> expenses;
    private final FileManager fileManager;
    private int nextId;

    public ExpenseTracker(String dataFilePath) {
        this.fileManager = new FileManager(dataFilePath);
        this.expenses = fileManager.loadExpenses();
        this.nextId = expenses.stream()
                .mapToInt(Expense::getId)
                .max()
                .orElse(0) + 1;
    }

    // ─── ADD ────────────────────────────────────────────────────────────────

    public void addExpense(String description, double amount, Category category, LocalDate date) {
        Expense expense = new Expense(nextId++, description, amount, category, date);
        expenses.add(expense);
        fileManager.saveExpenses(expenses);
        System.out.println("\n  ✔ Expense added successfully! (ID: " + expense.getId() + ")");
    }

    // ─── VIEW ALL ───────────────────────────────────────────────────────────

    public void viewAll() {
        if (expenses.isEmpty()) {
            System.out.println("\n  No expenses recorded yet.");
            return;
        }
        printHeader();
        expenses.forEach(System.out::println);
        printFooter();
        System.out.printf("%n  Total: %.2f INR across %d expense(s)%n", getTotalAmount(), expenses.size());
    }

    // ─── FILTER BY CATEGORY ─────────────────────────────────────────────────

    public void viewByCategory(Category category) {
        List<Expense> filtered = expenses.stream()
                .filter(e -> e.getCategory() == category)
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("\n  No expenses found for category: " + category.getDisplayName());
            return;
        }

        System.out.println("\n  Category: " + category.getDisplayName());
        printHeader();
        filtered.forEach(System.out::println);
        printFooter();
        double total = filtered.stream().mapToDouble(Expense::getAmount).sum();
        System.out.printf("  Total: %.2f INR (%d expense(s))%n", total, filtered.size());
    }

    // ─── MONTHLY SUMMARY ────────────────────────────────────────────────────

    public void monthlySummary(int year, int month) {
        List<Expense> monthly = expenses.stream()
                .filter(e -> e.getDate().getYear() == year && e.getDate().getMonthValue() == month)
                .collect(Collectors.toList());

        String monthName = Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        System.out.printf("%n  ══════════════════════════════════════%n");
        System.out.printf("   Monthly Summary: %s %d%n", monthName, year);
        System.out.printf("  ══════════════════════════════════════%n");

        if (monthly.isEmpty()) {
            System.out.println("  No expenses recorded for this month.");
            return;
        }

        // Group by category and sum
        Map<Category, Double> byCategory = new LinkedHashMap<>();
        for (Category cat : Category.values()) {
            double sum = monthly.stream()
                    .filter(e -> e.getCategory() == cat)
                    .mapToDouble(Expense::getAmount)
                    .sum();
            if (sum > 0) byCategory.put(cat, sum);
        }

        byCategory.forEach((cat, total) ->
                System.out.printf("  %-20s : %8.2f INR%n", cat.getDisplayName(), total));

        System.out.printf("  ──────────────────────────────────────%n");
        double grandTotal = monthly.stream().mapToDouble(Expense::getAmount).sum();
        System.out.printf("  %-20s : %8.2f INR%n", "TOTAL", grandTotal);
        System.out.printf("  ══════════════════════════════════════%n");
        System.out.printf("  Entries this month: %d%n", monthly.size());
    }

    // ─── DELETE ─────────────────────────────────────────────────────────────

    public void deleteExpense(int id) {
        Optional<Expense> found = expenses.stream().filter(e -> e.getId() == id).findFirst();
        if (found.isPresent()) {
            expenses.remove(found.get());
            fileManager.saveExpenses(expenses);
            System.out.println("\n  ✔ Expense ID " + id + " deleted successfully.");
        } else {
            System.out.println("\n  [Error] No expense found with ID: " + id);
        }
    }

    // ─── SEARCH ─────────────────────────────────────────────────────────────

    public void searchExpenses(String keyword) {
        String lower = keyword.toLowerCase();
        List<Expense> results = expenses.stream()
                .filter(e -> e.getDescription().toLowerCase().contains(lower))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("\n  No expenses found matching: \"" + keyword + "\"");
            return;
        }

        System.out.println("\n  Search results for: \"" + keyword + "\"");
        printHeader();
        results.forEach(System.out::println);
        printFooter();
    }

    // ─── HELPERS ────────────────────────────────────────────────────────────

    private double getTotalAmount() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    private void printHeader() {
        System.out.println("\n  " + "─".repeat(75));
        System.out.printf("  | %-4s | %-25s | %-12s | %12s | %s |%n",
                "ID", "Description", "Category", "Amount", "Date      ");
        System.out.println("  " + "─".repeat(75));
    }

    private void printFooter() {
        System.out.println("  " + "─".repeat(75));
    }
}
