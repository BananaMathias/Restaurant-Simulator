package org.example;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Abstract
 * MasterWaiter that the different waiters inherits
 * Extends Walker
 */
public abstract class MasterWaiter extends Walker {

    protected int diameter = 50;
    protected HashMap<Integer, ArrayList<String>> orderFromTable;
    protected enum States {IDLE, GOING_TO_TABLE, GOING_HOME}
    protected States state = States.IDLE;
    protected MasterChef masterChef;
    protected int homeX;
    protected int homeY;

    /**
     * Constructor
     * @param x Waiter's x-position
     * @param y Waiter's y-position
     * @param masterChef Reference to the masterChef
     */
    public MasterWaiter(int x, int y, MasterChef masterChef){
        super(x, y);
        this.masterChef = masterChef;

    }

    protected abstract void waiterSpecificTask();

    /**
     * Gets a waiter to walk to the specified table
     * Designed for getOrderWaiters
     */
    protected void walkToTable(){
        // If everything has gone correctly this should always be true, just a failsafe
        if (isGoingToTable()){
            // Runs goTo() and checks if it returns true for both x and y
            if (goTo()) {
                //Starts a timer if startCounting is true
                startTimer(2000, "GetOrderWaiter is taking order");
                long thisTime = System.currentTimeMillis(); // Takes new time every update in RestaurantMain

                // If the period time is reached:
                if ((thisTime - lastTime) >= period) {
                    state = States.GOING_HOME; // Sets state to GOING_HOME so that update() runs walkHome()
                    startCounting = true; // Makes it so that walkHome() can set a timer when it is supposed to
                    System.out.println("Going home");
                }

            }


        }
    }

    /**
     * Updates the waiters in main
     */
    public void update(){
        // Runs the different walk methods depending on which state GetOrderWaiter has
        switch (state){
            case GOING_TO_TABLE:
                walkToTable();
                break;

            case GOING_HOME:
                walkHome();
                break;
            // If the state is IDLE:
            default:
                break;
        }

    }

    /**
     * Makes the waiters walk home
     * Designed for getOrderWaiters
     */
    protected void walkHome(){
        // New target is its home
        targetX = this.homeX;
        targetY = this.homeY;
        // Failsafe, should always be true but if it is not it does not run goTo()
        if (isGoingHome()) {
            // Runs goTo() and checks if it returns true
            if (goTo()){
                // Starts timer if startCounting is true
                startTimer(2000, "GetOrderWaiter is now idle");
                long thisTime = System.currentTimeMillis(); // Takes new time every update in RestaurantMain
                // If it has gone the period time:
                if ((thisTime - lastTime) >= period) {
                    waiterSpecificTask();
                    state = States.IDLE;
                    startCounting = true;

                }
            }
        }

    }

    public boolean isIdle() { return (state == States.IDLE); }

    public boolean isGoingToTable(){
        return (state == States.GOING_TO_TABLE);
    }

    public boolean isGoingHome(){
        return (state == States.GOING_HOME);
    }

    /**
     * Prints out a provided ArrayList<String></String>
     * @param a Provided ArrayList<String></String>
     */
    public void toString(ArrayList<String> a) {
        System.out.print("[");
        for (String element : a) {
            System.out.print(element + ", ");
        }
        System.out.print("]");
    }
}
