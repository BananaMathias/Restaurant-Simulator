package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class Waiter implements Subscriber{
    private static final long PERIOD = 4000; //one lap
    private final long lastTime = System.currentTimeMillis();
    private int x;
    private int y;
    private int diameter = 50;
    private HashMap<Integer, String> appetizerOrder;
    private HashMap<Integer, String> mainCourseOrder;
    private HashMap<Integer, String> dessertOrder;
    private ArrayList<Table> tables;
    private enum States {IDLE, GOING_TO_TABLE, TAKING_ORDER, GOING_HOME}
    private States state = States.IDLE;
    private int targetX;
    private int targetY;

    public Waiter(int x, int y, int diameter, ArrayList<Table> tables){
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.tables = tables;
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

    public void recieveOrder(int x, int y){

            //long thisTime = System.currentTimeMillis(); //takes new time every update in RestaurantMain
            //if ((thisTime - lastTime) >= PERIOD) { // if the difference between when the object was created and the current time is == PERIOD, returns -1 all the time
                //state = States.GOING_HOME;
                //walkHome();
            if (isIdle()){
                state = States.GOING_TO_TABLE;
                targetX = x;
                targetY = y;
            }


    }

    private void walkToTable(){
        if (isGoingToTable()){
            if (goTo(targetX, targetY)){
                System.out.println(1);
            }


        }
    }
    private boolean goTo(int x, int y){
        boolean xArrived = false;
        boolean yArrived = false;

        if (this.x < targetX) {
            this.x += Math.min(10, targetX - this.x);
        } else if (this.x > targetX) {
            this.x -= Math.min(10, this.x - targetX);
        } else {
            xArrived = true;
        }


        if (this.y < targetY) {
            this.y += Math.min(5, targetY - this.y);
        } else if (this.y > targetY) {
            this.y -= Math.min(5, this.y - targetY);
        } else {
            yArrived = true;
        }


        return xArrived && yArrived;
    }

    public void update(){
        switch (state){
            case GOING_TO_TABLE:
                walkToTable();

            case GOING_HOME:
                walkHome();

            default: {}
        }

    }
    private void walkHome(){
        if (state == States.GOING_HOME) {
            goTo(507, 300);
        }


    }



    public States getState(){
        return state;
    }

    public boolean isIdle(){
        if (state == States.IDLE){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isGoingToTable(){
        if (state == States.GOING_TO_TABLE){
            return true;
        }
        else{
            return false;
        }
    }



}
