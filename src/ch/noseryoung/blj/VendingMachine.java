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
            throw new IllegalArgumentException("Der eingeworfene Betrag muss grösser als 0 sein.");
        }
        this.insertedMoney += amount;
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

    public Product findProduct(String code) {
        for (Product p : products) {
            if (p.getCode().equals(code)) {
                return p;
            }
        }
        return null;
    }

    public boolean refill(String code, int quantity) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Fehler: Ungültiger Produkt-Code.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Fehler: Menge muss grösser als 0 sein.");
        }
        Product p = findProduct(code);
        if (p != null) {
            p.setStock(p.getStock() + quantity);
            System.out.println("Bestand für " + code + " um " + quantity + " erhöht.");
            return true;
        }
        return false;
    }

    public boolean replaceProduct(String code, String newName, double newPrice, int newStock) {
        if (code == null || code.trim().isEmpty()) throw new IllegalArgumentException("Code ungültig");
        if (newName == null || newName.trim().isEmpty()) throw new IllegalArgumentException("Name ungültig");
        if (newPrice <= 0) throw new IllegalArgumentException("Preis muss > 0 sein");
        if (newStock < 0) throw new IllegalArgumentException("Stock darf nicht negativ sein");

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
            throw new IllegalArgumentException("Preis muss grösser als 0 sein.");
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
            throw new IllegalArgumentException("Secret Key muss mindestens 4 Zeichen lang sein.");
        }
        this.secretKey = secretKey;
    }

}
