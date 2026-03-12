package main.java.pl.kamil.streamapi;

import java.util.*;
import java.util.stream.Collectors;

public class StreamTaskRunner {

    public static void main(String[] args) {
        System.out.println("--- ZADANIE 1 ---");
        solveTask1();

        System.out.println("\n--- ZADANIE 2 ---");
        solveTask2();

        System.out.println("\n--- ZADANIE 3 ---");
        solveTask3();

        System.out.println("\n--- ZADANIE 4 ---");
        solveTask4();
    }

    private static void solveTask1() {
        List<Integer> numbers = Arrays.asList(15, 8, 23, 4, 42, 16, 11, 30, 7, 19, 2, 35, 28, 13, 6);
        List<Integer> result = numbers.stream()
                .filter(n -> n % 2 == 0)
                .sorted()
                .collect(Collectors.toList());
        System.out.println(result);
    }

    private static void solveTask2() {
        List<Product> products = Arrays.asList(
                new Product("Laptop Dell", "Elektronika", 3500.00),
                new Product("Mysz Logitech", "Elektronika", 150.00),
                new Product("Monitor Samsung", "Elektronika", 1200.00),
                new Product("Klawiatura Razer", "Elektronika", 450.00),
                new Product("Smartfon iPhone", "Elektronika", 4500.00),
                new Product("Krzesło biurowe", "Meble", 800.00),
                new Product("Słuchawki Sony", "Elektronika", 350.00),
                new Product("Tablet Samsung", "Elektronika", 2200.00),
                new Product("Biurko", "Meble", 1500.00),
                new Product("Drukarka HP", "Elektronika", 900.00)
        );

        List<String> result = products.stream()
                .filter(p -> "Elektronika".equals(p.getCategory()))
                .filter(p -> p.getPrice() > 1000)
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .map(Product::getName)
                .collect(Collectors.toList());
        System.out.println(result);
    }

