package ch.noseryoung.blj;

public class Product {
    private final String code;
    private String name;
    private double price;
    private int stock;

    public Product(String code, String name, double price, int stock) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Code must not be empty.");
        }

        this.code = code;
        setName(name);
        setPrice(price);
        setStock(stock);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty.");
        }

        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }

        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock must not be negative.");
        }

        this.stock = stock;
    }
}
