package com.clothesstore.pattern.nullobject;

public class NoDiscount implements DiscountPolicy {

    @Override
    public double apply(double price) {
        return price;
    }

    @Override
    public String getDescription() {
        return "No discount applied";
    }
}