# 💰 Expense Tracker

A command-line **Expense Tracking application** built in Java, demonstrating core Object-Oriented Programming principles, Collections, File I/O, and clean software design.

---

## 🚀 Features

- ➕ **Add expenses** with description, amount, category, and date
- 📋 **View all expenses** in a formatted table
- 🗂️ **Filter by category** (Food, Transport, Education, etc.)
- 📊 **Monthly summary** — spending breakdown by category for any month
- 🔍 **Search expenses** by keyword
- 🗑️ **Delete** entries by ID
- 💾 **Persistent storage** — data saved to CSV, survives app restarts

---

## 🏗️ Project Structure

```
ExpenseTracker/
├── src/
│   └── com/expensetracker/
│       ├── Main.java            # Entry point & CLI menu
│       ├── ExpenseTracker.java  # Core business logic
│       ├── Expense.java         # Expense model (OOP)
│       ├── Category.java        # Enum for expense categories
│       └── FileManager.java     # CSV file read/write (File I/O)
├── data/
│   └── expenses.csv             # Auto-generated data file
└── README.md
```

---

## 🧠 Concepts Demonstrated

| Concept | Where Used |
|---|---|
| OOP (Classes, Encapsulation) | `Expense.java`, `ExpenseTracker.java` |
| Enums | `Category.java` |
| Collections (`List`, `Map`) | `ExpenseTracker.java` |
| File I/O (`BufferedReader/Writer`) | `FileManager.java` |
| Java Streams & Lambdas | Filtering, grouping, aggregating |
| Input Validation | `Main.java` — all user inputs validated |
| Exception Handling | Throughout — `try/catch` with user-friendly messages |

---

## ▶️ How to Run

### Prerequisites
- Java 11 or higher installed
- Terminal / Command Prompt

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/Aafreen0185/ExpenseTracker.git
cd ExpenseTracker

# 2. Compile
javac -d out src/com/expensetracker/*.java

# 3. Run
java -cp out com.expensetracker.Main
```

---

## 🖥️ Sample Output

```
  ╔══════════════════════════════════════╗
  ║       💰 EXPENSE TRACKER v1.0        ║
  ║   Track. Analyse. Stay in Control.   ║
  ╚══════════════════════════════════════╝

  ┌──────────────────────────────────────┐
  │              MAIN MENU               │
  ├──────────────────────────────────────┤
  │  [1] Add Expense                     │
  │  [2] View All Expenses               │
  │  [3] View by Category                │
  │  [4] Monthly Summary                 │
  │  [5] Delete Expense                  │
  │  [6] Search Expenses                 │
  │  [0] Exit                            │
  └──────────────────────────────────────┘

Monthly Summary: March 2026
  ══════════════════════════════════════
  Food & Dining        :   1250.00 INR
  Transport            :    430.00 INR
  Education            :   2000.00 INR
  ──────────────────────────────────────
  TOTAL                :   3680.00 INR
  ══════════════════════════════════════
```

---

## 📁 Data Storage

Expenses are saved automatically to `data/expenses.csv` after every change. Format:

```
# ExpenseTracker Data File
1,Lunch at college canteen,120.0,FOOD,2026-03-15
2,Bus pass recharge,500.0,TRANSPORT,2026-03-15
3,Programming book,850.0,EDUCATION,2026-03-16
```

---

## 🗂️ Available Categories

| # | Category |
|---|---|
| 1 | Food & Dining |
| 2 | Transport |
| 3 | Education |
| 4 | Entertainment |
| 5 | Health & Medical |
| 6 | Shopping |
| 7 | Utilities |
| 8 | Other |

---

## 👩‍💻 Author

**Aafreen Sheriff M**  
B.E. Computer Science Engineering — Chennai Institute of Technology and Applied Research  
[LinkedIn](https://www.linkedin.com/in/aafreen-sheriff-m-206317291) • [GitHub](https://github.com/Aafreen0185)

---

## 📄 License

This project is open source under the [MIT License](LICENSE).
