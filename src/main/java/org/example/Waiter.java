package org.example;

import javax.swing.plaf.nimbus.State;
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

        this.appetizerOrder = new HashMap<>();
        this.mainCourseOrder = new HashMap<>();
        this.dessertOrder = new HashMap<>();
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

    public void recieveOrder(int x, int y, int tableNumber, String appetizer, String mainCourse, String dessert){

            appetizerOrder.put(tableNumber, appetizer);
            mainCourseOrder.put(tableNumber, mainCourse);
            dessertOrder.put(tableNumber, dessert);

            if (isIdle()){
                state = States.GOING_TO_TABLE;
                targetX = x;
                targetY = y;
            }

    }

    private void walkToTable(){
        if (isGoingToTable()){
            if (goTo()) {
                state = States.TAKING_ORDER;
                try {
                    Thread.sleep(2000); // Take order for 2 seconds
                } catch (Exception threadException) {
                    System.out.println("Sleep exception: " + threadException.getMessage());
                }
                state = States.GOING_HOME;
                System.out.println("Going home");
            }


        }
    }
    private boolean goTo(){
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
                break;

            case GOING_HOME:
                walkHome();
                break;

            default:
                break;
        }

    }
    private void walkHome(){
        targetX = 507;
        targetY = 300;
        if (isGoingHome()) {
            if (goTo()){
                try{
                    Thread.sleep(2000);
                }
                catch (Exception threadException) {
                    System.out.println("Sleep exception: " + threadException.getMessage());
                }

                state = States.IDLE;
                System.out.println("Is now home");
            }
        }

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

    public boolean isGoingHome(){
        if (state == States.GOING_HOME){
            return true;
        }
        else{
            return false;
        }
    }


// Have to make targetX and Y better implemented
}
