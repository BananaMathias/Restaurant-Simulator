package org.example;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates a waiter with the purpose of delivering completed orders to the tables
 */
public class DeliverOrderWaiter extends MasterWaiter {
    private ArrayList<Table> tables;

    /**
     * Constructor
     * @param x the waiter's x-position
     * @param y the waiter's y-position
     * @param tables ArrayList<Table></Table> of the existing tables
     * @param masterChef Reference to the masterChef
     */
    public DeliverOrderWaiter(int x, int y, ArrayList<Table> tables, MasterChef masterChef) {
        super(x, y, masterChef);
        this.tables = tables;
        this.homeX = x;
        this.homeY = y;
    }

    /**
     * Gets the x and y position of the table its going to
     */
    protected void waiterSpecificTask() {
        if (isIdle()) {
            targetX = tables.get(getTableNumberFromOrder()).getX();
            targetY = tables.get(getTableNumberFromOrder()).getY();
        }
    }

    /**
     * Gets the array containing the completed orders
     * @return ArrayList<String></String> order array
     */
    private ArrayList<String> chooseOrderFromOrdersArray() {
        return orderFromTable.get(getTableNumberFromOrder());

    }

    /**
     * Gets the targeted table's number
     * @return int The number
     */
    private int getTableNumberFromOrder(){
        Object objectKey = orderFromTable.keySet().toArray()[0];
        return (Integer) objectKey;
    }

    /**
     * Gets a HashMap from the masterChef containing table's number as key and completed order array as item
     */
    private void getOrderFromMaster(){
        orderFromTable = masterChef.giveOrderToWaiter();
    }

    /**
     * Gives the table its food
     */
    private void giveTableOrder(){
        tables.get(getTableNumberFromOrder()).setFoodToEat(chooseOrderFromOrdersArray());
        orderFromTable.clear();

    }

    /**
     * Walks to the table and gives the food
     */
    @Override
    protected void walkToTable() {
        // If everything has gone correctly this should always be true, just a failsafe
        if (isGoingToTable()) {
            // Runs goTo() and checks if it returns true for both x and y
            if (goTo()) {
                //Starts a timer if startCounting is true
                startTimer(2000, "Giving order");
                long thisTime = System.currentTimeMillis(); // Takes new time every update in RestaurantMain

                // If the period time is reached:
                if ((thisTime - lastTime) >= period) {
                    giveTableOrder(); // Gives the table its order
                    state = States.GOING_HOME; // Sets state to GOING_HOME so that update() runs walkHome()
                    startCounting = true; // Makes it so that walkHome() can set a timer when it is supposed to
                    System.out.println("Going home");

                }

            }
        }
    }

    /**
     * Checks if the masterChef has a completed order it can give
     */
    private void checkMasterChef() {
        if (isIdle()) {
            if (!masterChef.getOrdersToBeDelivered().isEmpty()) { // if masterChef has a completed order
                getOrderFromMaster(); // Takes an order
                waiterSpecificTask(); // Gets the table x and y coordinates
                state = States.GOING_TO_TABLE;
            }
        }
    }

    /**
     * Updates the waiter in main
     */
    @Override
    public void update(){
                switch (state){
                    case GOING_TO_TABLE:
                        walkToTable();
                        break;

                    case GOING_HOME:
                        walkHome();
                        break;
                    case IDLE:
                        checkMasterChef();
                        break;
                }
    }

    /**
     * Walks to its home station from the tables
     */
    @Override
    protected void walkHome(){
        // New target is its home
        targetX = this.homeX;
        targetY = this.homeY;
        // Failsafe, should always be true but if it is not it does not run goTo()
        if (isGoingHome()) {
            // Runs goTo() and checks if it returns true
            if (goTo()){
                state = States.IDLE;
                startCounting = true;

                }
            }
        }
    }


