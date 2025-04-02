package org.example;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.HashMap;

public class Waiter extends Walker implements Subscriber{
    private static long period; //one lap
    private long lastTime;
    private int diameter = 50;
    private HashMap<Integer, String> appetizerOrder;
    private HashMap<Integer, String> mainCourseOrder;
    private HashMap<Integer, String> dessertOrder;
    private ArrayList<Table> tables;
    private enum States {IDLE, GOING_TO_TABLE, TAKING_ORDER, GOING_HOME}
    private States state = States.IDLE;
    private boolean startCounting = true;


    public Waiter(int x, int y, int diameter, ArrayList<Table> tables){
        super(x, y);
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
            if (super.goTo()) {
                if (startCounting){
                    lastTime = System.currentTimeMillis();
                    period = 2000;
                    startCounting = false; // create startTimer method
                }
                long thisTime = System.currentTimeMillis();//takes new time every update in RestaurantMain
                if ((thisTime - lastTime) >= period) {
                    state = States.GOING_HOME;
                    startCounting = true;
                    System.out.println("Going home");
                }

            }


        }
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
            if (super.goTo()){
                if (startCounting){
                    lastTime = System.currentTimeMillis();
                    period = 2000;
                    startCounting = false;
                }
                long thisTime = System.currentTimeMillis();//takes new time every update in RestaurantMain
                if ((thisTime - lastTime) >= period) {
                    state = States.GOING_HOME;
                    startCounting = true;
                    System.out.println("Is now idle");
                    state = States.IDLE;
                }
            }
        }

    }

    public boolean isIdle(){
        return (state == States.IDLE);

    }

    public boolean isGoingToTable(){
        return (state == States.GOING_TO_TABLE);
    }

    public boolean isGoingHome(){
        return (state == States.GOING_HOME);
    }


    }
// Have to make targetX and Y better implemented


