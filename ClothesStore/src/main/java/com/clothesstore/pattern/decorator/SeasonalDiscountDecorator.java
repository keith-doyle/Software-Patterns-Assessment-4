package com.clothesstore.pattern.decorator;

public class SeasonalDiscountDecorator extends PriceDecorator {

    public SeasonalDiscountDecorator(PriceComponent wrapped) {
        super(wrapped);
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice() * 0.95;
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + 5% seasonal discount";
    }
}