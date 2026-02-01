package pl.kamil.streamapi;

import java.util.*;

public class Task2Products {
    public static void run() {
        List<Product> products = Arrays.asList(
                new Product("Laptop Dell", "Elektronika", 3500),
                new Product("Mysz Logitech", "Elektronika", 150),
                new Product("Monitor Samsung", "Elektronika", 1200),
                new Product("Klawiatura Razer", "Elektronika", 450),
                new Product("Smartfon iPhone", "Elektronika", 4500),
                new Product("Krzesło biurowe", "Meble", 800),
                new Product("Tablet Samsung", "Elektronika", 2200)
        );

        List<String> result = products.stream()
                .filter(p -> p.getCategory().equals("Elektronika"))
                .filter(p -> p.getPrice() > 1000)
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .map(Product::getName)
                .toList();

        System.out.println(result);
    }
}
