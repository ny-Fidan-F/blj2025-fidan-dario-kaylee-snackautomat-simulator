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

        System.out.println("you selected: " + product.getName());
        System.out.println("price: " + format(product.getPrice()));
        System.out.println("insert money or type 'cancel':");

        double inserted = 0.0;

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("cancel")) {
                if (inserted > 0) {
                    System.out.println("returning " + format(inserted));
                }
                System.out.println("purchase cancelled.");
                return;
            }

            double coin = readNumber(input);

            if (coin == -1) {
                System.out.println("please enter a valid amount, like 1.0 or 0.5");
                continue;
            }

            inserted = inserted + coin;

            if (inserted >= product.getPrice()) {
                double change = inserted - product.getPrice();
                product.setStock(product.getStock() - 1);
                System.out.println("enjoy your " + product.getName() + "!");
                if (change > 0) {
                    System.out.println("your change: " + format(change));
                }
                return;

            } else {
                double stillNeeded = product.getPrice() - inserted;
                System.out.println("you are missing " + format(stillNeeded));
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
                System.out.println("How many to add?");
                String amountInput = scanner.nextLine().trim();
                double amountAsNumber = readNumber(amountInput);
                if (amountAsNumber == -1) {
                    System.out.println("that's not a valid number.");
                    break;
                }
                amountAsNumber = Math.round(amountAsNumber * 100.0) / 100.0;
                int toAdd = (int) amountAsNumber;
                int currentStock = toRestock.getStock();
                if (currentStock >= 30) {
                    System.out.println("cannot restock over 30 items. maximum allowed: " + (30 - currentStock));
                    break;
                }
                // Only add as many as fit up to 30, silently capping if needed
                int actuallyAdded = Math.min(toAdd, 30 - currentStock);
                toRestock.setStock(currentStock + actuallyAdded);
                System.out.println("Restocked " + actuallyAdded + " items. New stock: " + toRestock.getStock());
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
                double newPrice = readNumber(scanner.nextLine().trim());
                if (newPrice == -1 || !isValidPrice(newPrice)) {
                    System.out.println("that's not a valid price.");
                    break;
                }
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
                double price = readNumber(scanner.nextLine().trim());
                if (price == -1 || !isValidPrice(price)) {
                    System.out.println("that's not a valid price.");
                    break;
                }
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


    private double readNumber(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return -1;
        }
    }


    private boolean isValidPrice(double price) {
        double rounded = Math.round(price * 100.0) / 100.0;
        return price == rounded;
    }


    private String format(double number) {
        return String.format("%.2f", number);
    }
}