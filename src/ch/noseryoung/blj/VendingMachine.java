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
        if (amount > 0) {
            this.insertedMoney += amount;
        }
    }

    public boolean selectProduct(String code) {

        for (Product p : products) {
            if (p.getCode().equals(code)) {

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
            products.add(new Product("A2", "Chips", 1.80, 7));
            products.add(new Product("A3", "Mars-Riegel", 1.50, 8));

            this.initialFillDone = true;
            System.out.println("Automat wurde gefüllt.");
        } else {
            System.out.println("Initialisierung schon durchgeführt.");
        }
    }

    public Product findProduct(String code) {
        for (Product p : products) {
            if (p.getCode().equals(code)) {
                return p;
            }
        }
        return null;
    }

    public boolean replaceProduct(String code, String newName, double newPrice, int newStock) {
        if (code == null || code.trim().isEmpty()) {
            return false;
        }

        if (newName == null || newName.trim().isEmpty()) {
            return false;
        }

        if (newPrice <= 0) {
            return false;
        }

        if (newStock < 0) {
            return false;
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

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

}
