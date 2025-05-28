package org.example;

import java.awt.*;

/**
 * Creates guests that eat at the tables
 * Extends Walker
 */
public class Guest extends Walker {

    private int number;
    private enum States {IDLE, GOING_TO_TABLE, EATING}
    private States state = States.IDLE;
    private String food = null;
    private int mealsEaten = 0;
    private Color color = Color.BLUE;

    /**
     * Constructor
     * @param x the guests x-position
     * @param y the guests y-position
     * @param number a number specific to a guest in a group of guests
     */
    public Guest(int x, int y, int number) {
        super(x, y);
        this.number = number;
    }

    /**
     * Makes the guests walk to its assigned table
     */
    public void walkToTable() {
        // If everything has gone correctly this should always be true, just a failsafe
        if (isGoingToTable()) {
            // Runs goTo() and checks if it returns true for both x and y
            if (goTo()) {
                state = States.IDLE;
            }
        }
    }

    /**
     * Sets the target x and y coordinates of the assigned table
     * @param x table's x-position
     * @param y table's y-position
     */
    public void setSeat(int x, int y){
        this.targetX = x;
        this.targetY = y;
        state = States.GOING_TO_TABLE;
    }

    /**
     * The guest eats its received meal for 10 seconds after which the amount of meals its eaten increases
     */
    public void eat(){
        if (isEating()){
            if (!(food == null)) { // If it has food:
                startTimer(10000, "eating");
                long thisTime = System.currentTimeMillis();
                if ((thisTime - lastTime) >= period) { // When the guest has eaten for 10 seconds
                    food = null;
                    state = States.IDLE;
                    startCounting = true;
                    mealsEaten ++; // 1 more meal has been eaten
                    System.out.println("Has eaten");
                }
            }
        }
    }

    /**
     * Updates the guests in table which updates them in main
     */
    public void update(){
        switch (state){
            case GOING_TO_TABLE:
                walkToTable();
                break;

            case EATING:
                eat();
                break;

            default:
                break;

        }
    }

    /**
     * Gets the meal from DeliverOrderWaiter
     * @param meal String The ordered meal
     */
    public void receiveFood(String meal) {
        food = meal;
        state = States.EATING;
    }

    public boolean isGoingToTable() {
        return state == States.GOING_TO_TABLE;
    }

    public boolean isEating(){ return state == States.EATING;}

    public boolean isDone(){return mealsEaten == 3;}

    public Color getColor(){
        return color;
    }

    public int getNumber(){
        return number;
    }
}
