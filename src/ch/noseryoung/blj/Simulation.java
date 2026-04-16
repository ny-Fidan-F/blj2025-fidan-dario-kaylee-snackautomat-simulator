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

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("cancel")) {
                double returnedMoney = machine.cancelProduct();
                if (returnedMoney > 0) {
                    System.out.println("returning " + format(returnedMoney));
                }
                System.out.println("purchase cancelled.");
                return;
            }

            double coin = readNumber(input);

            if (coin == -1) {
                System.out.println("please enter a valid amount, like 1.0 or 0.5");
                continue;
            }

            try {
                machine.insertMoney(coin);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (machine.selectProduct(code)) {
                double change = machine.cancelProduct();
                System.out.println("enjoy your " + product.getName() + "!");
                if (change > 0) {
                    System.out.println("your change: " + format(change));
                }
                return;

            } else {
                double stillNeeded = product.getPrice() - machine.getInsertedMoney();
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
                Integer quantity = readInteger(scanner.nextLine().trim());
                if (quantity == null) {
                    System.out.println("that's not a valid integer.");
                    break;
                }
                try {
                    machine.refill(restockCode, quantity);
                    System.out.println("New stock: " + toRestock.getStock() + "/" + machine.getMaxStockPerProduct());
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
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
                if (newPrice == -1) {
                    System.out.println("that's not a valid price.");
                    break;
                }
                System.out.println("new stock:");
                Integer replacementStock = readInteger(scanner.nextLine().trim());
                if (replacementStock == null) {
                    System.out.println("that's not a valid integer.");
                    break;
                }
                try {
                    machine.replaceProduct(replaceCode, newName, newPrice, replacementStock);
                    System.out.println("product replaced!");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
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
                if (price == -1) {
                    System.out.println("that's not a valid price.");
                    break;
                }
                try {
                    machine.changePrice(priceCode, price);
                    System.out.println("price updated!");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
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

    private Integer readInteger(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String format(double number) {
        return String.format("%.2f", number);
    }
}
