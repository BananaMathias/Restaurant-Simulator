package org.example;

import java.util.ArrayList;

/**
 * The GetOrderWaiter's interface that the tables uses
 */
public interface WaiterListener {
    public void retrieveOrder(int x, int y, int tableNumber, ArrayList<String> foodOrders);
}
