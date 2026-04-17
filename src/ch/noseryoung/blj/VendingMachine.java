package ch.noseryoung.blj;


import java.util.*;

public class VendingMachine {
    private static final int MAX_STOCK_PER_PRODUCT = 30;
    private static final double MONEY_SCALE = 100.0;


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

        double roundedAmount = Math.round(amount * MONEY_SCALE) / MONEY_SCALE;
        if (Double.compare(amount, roundedAmount) != 0) {
            throw new IllegalArgumentException("The amount must have at most 2 decimal places.");
        }
        this.insertedMoney += amount;
    }

    public Product findProduct(String code) {
        for (Product p : products) {
            if (p.getCode().equalsIgnoreCase(code)) {
                return p;
            }
        }
        return null;
    }


    public Product selectProduct(String code) {
        Product p = findProduct(code);
        if (p != null && this.insertedMoney >= p.getPrice() && p.getStock() > 0) {
            this.insertedMoney -= p.getPrice();
            p.setStock(p.getStock() - 1);
            return p;
        }
        return null;
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


    public boolean refill(String code, int quantity) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: Invalid product code.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Error: The quantity must be greater than 0.");
        }
        Product p = findProduct(code);
        if (p != null) {
            if (p.getStock() + quantity > MAX_STOCK_PER_PRODUCT) {
                throw new IllegalArgumentException(
                        "Error: Only " + (MAX_STOCK_PER_PRODUCT - p.getStock()) + " items fit into slot " + code + "."
                );
            }
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
        if (newStock > MAX_STOCK_PER_PRODUCT) {
            throw new IllegalArgumentException("The stock must not exceed " + MAX_STOCK_PER_PRODUCT + ".");
        }

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

    public List<Product> getAllProducts() {
        return products;
    }

    public int getMaxStockPerProduct() {
        return MAX_STOCK_PER_PRODUCT;
    }
    public double getInsertedMoney() {
        return insertedMoney;
    }

}
