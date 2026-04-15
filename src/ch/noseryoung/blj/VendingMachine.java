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
            products.add(new Product("A2", "Eistee", 2.30, 10));
            products.add(new Product("A3", "Wasser", 1.50, 15));
            products.add(new Product("B1", "Snickers", 1.80, 12));
            products.add(new Product("B2", "Twix", 1.80, 12));
            products.add(new Product("B3", "Gummibärchen", 2.10, 8));
            products.add(new Product("C1", "Chips", 2.00, 7));
            products.add(new Product("C2", "Salzstangen", 1.70, 10));
            products.add(new Product("C3", "Apfelschnitze", 2.40, 5));

            this.initialFillDone = true;
            System.out.println("Automat wurde gefüllt.");
        } else {
            System.out.println("Initialisierung schon durchgeführt.");
        }
    }

    private Product findProduct(String code) {
        for (Product p : products) {
            if (p.getCode().equals(code)) {
                return p;
            }
        }
        return null;
    }

    public void replaceProduct(String code, String newName, double newPrice, int newStock) {
       Product p = findProduct(code);

        if (p != null) {
            p.setName(newName);
            p.setPrice(newPrice);
            p.setStock(newStock);
        }
    }

    public void changePrice(String code, double newPrice) {
        Product p = findProduct(code);

        if (p != null) {
            p.setPrice(newPrice);
        } else {
            System.out.println("Produkt nicht gefunden.");
        }
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public double getInsertedMoney() {
        return this.insertedMoney;
    }
}
