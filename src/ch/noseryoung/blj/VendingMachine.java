package ch.noseryoung.blj;


import java.util.*;

public class VendingMachine {

    private String secretKey;
    private double insertedMoney;
    private boolean initialFillDone;
    private List<Product> products;

    public VendingMachine() {
        this.secretKey = "RandomPassword";
        this.insertedMoney = 0.0;
        this.initialFillDone = false;
        this.products = new ArrayList<>();
    }

    public void insertMoney(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The amount entered must be greater than 0.");
        }
        this.insertedMoney += amount;
    }

    public boolean selectProduct(String code) {
        for (Product p : products) {
            if (p.getCode().equalsIgnoreCase(code)) {
                if (this.insertedMoney >= p.getPrice() && p.getStock() > 0) {
                    this.insertedMoney -= p.getPrice();
                    p.setStock(p.getStock() - 1);

                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public double cancelProduct() {
        double moneyToReturn = this.insertedMoney;
        this.insertedMoney = 0.0;
        return moneyToReturn;
    }

    public void initialFill() {
        if (!this.initialFillDone) {
            products.add(new Product("A1", "Cola", 2.50, 10));
            products.add(new Product("A2", "Iced Tea", 2.30, 10));
            products.add(new Product("A3", "Water", 1.50, 15));
            products.add(new Product("B1", "Chocolate Bar (Snickers)", 1.80, 12));
            products.add(new Product("B2", "Chocolate Bar (Twix)", 1.80, 12));
            products.add(new Product("B3", "Gummy Bears", 2.10, 8));
            products.add(new Product("C1", "Potato Chips", 2.00, 7));
            products.add(new Product("C2", "Pretzel Sticks", 1.70, 10));
            products.add(new Product("C3", "Apple Slices", 2.40, 5));

            this.initialFillDone = true;
            System.out.println("The vending machine has been restocked.");
        } else {
            System.out.println("Initialization has already been performed.");
        }
    }

    public Product findProduct(String code) {
        for (Product p : products) {
            if (p.getCode().equalsIgnoreCase(code)) {
                return p;
            }
        }
        return null;
    }

    public boolean refill(String code, int quantity) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: Invalid product code.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Error: The quantity must be greater than 0.");
        }
        Product p = findProduct(code);
        if (p != null) {
            p.setStock(p.getStock() + quantity);
            System.out.println("Stock for " + code + " increased by " + quantity + ".");
            return true;
        }
        return false;
    }

    public boolean replaceProduct(String code, String newName, double newPrice, int newStock) {
        if (code == null || code.trim().isEmpty()) throw new IllegalArgumentException("Invalid code");
        if (newName == null || newName.trim().isEmpty()) throw new IllegalArgumentException("Name invalid");
        if (newPrice <= 0) throw new IllegalArgumentException("The price must be greater than 0");
        if (newStock < 0) throw new IllegalArgumentException("The stock must not be negative");

        Product p = findProduct(code);
        if (p != null) {
            p.setName(newName);
            p.setPrice(newPrice);
            p.setStock(newStock);
            return true;
        }
        return false;
    }

    public boolean changePrice(String code, double newPrice) {
        if (newPrice <= 0) {
            throw new IllegalArgumentException("The price must be greater than 0.");
        }

        if ((newPrice * 10) % 1 != 0) {
            throw new IllegalArgumentException("Preis darf nur eine Nachkommastelle haben.");
        }

        Product p = findProduct(code);
        if (p != null) {
            p.setPrice(newPrice);
            return true;
        }
        return false;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        if (secretKey == null || secretKey.trim().length() < 4) {
            throw new IllegalArgumentException("The secret key must be at least 4 characters long.");
        }
        this.secretKey = secretKey;
    }

}
