package com.clothesstore.pattern.decorator;

public class BasePrice implements PriceComponent {

    private final double price;

    public BasePrice(double price) {
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return "Base price";
    }
}