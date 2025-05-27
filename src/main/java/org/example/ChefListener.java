package org.example;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The listener interface that the masterChef uses to subscriber to the chefs
 */
public interface ChefListener {

    void notifyListener();
    void takeOrderFromChef(HashMap<Integer, ArrayList<String>> completedOrders);
    void setIdle();
    int getX();
    int getY();

}
