package com.clothesstore.pattern.observer;

public interface ReviewSubject {
    void attach(ProductObserver observer);
    void detach(ProductObserver observer);
    void notifyObservers(int productId);
}