package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class DeliverOrderWaiter extends MasterWaiter {
    private ArrayList<Table> tables;

    public DeliverOrderWaiter(int x, int y, ArrayList<Table> tables, MasterChef masterChef) {
        super(x, y, masterChef);
        this.tables = tables;
        this.homeX = x;
        this.homeY = y;
    }

    protected void waiterSpecificTask() {
        //Give table its order
        if (isIdle()) {
            targetX = tables.get(getTableNumberFromOrder()).getX();
            targetY = tables.get(getTableNumberFromOrder()).getY();
        }
    }

    private ArrayList<String> chooseOrderFromOrders() {
        //orderFromTable.remove(key);

        return orderFromTable.get(getTableNumberFromOrder());

    }

    private int getTableNumberFromOrder(){
        Object objectKey = orderFromTable.keySet().toArray()[0];
        return (Integer) objectKey;
    }

    private void getOrderFromMaster(){
        orderFromTable = masterChef.giveOrderToWaiter();
        toString(chooseOrderFromOrders());

    }

    private void giveTableOrder(){
        tables.get(getTableNumberFromOrder()).setFoodToEat(chooseOrderFromOrders());
        //toString(tables.get(getTableNumberFromOrder()).foodToEat);
        orderFromTable.clear();
    }


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

    private void checkMasterChef() {
        if (isIdle()) {
            if (!masterChef.getOrdersToBeDelivered().isEmpty()) {
                getOrderFromMaster();
                waiterSpecificTask();
                state = States.GOING_TO_TABLE;
            }
        }
    }

    @Override
    public void update(){
                switch (state){
                    case GOING_TO_TABLE:
                        walkToTable();
                        break;

                    case GOING_HOME:
                        walkHome();
                        break;
                    // If the state is IDLE:
                    case IDLE:
                        checkMasterChef();
                        break;
                }
    }

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


