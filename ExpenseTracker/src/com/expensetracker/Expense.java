package com.expensetracker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single expense entry.
 */
public class Expense {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private int id;
    private String description;
    private double amount;
    private Category category;
    private LocalDate date;

    public Expense(int id, String description, double amount, Category category, LocalDate date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    // Getters
    public int getId() { return id; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public Category getCategory() { return category; }
    public LocalDate getDate() { return date; }

    /**
     * Converts this expense to a CSV line for file storage.
     */
    public String toCsv() {
        return id + "," + description + "," + amount + "," + category.name() + "," + date.format(FORMATTER);
    }

    /**
     * Parses a CSV line back into an Expense object.
     */
    public static Expense fromCsv(String csvLine) {
        String[] parts = csvLine.split(",", 5);
        int id = Integer.parseInt(parts[0].trim());
        String description = parts[1].trim();
        double amount = Double.parseDouble(parts[2].trim());
        Category category = Category.valueOf(parts[3].trim());
        LocalDate date = LocalDate.parse(parts[4].trim(), FORMATTER);
        return new Expense(id, description, amount, category, date);
    }

    @Override
    public String toString() {
        return String.format("| %-4d | %-25s | %-12s | %8.2f INR | %s |",
                id, description, category.getDisplayName(), amount, date.format(FORMATTER));
    }
}
