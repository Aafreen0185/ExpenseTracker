package com.expensetracker;

/**
 * Expense categories with display-friendly names.
 */
public enum Category {
    FOOD("Food & Dining"),
    TRANSPORT("Transport"),
    EDUCATION("Education"),
    ENTERTAINMENT("Entertainment"),
    HEALTH("Health & Medical"),
    SHOPPING("Shopping"),
    UTILITIES("Utilities"),
    OTHER("Other");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Displays all categories with their index for user selection.
     */
    public static void printAll() {
        Category[] values = Category.values();
        for (int i = 0; i < values.length; i++) {
            System.out.printf("  [%d] %s%n", i + 1, values[i].getDisplayName());
        }
    }

    /**
     * Returns the Category at the given 1-based index.
     */
    public static Category fromIndex(int index) {
        Category[] values = Category.values();
        if (index < 1 || index > values.length) {
            throw new IllegalArgumentException("Invalid category index: " + index);
        }
        return values[index - 1];
    }
}
