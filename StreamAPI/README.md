# Stream API Project - Assignment 2

This project contains solutions for data collection processing tasks using Java Stream API. All tasks have been implemented within the main class following the standard Maven project structure.

## System Requirements
- Java JDK 21
- Maven 3.6 or newer

## Project Structure
The project follows the standard Maven directory layout:
- `src/main/java/pl/kamil/streamapi/` - Java source files.
- `pom.xml` - Maven configuration file defining dependencies and compiler version.

## Tasks Included
1. Task 1: Filtering and sorting even numbers from a list.
2. Task 2: Filtering "Electronics" products priced over 1000 PLN and sorting them by price descending.
3. Task 3: Calculating average salaries per department and filtering departments with an average above 5000 PLN.
4. Task 4: Complex E-commerce analysis: filtering completed orders, flattening product lists, grouping by category, and finding the top 3 most popular products per category.

## How to Run

### Option 1: Using IntelliJ IDEA (Recommended)
1. Open IntelliJ IDEA.
2. Go to File > Open and select the folder containing the pom.xml file.
3. Wait for IntelliJ to load the Maven dependencies.
4. Navigate to src/main/java/pl/kamil/streamapi/StreamTaskRunner.java.
5. Click the green Run icon next to the main method.

### Option 2: Using Terminal (Maven)
If you have Maven installed on your system, you can run the project directly from the terminal:

1. Navigate to the project root directory:
   cd StreamAPI
2. Compile the project:
   mvn clean compile
3. Execute the main class:
   mvn exec:java -Dexec.mainClass="pl.kamil.streamapi.StreamTaskRunner"