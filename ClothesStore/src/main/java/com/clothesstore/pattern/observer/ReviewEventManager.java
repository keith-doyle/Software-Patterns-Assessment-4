package com.clothesstore.pattern.observer;

import java.util.ArrayList;
import java.util.List;

public class ReviewEventManager implements ReviewSubject {

    private final List<ProductObserver> observers = new ArrayList<>();

    @Override
    public void attach(ProductObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(ProductObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(int productId) {
        for (ProductObserver observer : observers) {
            observer.update(productId);
        }
    }
}