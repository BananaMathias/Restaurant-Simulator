package org.example;

import java.util.ArrayList;

public interface Subscriber {
    public void retrieveOrder(int x, int y, int tableNumber, ArrayList<String> foodOrders);
}