    private static void solveTask3() {
        List<Employee> employees = Arrays.asList(
                new Employee("Anna Kowalska", "IT", 8000.00),
                new Employee("Jan Nowak", "IT", 7500.00),
                new Employee("Maria Wiśniewska", "HR", 4500.00),
                new Employee("Piotr Zieliński", "IT", 9000.00),
                new Employee("Katarzyna Lewandowska", "HR", 4800.00),
                new Employee("Tomasz Kamiński", "Sprzedaż", 5500.00),
                new Employee("Agnieszka Wójcik", "Sprzedaż", 6000.00),
                new Employee("Michał Kowalczyk", "IT", 8500.00),
                new Employee("Ewa Szymańska", "HR", 5000.00),
                new Employee("Paweł Dąbrowski", "Sprzedaż", 5800.00),
                new Employee("Magdalena Król", "Marketing", 6500.00),
                new Employee("Krzysztof Piotrowski", "Marketing", 7000.00),
                new Employee("Joanna Grabowska", "HR", 4200.00),
                new Employee("Adam Pawlak", "Sprzedaż", 6200.00)
        );

        Map<String, Double> result = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)
                ))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 5000)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Math.round(entry.getValue() * 100.0) / 100.0
                ));
        System.out.println(result);
    }

    private static void solveTask4() {
        List<Order> orders = Arrays.asList(
                new Order("ORD001", "ZREALIZOWANE", Arrays.asList(
                        new OrderItem("Laptop Dell", "Elektronika", 1, 3500),
                        new OrderItem("Mysz Logitech", "Elektronika", 2, 150),
                        new OrderItem("Biurko Ikea", "Meble", 1, 800)
                )),
                new Order("ORD002", "ZREALIZOWANE", Arrays.asList(
                        new OrderItem("Laptop Dell", "Elektronika", 1, 3500),
                        new OrderItem("Monitor Samsung", "Elektronika", 1, 1200),
                        new OrderItem("Krzesło Herman Miller", "Meble", 1, 2500)
                )),
                new Order("ORD004", "ZREALIZOWANE", Arrays.asList(
                        new OrderItem("Monitor Samsung", "Elektronika", 2, 1200),
                        new OrderItem("Klawiatura Razer", "Elektronika", 1, 450),
                        new OrderItem("Lampa LED", "Oświetlenie", 3, 120)
                )),
                new Order("ORD005", "ZREALIZOWANE", Arrays.asList(
                        new OrderItem("Laptop Dell", "Elektronika", 1, 3500),
                        new OrderItem("Biurko Ikea", "Meble", 1, 800),
                        new OrderItem("Lampa LED", "Oświetlenie", 2, 120)
                )),
                new Order("ORD006", "ZREALIZOWANE", Arrays.asList(
                        new OrderItem("Mysz Logitech", "Elektronika", 1, 150),
                        new OrderItem("Klawiatura Razer", "Elektronika", 1, 450),
                        new OrderItem("Krzesło Herman Miller", "Meble", 1, 2500)
                )),
                new Order("ORD008", "ZREALIZOWANE", Arrays.asList(
                        new OrderItem("Monitor Samsung", "Elektronika", 1, 1200),
                        new OrderItem("Lampa LED", "Oświetlenie", 1, 120),
                        new OrderItem("Dywan", "Dekoracje", 1, 350)
                )),
                new Order("ORD009", "ZREALIZOWANE", Arrays.asList(
                        new OrderItem("Mysz Logitech", "Elektronika", 3, 150),
                        new OrderItem("Biurko Ikea", "Meble", 1, 800)
                )),
                new Order("ORD010", "ZREALIZOWANE", Arrays.asList(
                        new OrderItem("Klawiatura Razer", "Elektronika", 1, 450),
                        new OrderItem("Krzesło Herman Miller", "Meble", 1, 2500),
                        new OrderItem("Dywan", "Dekoracje", 1, 350)
                ))
        );

        Map<String, List<ProductStats>> result = orders.stream()
                .filter(o -> "ZREALIZOWANE".equals(o.getStatus()))
                .flatMap(o -> o.getItems().stream())
                .collect(Collectors.groupingBy(
                        OrderItem::getCategory,
                        Collectors.groupingBy(OrderItem::getProductName, Collectors.counting())
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        categoryEntry -> categoryEntry.getValue().entrySet().stream()
                                .map(e -> new ProductStats(e.getKey(), e.getValue()))
                                .sorted(Comparator.comparing(ProductStats::getOrderCount).reversed())
                                .limit(3)
                                .collect(Collectors.toList())
                ));

        result.forEach((category, stats) -> System.out.println(category + " = " + stats));
    }

    // --- Static Nested Classes (Models) ---

    static class Product {
        private String name;
        private String category;
        private double price;

        public Product(String name, String category, double price) {
            this.name = name; this.category = category; this.price = price;
        }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public double getPrice() { return price; }
    }

    static class Employee {
        private String name;
        private String department;
        private double salary;

        public Employee(String name, String department, double salary) {
            this.name = name; this.department = department; this.salary = salary;
        }
        public String getDepartment() { return department; }
        public double getSalary() { return salary; }
    }

    static class OrderItem {
        private String productName;
        private String category;
        private int quantity;
        private double price;

        public OrderItem(String productName, String category, int quantity, double price) {
            this.productName = productName; this.category = category; this.quantity = quantity; this.price = price;
        }
        public String getProductName() { return productName; }
        public String getCategory() { return category; }
    }

    static class Order {
        private String orderId;
        private String status;
        private List<OrderItem> items;

        public Order(String orderId, String status, List<OrderItem> items) {
            this.orderId = orderId; this.status = status; this.items = items;
        }
        public String getStatus() { return status; }
        public List<OrderItem> getItems() { return items; }
    }

    static class ProductStats {
        private String productName;
        private long orderCount;

        public ProductStats(String productName, long orderCount) {
            this.productName = productName; this.orderCount = orderCount;
        }
        public long getOrderCount() { return orderCount; }
        @Override
        public String toString() { return productName + " (" + orderCount + ")"; }
    }
}