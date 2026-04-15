package ch.noseryoung.blj;


import java.util.*;

public class VendingMachine {

    private String secretKey;
    private double insertedMoney;
    private boolean initalFillDone;
    private List<Product> products;

    public VendingMachine() {

        this.secretKey = "RandomPassword";

        this.insertedMoney = 0.0;
        this.initalFillDone = false;
        this.products = new ArrayList<>();

    }

    public void insertMoney(double amount) {
        if (amount > 0) {
            this.insertedMoney += amount;
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
}
