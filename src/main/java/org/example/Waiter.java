package org.example;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.HashMap;

public class Waiter extends Walker implements Subscriber{
    ;
    private int diameter = 50;
    private HashMap<Integer, String> orderFromTable;
    private ArrayList<Table> tables;
    private enum States {IDLE, GOING_TO_TABLE, GOING_HOME}
    private States state = States.IDLE;
    private MasterChef masterChef;


    public Waiter(int x, int y, int diameter, ArrayList<Table> tables, MasterChef masterChef){
        super(x, y);
        this.diameter = diameter;
        this.tables = tables;
        this.orderFromTable = new HashMap<>();
        this.masterChef = masterChef;

    }
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getDiameter() {
        return this.diameter;
    }

    public void retrieveOrder(int x, int y, int tableNumber, String foodOrder){

        // Puts order from the table into orderFromTable to be given to MasterChef
        this.orderFromTable.put(tableNumber, foodOrder);

        // If the waiter is idle, makes its state into GOING_TO_TABLE to make update() run walkToTable()
        if (isIdle()){
            state = States.GOING_TO_TABLE;
            targetX = x; // Sets the x-coordinate that waiter is going to
            targetY = y; // Sets the y-coordinate that waiter is going to
            System.out.println("Is going to table " + tableNumber);
        }

    }

    private void walkToTable(){
        // If everything has gone correctly this should always be true, just a failsafe
        if (isGoingToTable()){
            // Runs goTo() and checks if it returns true for both x and y
            if (goTo()) {
                //Starts a timer if startCounting is true
                startTimer(2000, "Is taking order");
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

    public void update(){
        // Runs the different walk methods depending on which state Waiter has
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
    private void walkHome(){
        // New target is its home
        targetX = 507;
        targetY = 300;
        // Failsafe, should always be true but if it is not it does not run goTo()
        if (isGoingHome()) {
            // Runs goTo() and checks if it returns true
            if (goTo()){
                // Starts timer if startCounting is true
                startTimer(2000, "Is now idle");
                long thisTime = System.currentTimeMillis(); // Takes new time every update in RestaurantMain
                // If it has gone the period time:
                if ((thisTime - lastTime) >= period) {
                    handOrder();
                    state = States.IDLE;
                    startCounting = true;

                }
            }
        }

    }
    // Hands order to masterChef and clears the order the waiter has
    private void handOrder(){
        masterChef.takeOrder(this.orderFromTable);
        this.orderFromTable.clear();
    }

    public boolean isIdle() { return (state == States.IDLE); }

    public boolean isGoingToTable(){
        return (state == States.GOING_TO_TABLE);
    }

    public boolean isGoingHome(){
        return (state == States.GOING_HOME);
    }

    }



// Have to make targetX and Y better implemented


