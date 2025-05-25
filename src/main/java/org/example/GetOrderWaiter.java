package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class GetOrderWaiter extends MasterWaiter implements WaiterListener {

    private HashMap<Integer, ArrayList<String>> orderFromTable;

    public GetOrderWaiter(int x, int y, MasterChef masterChef){
        super(x, y, masterChef);
        this.orderFromTable = new HashMap<>();
        this.homeX = x;
        this.homeY = y;


    }
    @Override
    public void retrieveOrder(int x, int y, int tableNumber, ArrayList<String> foodOrders){

        // Puts order from the table into orderFromTable to be given to MasterChef
        this.orderFromTable.put(tableNumber,foodOrders);

        // If the waiter is idle, makes its state into GOING_TO_TABLE to make update() run walkToTable()
        if (isIdle()){
            state = States.GOING_TO_TABLE;
            targetX = x; // Sets the x-coordinate that waiter is going to
            targetY = y; // Sets the y-coordinate that waiter is going to
            System.out.println("Is going to table " + tableNumber);
        }

    }


    private ArrayList<String> chooseOrderFromOrders() {
        Object objectKey = orderFromTable.keySet().toArray()[0];
        Integer key = (Integer) objectKey;
        ArrayList<String> order = orderFromTable.get(key);
        //orderFromTable.remove(key);

        return order;
    }
    // Hands order to masterChef and clears the order the waiter has
    @Override
    protected void waiterSpecificTask(){
        masterChef.takeOrderFromWaiter(this.orderFromTable);
        this.orderFromTable.clear();
    }

    }



// Have to make targetX and Y better implemented


