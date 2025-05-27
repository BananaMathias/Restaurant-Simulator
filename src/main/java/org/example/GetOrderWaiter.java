package org.example;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates a waiter that gets orders from tables
 * Listener on the tables
 *
 */
public class GetOrderWaiter extends MasterWaiter implements WaiterListener {

    private HashMap<Integer, ArrayList<String>> orderFromTable;

    /**
     * Constructor
     * @param x The waiter's x-position
     * @param y The waiter's y-position
     * @param masterChef Reference to the masterChef
     */
    public GetOrderWaiter(int x, int y, MasterChef masterChef){
        super(x, y, masterChef);
        this.orderFromTable = new HashMap<>();
        this.homeX = x;
        this.homeY = y;


    }

    /**
     * Gets the order from the table its at and puts it in a HashMap containing the tablenumber as a key and the order as an ArrayList
     * @param x The table's x-position
     * @param y The table's y-position
     * @param tableNumber int The table's number
     * @param foodOrders ArrayList<String></String> The table's food order
     */
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

    /**
     * Hands the received order to the masterChef and clears its HashMap so that the size of orderFromTable always is 1
     */
    @Override
    protected void waiterSpecificTask(){
        masterChef.takeOrderFromWaiter(this.orderFromTable);
        this.orderFromTable.clear();
    }

    }