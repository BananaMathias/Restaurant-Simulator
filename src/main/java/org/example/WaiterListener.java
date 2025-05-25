package org.example;

import java.util.ArrayList;

public interface WaiterListener {
    public void retrieveOrder(int x, int y, int tableNumber, ArrayList<String> foodOrders);
}
