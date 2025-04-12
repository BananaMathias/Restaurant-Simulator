package org.example;

import java.util.HashMap;

public class MasterChef {

    private static MasterChef masterChefInstance = null;
    private int diameter;
    private int x;
    private int y;
    private Menu menu;
    private HashMap<Integer, String> ordersFromTables;


    private MasterChef(int x, int y, int diameter, Menu menu){
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.menu = menu;
        this.ordersFromTables = new HashMap<>();
    }

    public static MasterChef getInstance(int x, int y, int diameter, Menu menu) {
        if (masterChefInstance == null) {
            masterChefInstance = new MasterChef(x, y, diameter, menu);
        }
        return masterChefInstance;
    }
    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getDiameter(){
        return this.diameter;
    }
    // Takes order and puts in order list
    public void takeOrder(HashMap<Integer, String> orderFromTable){
        ordersFromTables.putAll(orderFromTable);

    }

    // Strategy pattern depending on which chef should take the order
    // Observer for each chef. If masterChef gets appetizer, he should notify correct chef to take care of it

}
