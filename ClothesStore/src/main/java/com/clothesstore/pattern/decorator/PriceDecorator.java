package com.clothesstore.pattern.decorator;

public abstract class PriceDecorator implements PriceComponent {

    protected PriceComponent wrapped;

    public PriceDecorator(PriceComponent wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice();
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription();
    }
}