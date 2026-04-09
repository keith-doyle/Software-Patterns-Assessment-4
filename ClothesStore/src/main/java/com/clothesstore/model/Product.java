package com.clothesstore.model;

public class Product {
    private int productId;
    private String title;
    private String description;
    private double price;
    private int stockQuantity;
    private String imagePath;
    private String categoryName;
    private String manufacturerName;
    private double averageRating;
    private boolean active;

    public Product() {
    }

    public Product(int productId, String title, String description, double price, int stockQuantity,
                   String imagePath, String categoryName, String manufacturerName,
                   double averageRating, boolean active) {
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imagePath = imagePath;
        this.categoryName = categoryName;
        this.manufacturerName = manufacturerName;
        this.averageRating = averageRating;
        this.active = active;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}