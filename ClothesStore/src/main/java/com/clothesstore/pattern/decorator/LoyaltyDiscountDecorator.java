package com.clothesstore.pattern.decorator;

public class LoyaltyDiscountDecorator extends PriceDecorator {

    public LoyaltyDiscountDecorator(PriceComponent wrapped) {
        super(wrapped);
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice() * 0.90;
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + 10% loyalty discount";
    }
}