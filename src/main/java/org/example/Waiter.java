package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class Waiter {
    private static final long PERIOD = 6230; //one lap
    private final long lastTime = System.currentTimeMillis();;
    private int x;
    private int y;
    private int diameter = 50;
    private HashMap<Integer, String> appetizerOrder;
    private HashMap<Integer, String> mainCourseOrder;
    private HashMap<Integer, String> dessertOrder;
    private ArrayList<Table> tables;

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

    public void recieveOrder(){
        walk();


    }

    private void deliverOrder(){

    }
    private int walk(){
        long thisTime = System.currentTimeMillis(); //takes new time every update in RestaurantMain
        if ((thisTime - lastTime) >= PERIOD) { // if the difference between when the object was created and the current time is == PERIOD, returns -1 all the time
            return -1;
        }
        else{
            if (this.y > 93 && this.x < 508) { // First table top left
                this.y = this.y - 10;
            } else if (this.x < 1047 && this.y < 100) { // Last table top right
                this.x = this.x + 10;
            } else if (this.y < 470 && this.x > 999) { // 
                this.y = this.y + 10;
            } else if (this.x > 350 && this.y > 449) {
                this.x = this.x - 10;
            } else if (this.y > 300) {
                this.y = this.y - 10;
            }
        }
        return 0;

    }
    public void update(){
        // For-loop loops through tables to check if they are ready
    }



}
