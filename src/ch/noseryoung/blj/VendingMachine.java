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

        System.out.println("VendingMachine bereit.");
    }
}
