package ch.noseryoung.blj;

import java.util.Scanner;

public class Simulation {

    private VendingMachine machine;
    private Scanner scanner;

    public Simulation() {
        this.scanner = new Scanner(System.in);
        this.machine = new VendingMachine();
    }

    public void run() {
        System.out.println("hi welcome to the Snack Machine!");

        while (true) {
            System.out.println("\nenter a product code, the secret key, or type exit:");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("exit")) {
                System.out.println("byebye");
                break;

            } else if (input.equals(machine.getSecretKey().toLowerCase())) {
                handleMaintenance();

            } else {
                handlePurchase(input);
            }
        }
    }

    private void handlePurchase(String code) {
        Product product = machine.findProduct(code);

        if (product == null) {
            System.out.println("product not found");
            return;
        }

        if (product.getStock() == 0) {
            System.out.println("sorry, " + product.getName() + " is out of stock");
            return;
        }

        // the machine already knows the name and price — no need to ask the user
        System.out.println("you selected: " + product.getName());
        System.out.println("price: " + product.getPrice());
        System.out.println("insert money or type 'cancel':");

        double inserted = 0.0;

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("cancel")) {
                if (inserted > 0) {
                    System.out.println("returning " + inserted);
                }
                System.out.println("purchase cancelled.");
                return;
            }

            try {
                inserted += Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("please enter a valid amount.");
                continue;
            }

            if (inserted >= product.getPrice()) {
                double change = inserted - product.getPrice();
                // reduce stock by 1 using the existing setStock()
                product.setStock(product.getStock() - 1);
                System.out.println("enjoy your " + product.getName() + "!");
                if (change > 0) {
                    System.out.println("your change: " + change);
                }
                return;

            } else {
                double stillNeeded = product.getPrice() - inserted;
                System.out.println("you are missing " + stillNeeded);
            }
        }
    }

    private void handleMaintenance() {
        System.out.println("\n--- Maintenance Menu ---");
        System.out.println("1 - Initial fill");
        System.out.println("2 - Restock a product");
        System.out.println("3 - Replace a product");
        System.out.println("4 - Change a price");
        System.out.println("5 - Exit maintenance");

        String choice = scanner.nextLine().trim().toLowerCase();

        switch (choice) {
            case "1":
                machine.initialFill();
                break;

            case "2":
                System.out.println("enter product code:");
                String restockCode = scanner.nextLine().trim();
                Product toRestock = machine.findProduct(restockCode);
                if (toRestock == null) {
                    System.out.println("product not found.");
                    break;
                }
                System.out.println("how many to add?");
                int amount = Integer.parseInt(scanner.nextLine());
                int newStock = toRestock.getStock() + amount;
                if (newStock > 30) {
                    System.out.println("cannot restock over 30 items. maximum allowed: " + (30 - toRestock.getStock()));
                    break;
                }
                toRestock.setStock(newStock);

                break;

            case "3":
                System.out.println("enter product code to replace:");
                String replaceCode = scanner.nextLine().trim();
                Product toReplace = machine.findProduct(replaceCode);
                if (toReplace == null) {
                    System.out.println("product not found.");
                    break;
                }
                System.out.println("new product name:");
                String newName = scanner.nextLine().trim();
                System.out.println("new price:");
                double newPrice = Double.parseDouble(scanner.nextLine().trim());
                // update using the setters from Product
                toReplace.setName(newName);
                toReplace.setPrice(newPrice);
                System.out.println("product replaced!");
                break;

            case "4":
                System.out.println("enter product code:");
                String priceCode = scanner.nextLine().trim();
                Product toChange = machine.findProduct(priceCode);
                if (toChange == null) {
                    System.out.println("product not found.");
                    break;
                }
                System.out.println("new price:");
                double price = Double.parseDouble(scanner.nextLine().trim());
                toChange.setPrice(price);
                System.out.println("price updated!");
                break;

            case "5":
                System.out.println("exiting maintenance.");
                break;

            default:
                System.out.println("unknown option.");
        }
    }
}