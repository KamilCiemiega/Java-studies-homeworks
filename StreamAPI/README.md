# Java Stream API - Practical Data Processing

This project contains solutions to four programming tasks of varying difficulty levels, designed to practice the functionalities of the **Java Stream API**.

## 🚀 Project Goal
The primary objective is to replace traditional imperative loops (`for`, `while`) with a declarative approach. This results in code that is more concise, readable, and easier to maintain.



---

## 🛠️ Tasks Overview

### Task 1: Even Number Filtering (Very Easy)
- **Operations:** `filter()`, `sorted()`, `toList()`.
- **Description:** Extracts even integers from a list, sorts them in ascending order, and returns them as a new list.

### Task 2: Product List Processing (Easy)
- **Operations:** Multiple `filter()` calls, `sorted()` with `Comparator.reversed()`, and `map()`.
- **Description:** Identifies products in the "Electronics" category priced over 1000 PLN, sorts them by price descending, and returns only their names.

### Task 3: Employee Data Analysis (Medium)
- **Operations:** `groupingBy()`, `averagingDouble()`, `entrySet().stream()`.
- **Description:** Groups employees by department to calculate the average salary. It rounds the result to two decimal places and filters out departments where the average salary is 5000 PLN or less.

### Task 4: E-commerce Order Analysis (Hard)
- **Operations:** `flatMap()`, nested `groupingBy()`, `counting()`, `sorted()`, and `limit()`.
- **Description:** Analyzes a complex order system to find the top 3 most frequently ordered products within each category, considering only "COMPLETED" (ZREALIZOWANE) orders.

---

## 💻 Tech Stack
* **Java 17+**
* **Java Stream API** (`java.util.stream` package)
* **Collectors** (for grouping, averaging, and transforming data)

---

## 📋 How to Run
1. Clone the repository or copy the source files.
2. Ensure you have the model classes from `Models.java` in your classpath.
3. Run the `main` method in `StreamExercise.java` to see the results printed to the console for each task.

> **Note:** These solutions strictly follow the requirement of using the Stream API and avoid using manual loops to ensure a functional programming style.