package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public interface ChefListener {

    void notifyListener();
    void takeOrderFromChef(HashMap<Integer, ArrayList<String>> completedOrders);

}
