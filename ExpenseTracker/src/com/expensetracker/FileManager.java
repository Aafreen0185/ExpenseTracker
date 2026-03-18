package com.expensetracker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles reading and writing expenses to a CSV file.
 * Demonstrates file I/O with proper error handling.
 */
public class FileManager {

    private final String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads all expenses from the CSV file.
     * Returns an empty list if the file doesn't exist yet.
     */
    public List<Expense> loadExpenses() {
        List<Expense> expenses = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return expenses; // Fresh start
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("#")) { // skip comments/blank lines
                    try {
                        expenses.add(Expense.fromCsv(line));
                    } catch (Exception e) {
                        System.out.println("  [Warning] Skipping corrupted entry: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("  [Error] Could not read data file: " + e.getMessage());
        }

        return expenses;
    }

    /**
     * Saves all expenses to the CSV file, overwriting previous content.
     */
    public void saveExpenses(List<Expense> expenses) {
        File file = new File(filePath);

        // Create parent directories if they don't exist
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("# ExpenseTracker Data File - do not edit manually");
            writer.newLine();
            for (Expense expense : expenses) {
                writer.write(expense.toCsv());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("  [Error] Could not save data: " + e.getMessage());
        }
    }
}
