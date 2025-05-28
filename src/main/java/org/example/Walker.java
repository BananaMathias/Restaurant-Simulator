package org.example;

import java.util.ArrayList;

/**
 * Abstract
 * The class that all classes that are supposed to walk inherits to be able to walk and stand still when needed
 */
public abstract class Walker {

    protected static long period; //one lap
    protected long lastTime;
    protected boolean startCounting = true;

    protected int x;
    protected int y;
    protected int targetX;
    protected int targetY;
    protected int diameter = 50;

    /**
     * Constructor
     * @param x The Walker's x-position
     * @param y The Walker's y-position
     */
    public Walker(int x, int y) {
        this.x = x;
        this.y = y;

    }

    /**
     * Adds or removes from the Walker's x and y position so that they walk towards the target
     * Checks if the Walker has arrived and returns true if that is the case
     * @return true if the Walker has reached its destination
     */
    protected boolean goTo() {
        boolean xArrived = false;
        boolean yArrived = false;

        // If Walker x-position is less than the targets x-position
        if (this.x < targetX) {
            // Adds the difference between target-x and Walker-x, with a max of adding 10
            this.x += Math.min(10, targetX - this.x);

            // If Walker x-position is greater than targets x-position
        } else if (this.x > targetX) {
            // Adds the difference between Walker-x and target-x, with a max of removing 10
            this.x -= Math.min(10, this.x - targetX);

            // If none of the above is true, the Walker is at its target's x-position
        } else {
            xArrived = true;
        }

        // If Walker y-position is less than the targets x-position
        if (this.y < targetY) {
            // Adds the difference between target-y and Walker-y, with a max of adding 5
            this.y += Math.min(5, targetY - this.y);

            // If Walker y-position is greater than targets y-position
        } else if (this.y > targetY) {
            // Adds the difference between Walker-y and target-y, with a max of removing 5
            this.y -= Math.min(5, this.y - targetY);

            // If none of the above is true, the Walker is at its target's y-position
        } else {
            yArrived = true;
        }
        // Returns true if the Walker has arrived at both x and y-coordinates
        return xArrived && yArrived;
    }

    /**
     * Starts a timer if the Walker can start a timer
     * @param newPeriod int The time its supposed to wait
     * @param action String Message the Walker prints out when the timer starts
     */
    protected boolean startTimer(int newPeriod, String action) {
        // If the Walker has its startCounting set as true
        if (startCounting) {
            // Sets the time that should be compared to in Walkers timer
            lastTime = System.currentTimeMillis();
            period = newPeriod;

            // Sets startCounting to false so that this method only runs once by itself
            startCounting = false;
            System.out.println(action);
            return true;
        }
        else{
            return false;
        }

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
}
